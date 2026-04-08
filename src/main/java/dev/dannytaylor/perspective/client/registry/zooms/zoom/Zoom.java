/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;

public interface Zoom {
    float getPreviousMultiplier();
    float getMultiplier();
    void setPreviousMultiplier(float value);
    void setMultiplier(float value);
    boolean isZooming();
    ZoomScale getScale();
    float getZoomAmount();
    void update();
}
