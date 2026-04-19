/*
    Classic Rendering
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.classic_rendering.config;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.classic_rendering.ClassicRenderingClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class ClassicRenderingConfig extends ReflectiveConfig {
    public static final ClassicRenderingConfig instance = ClassicRenderingConfig.createToml(Paths.get("config"), "perspective", ClassicRenderingClient.getMod().getId(false), ClassicRenderingConfig.class);

    public final TrackedValue<Float> starBrightnessMultiplier = this.value(1.0F);

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Config", () -> {});
    }
}
