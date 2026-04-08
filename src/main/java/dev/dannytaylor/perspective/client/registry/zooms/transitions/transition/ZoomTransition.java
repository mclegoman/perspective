/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.transitions.transition;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public interface ZoomTransition {
    float updateFov(float fov, Zoom zoom, float tickDelta);
    float updateMultiplier(Zoom zoom);
}
