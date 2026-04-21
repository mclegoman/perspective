/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions.transition;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public interface ZoomTransition {
    float updateFov(float fov, Zoom zoom, float tickDelta);
    float updateMultiplier(Zoom zoom);
    float getSpeedOut();
    float getSpeedIn();
    boolean isSpeedConfigEnabled();
}
