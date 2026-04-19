/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.scales.scale;

public abstract class AbstractZoomScale implements ZoomScale {
    public float getLimitFov(float fov) {
        return Math.clamp(fov, 0.1F, 179.9F);
    }

    public float update(float zoomLevel) {
        return 1.0F;
    }
}
