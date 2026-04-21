/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.effects.effect;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public abstract class AbstractZoomEffect implements ZoomEffect {
    public float getBobViewMultiplier(Zoom zoom) {
        return 1.0F;
    }

    public float getMouseMultiplier(Zoom zoom) {
        return 1.0F;
    }
}
