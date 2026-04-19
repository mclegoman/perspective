/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.scales;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective_old.client.events.Events;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.LinearZoomScale;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.LogarithmicZoomScale;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import net.minecraft.resources.Identifier;

public class ZoomScales {
    public static ZoomScale LOGARITHMIC = register(LensClient.idOf("logarithmic"), new LogarithmicZoomScale());
    public static ZoomScale LINEAR = register(LensClient.idOf("linear"), new LinearZoomScale());

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Zoom Scales", () -> {});
    }

    public static ZoomScale register(Identifier identifier, ZoomScale zoomScale) {
        Events.ZoomScales.register(identifier, zoomScale);
        return zoomScale;
    }
}
