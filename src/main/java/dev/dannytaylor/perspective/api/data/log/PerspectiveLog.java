/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.data.log;

import com.mclegoman.luminance.common.util.LogType;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;

public class PerspectiveLog {
    public static void send(PerspectiveMod mod, LogType type, String message, Object... args) {
        mod.getLogger().send(type, message, args);
    }

    public static void info(PerspectiveMod mod, String message, Object... args) {
        mod.getLogger().info(message, args);
    }

    public static void warn(PerspectiveMod mod, String message, Object... args) {
        mod.getLogger().warn(message, args);
    }

    public static void error(PerspectiveMod mod, String message, Object... args) {
        mod.getLogger().error(message, args);
    }

    public static void debug(PerspectiveMod mod, String message, Object... args) {
        mod.getLogger().debug(message, args);
    }
}
