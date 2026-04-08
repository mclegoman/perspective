/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.scales;

import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.LinearZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.LogarithmicZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;

public class ZoomScales {
    public static ZoomScale LOGARITHMIC = register(Identifiers.LOGARITHMIC, new LogarithmicZoomScale());
    public static ZoomScale LINEAR = register(Identifiers.LINEAR, new LinearZoomScale());

    public static void onInitializeClient() {
        Log.info("Initializing zoom scales...");
    }

    public static ZoomScale register(Identifier identifier, ZoomScale zoomScale) {
        Events.ZoomScales.register(identifier, zoomScale);
        return zoomScale;
    }
}
