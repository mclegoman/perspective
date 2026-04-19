/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.SmoothZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.resources.Identifier;

public class ZoomTransitions {
    public static ZoomTransition SMOOTH = register(LensClient.idOf("smooth"), new SmoothZoomTransition());

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom Transitions", () -> {});
    }

    public static ZoomTransition register(Identifier identifier, ZoomTransition zoomTransition) {
        LensEvents.ZoomTransitions.register(identifier, zoomTransition);
        return zoomTransition;
    }
}
