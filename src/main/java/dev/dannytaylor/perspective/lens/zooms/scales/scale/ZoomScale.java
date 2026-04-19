/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.scales.scale;

public interface ZoomScale {
    float getLimitFov(float fov);
    float update(float zoomAmount);
}
