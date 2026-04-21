/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.SmoothZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.resources.Identifier;

public class ZoomTransitions {
    public static ZoomTransition SMOOTH = register(LensClient.idOf("smooth"), SmoothZoomTransition.builder()
            .speedOut(LensConfig.instance.smoothSpeedOut::value)
            .speedIn(LensConfig.instance.smoothSpeedIn::value)
            .build(LensClient.getMod()));

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom Transitions", () -> {});
    }

    public static ZoomTransition register(Identifier identifier, ZoomTransition zoomTransition) {
        LensEvents.ZoomTransitions.register(identifier, zoomTransition);
        return zoomTransition;
    }
}
