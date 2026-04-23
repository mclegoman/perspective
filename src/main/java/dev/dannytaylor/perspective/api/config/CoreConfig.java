/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.config;

import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class CoreConfig extends PerspectiveConfig {
    public static final CoreConfig instance = CoreConfig.createToml(Paths.get("config"), "perspective", CoreClient.getMod().getId(false), CoreConfig.class);

    public final TrackedValue<Boolean> checkHideHudOnTick = this.value(true);
    public final TrackedValue<Boolean> debug = this.value(false);

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Config", () -> {
            // Neither of these options require a gui.
        });
    }
}
