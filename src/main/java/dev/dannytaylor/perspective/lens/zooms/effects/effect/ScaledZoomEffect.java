/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.effects.effect;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public class ScaledZoomEffect implements ZoomEffect {
    public float getBobViewMultiplier(Zoom zoom) {
        return zoom.isZooming() ? zoom.getMultiplier() : 1.0F;
    }

    public float getMouseMultiplier(Zoom zoom) {
        return zoom.isZooming() ? zoom.getMultiplier() : 1.0F;
    }
}
