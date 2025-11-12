/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.time;

import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.config.value.ConfigIdentifier;
import com.mclegoman.perspective.common.util.Identifiers;
import net.minecraft.util.Identifier;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDate {
    private static final Map<Identifier, Type> registry = new HashMap<>();

    public static boolean register(Identifier identifier, Type type) {
        if (!registry.containsKey(identifier)) {
            registry.put(identifier, type);
            return true;
        }
        return false;
    }

    public static Type get(Identifier identifier) {
        return registry.get(identifier);
    }

    public static boolean exists(Identifier identifier) {
        return registry.containsKey(identifier);
    }

    public static void cycle(boolean direction) {
        List<Identifier> typeIds = registry.keySet().stream().toList();
        int currentIndex = typeIds.indexOf(getTypeIdFromConfig());
        PerspectiveConfig.config.dateType.setValue(ConfigIdentifier.of(typeIds.get(direction ? (currentIndex + 1) % typeIds.size() : (currentIndex - 1 + typeIds.size()) % typeIds.size())), false);
    }

    public static void bootstrap() {
        register(Identifiers.GREGORIAN, new Type((gameDay) -> LocalDate.of(1, 1, 1).plusDays(gameDay).getDayOfWeek().getValue(),
                (gameDay) -> LocalDate.of(1, 1, 1).plusDays(gameDay).getMonthValue(),
                (gameDay) -> LocalDate.of(1, 1, 1).plusDays(gameDay).getDayOfMonth(),
                (gameDay) -> LocalDate.of(1, 1, 1).plusDays(gameDay).getYear()));
        register(Identifiers.MOON_PHASES, new Type((gameDay) -> {
                // If there is 8 days in a month, there would be 2 days in a week (2x4=8).
                return (gameDay % 2) + 1;
            }, (gameDay) -> {
                // The moon cycles state each game day, so there are 8 days in a month.
                // 8 (days) * 12 (months) = 96 (days)
                return ((gameDay % 96) / 8) + 1;
            }, (gameDay) -> {
                // There is always 8 days per moon cycle.
                return (gameDay % 8) + 1;
            }, (gameDay) -> {
                // There is always 96 days in a lunar year.
                return (gameDay / 96) + 1;
            })
        );
    }

    public static Identifier getTypeIdFromConfig() {
        return PerspectiveConfig.config.dateType.value().identifier();
    }

    public static int getGameDay(long time) {
        return (int)(time / 24000L);
    }

    public static int getDayOfWeek(int gameDay, Type type) {
        return type.getDayOfWeek().call(gameDay);
    }

    public static int getDayOfWeek(long time, Type type) {
        return getDayOfWeek(getGameDay(time), type);
    }

    public static int getMonth(int gameDay, Type type) {
        return type.getMonthInYear().call(gameDay);
    }

    public static int getMonth(long time, Type type) {
        return getMonth(getGameDay(time), type);
    }

    public static int getDayOfMonth(int gameDay, Type type) {
        return type.getDayOfMonth().call(gameDay);
    }

    public static int getDayOfMonth(long time, Type type) {
        return getDayOfMonth(getGameDay(time), type);
    }

    public static int getYear(int gameDay, Type type) {
        return type.getYear().call(gameDay);
    }

    public static int getYear(long time, Type type) {
        return getYear(getGameDay(time), type);
    }

    public record Type(CallableInt getDayOfWeek, CallableInt getMonthInYear, CallableInt getDayOfMonth, CallableInt getYear) {
    }

    @FunctionalInterface
    public interface CallableInt {
        int call(int value);
    }
}
