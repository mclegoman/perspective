/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.effects.effect;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public interface ZoomEffect {
    float getBobViewMultiplier(Zoom zoom);
    float getMouseMultiplier(Zoom zoom);
}
