/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.ZoomTransition;

public interface Zoom {
    float getPreviousMultiplier();
    float getMultiplier();
    void setPreviousMultiplier(float value);
    void setMultiplier(float value);
    boolean isZooming();
    ZoomScale getScale();
    ZoomTransition getTransition();
    float getZoomAmount();
    float getTransitionSpeedOut();
    float getTransitionSpeedIn();
    void update();
}
