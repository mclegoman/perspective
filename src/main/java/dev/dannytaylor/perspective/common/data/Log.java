/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.common.data;

import com.mclegoman.luminance.common.util.LogType;

public class Log {
    public static void sendToLog(LogType type, String message, Object... args) {
        Data.sendToLog(type, message, args);
    }

    public static void info(String message, Object... args) {
        sendToLog(LogType.INFO, message, args);
    }

    public static void warn(String message, Object... args) {
        sendToLog(LogType.WARN, message, args);
    }

    public static void error(String message, Object... args) {
        sendToLog(LogType.ERROR, message, args);
    }

    public static void debug(String message, Object... args) {
        sendToLog(LogType.DEBUG, message, args);
    }
}
