/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.scales.scale;

public class LinearZoomScale extends AbstractZoomScale {
    public float update(float zoomLevel) {
        return 1.0F - (zoomLevel / 100.0F);
    }
}
