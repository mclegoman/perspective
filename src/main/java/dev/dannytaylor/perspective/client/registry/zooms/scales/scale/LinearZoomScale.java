/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.scales.scale;

public class LinearZoomScale extends AbstractZoomScale {
    public float update(float zoomLevel) {
        return 1.0F - (zoomLevel / 100.0F);
    }
}
