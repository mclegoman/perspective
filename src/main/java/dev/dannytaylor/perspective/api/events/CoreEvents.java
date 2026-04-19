/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;

public class CoreEvents {
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
        mod.getLogger().info("Initializing{}...", getName(name));
        try {
            onInitialize.onInitialize();
            if (logFinish) mod.getLogger().info("Finished initializing{}!", getName(name));
        } catch (Exception error) {
            mod.getLogger().error("Failed to initialize{}: {}", getName(name), error);
        }
    }

    private static String getName(String name) {
        return name.isBlank() ? name : " " + name;
    }

    public interface Initializer {
        void onInitialize();
    }
}
