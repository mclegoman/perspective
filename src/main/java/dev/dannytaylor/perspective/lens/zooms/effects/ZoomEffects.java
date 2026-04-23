/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.effects;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ScaledZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.AbstractZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import net.minecraft.resources.Identifier;

public class ZoomEffects {
    public static ZoomEffect SCALED = register(LensClient.idOf("scaled"), new ScaledZoomEffect(), 0.0F);
    public static ZoomEffect UNSCALED = register(LensClient.idOf("unscaled"), new AbstractZoomEffect() {});

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom Effects", () -> {});
    }

    public static ZoomEffect register(Identifier identifier, ZoomEffect zoomEffect) {
        LensEvents.ZoomEffects.register(identifier, zoomEffect);
        return zoomEffect;
    }

    public static ZoomEffect register(Identifier identifier, ZoomEffect zoomEffect, float priority) {
        LensEvents.ZoomEffects.register(identifier, zoomEffect, priority);
        return zoomEffect;
    }
}
