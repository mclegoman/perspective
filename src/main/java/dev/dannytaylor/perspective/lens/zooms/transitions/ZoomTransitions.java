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
import dev.dannytaylor.perspective_old.client.events.Events;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.SmoothZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.resources.Identifier;

public class ZoomTransitions {
    public static ZoomTransition SMOOTH = register(LensClient.idOf("smooth"), new SmoothZoomTransition());

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Zoom Transitions", () -> {});
    }

    public static ZoomTransition register(Identifier identifier, ZoomTransition zoomTransition) {
        Events.ZoomTransitions.register(identifier, zoomTransition);
        return zoomTransition;
    }
}
