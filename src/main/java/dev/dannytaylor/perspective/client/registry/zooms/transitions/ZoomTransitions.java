/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.transitions;

import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.LinearZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.LogarithmicZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.SmoothZoomTransition;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;

public class ZoomTransitions {
    public static ZoomTransition SMOOTH = register(Identifiers.SMOOTH, new SmoothZoomTransition());

    public static void onInitializeClient() {
        Log.info("Initializing zoom transitions...");
    }

    public static ZoomTransition register(Identifier identifier, ZoomTransition zoomTransition) {
        Events.ZoomTransitions.register(identifier, zoomTransition);
        return zoomTransition;
    }
}
