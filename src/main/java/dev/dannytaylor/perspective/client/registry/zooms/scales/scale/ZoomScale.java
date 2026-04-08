/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.scales.scale;

public interface ZoomScale {
    float getLimitFov(float fov);
    float update(float zoomAmount);
}
