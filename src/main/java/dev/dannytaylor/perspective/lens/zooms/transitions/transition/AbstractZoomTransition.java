/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions.transition;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public abstract class AbstractZoomTransition implements ZoomTransition {
    public float updateFov(float fov, Zoom zoom, float tickDelta) {
        return fov * zoom.getMultiplier();
    }

    public float updateMultiplier(Zoom zoom) {
        return zoom.getMultiplier();
    }

    public float getSpeedOut(Zoom zoom) {
        return 1.0F;
    }

    public float getSpeedIn(Zoom zoom) {
        return 1.0F;
    }

    public boolean isInstant(Zoom zoom) {
        return true;
    }

    public boolean isSpeedConfigEnabled(Zoom zoom) {
        return false;
    }
}
