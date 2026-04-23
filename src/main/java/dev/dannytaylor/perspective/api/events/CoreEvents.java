/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import com.mclegoman.luminance.client.events.Events;
import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.config.CoreConfig;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.gui.screen.config.ConfigGroup;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

// TODO: Split events from Luminance into a shared library mod, so that Luminance isn't required for every v2 mod.
// Some of these methods could also be moved over to that mod tbh.
// Since we are extending, nothing *should* break, unless it's luminance specific (in which case, addons should be using the luminance's classes instead)
public class CoreEvents extends Events {
    public static final Registry<CoreRunnables.UseItem> OnClientStartItemUse = new Registry<>();
    public static final Registry<CoreRunnables.FinishUsingItem> OnClientFinishItemUse = new Registry<>();
    public static final Registry<CoreRunnables.Callable<HideUi>> ShouldHideHud = new Registry<>();
    public static final Registry<ConfigGroup> ConfigGroups = new Registry<>();

    public static void onInitialize(PerspectiveMod mod, Initializer onInitialize) {
        onInitialize(mod, "", onInitialize, true);
    }

    public static void onInitialize(PerspectiveMod mod, String name, Initializer onInitialize) {
        onInitialize(mod, name, onInitialize, false);
    }

    public static void onInitialize(PerspectiveMod mod, Initializer onInitialize, boolean logFinish) {
        onInitialize(mod, "", onInitialize, logFinish);
    }

    public static void onInitialize(PerspectiveMod mod, String name, Initializer onInitialize, boolean logFinish) {
        PerspectiveLog.info(CoreClient.getMod(), "Initializing{}...", getName(name));
        try {
            onInitialize.onInitialize();
            if (logFinish) PerspectiveLog.info(CoreClient.getMod(), "Finished initializing{}!", getName(name));
        } catch (Exception error) {
            PerspectiveLog.error(CoreClient.getMod(), "Failed to initialize{}: {}", getName(name), error);
        }
    }

    private static HideUi hideUi;

    public static HideUi getHideHud() {
        if (CoreConfig.instance.checkHideHudOnTick.value()) {
            if (hideUi == null) hideUi = CoreExecute.updateHideHud();
            return hideUi;
        } else return CoreExecute.updateHideHud();
    }

    private static String getName(String name) {
        return name.isBlank() ? name : " " + name;
    }

    public interface Initializer {
        void onInitialize();
    }

    public static void onTickClient(Minecraft minecraft) {
        if (CoreConfig.instance.checkHideHudOnTick.value()) hideUi = CoreExecute.updateHideHud();
    }

    public static Map<Identifier, ConfigGroup> getConfigGroups() {
        return ConfigGroups.registry.entrySet().stream().sorted(
                Map.Entry.<Identifier, ConfigGroup>comparingByValue(
                        Comparator.comparingDouble(ConfigGroup::getPriority)
                ).thenComparing(Map.Entry.comparingByKey())
        ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public static Identifier next(Registry<?> registry, Identifier identifier) {
        boolean returnNext = false;
        Identifier first = null;

        for (Identifier id : registry.registry.keySet().stream().sorted(Identifier::compareTo).toList()) {
            if (first == null) first = id;
            if (returnNext) return id;
            else if (id.equals(identifier)) returnNext = true;
        }

        return first;
    }
}
