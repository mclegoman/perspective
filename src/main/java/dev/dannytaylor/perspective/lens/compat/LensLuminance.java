/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.compat;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderTime;
import com.mclegoman.luminance.client.shaders.Uniforms;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;

public class LensLuminance extends RenderLocations {
    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Luminance Compatibility", () -> {
            Uniforms.registerSingleValueTree(LensClient.getMod().getId(true), "zoom_multiplier", LensLuminance::getZoomMultiplier, null, null);
            Uniforms.registerSingleValueTree(LensClient.getMod().getId(true), "zoom_amount", LensLuminance::getZoomAmount, 0f, 100f);
            Uniforms.registerSingleValueTree(LensClient.getMod().getId(true), "zooming", LensLuminance::getZooming, 0f, 1f);
        });
    }

    public static float getZoomMultiplier(ShaderTime shaderTime) {
        return ZoomRegistry.getCombinedMultiplier();
    }

    public static float getZoomAmount(ShaderTime shaderTime) {
        return LensConfig.instance.amount.value();
    }

    public static float getZooming(ShaderTime shaderTime) {
        return ZoomRegistry.isZooming() ? 1.0F : 0.0F;
    }
}
