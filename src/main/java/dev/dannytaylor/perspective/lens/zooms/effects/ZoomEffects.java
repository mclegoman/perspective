/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.effects;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective_old.client.events.Events;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ScaledZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.UnscaledZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import net.minecraft.resources.Identifier;

public class ZoomEffects {
    public static ZoomEffect SCALED = register(LensClient.idOf("scaled"), new ScaledZoomEffect());
    public static ZoomEffect UNSCALED = register(LensClient.idOf("unscaled"), new UnscaledZoomEffect());

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Zoom Effects", () -> {});
    }

    public static ZoomEffect register(Identifier identifier, ZoomEffect zoomEffect) {
        Events.ZoomEffects.register(identifier, zoomEffect);
        return zoomEffect;
    }
}
