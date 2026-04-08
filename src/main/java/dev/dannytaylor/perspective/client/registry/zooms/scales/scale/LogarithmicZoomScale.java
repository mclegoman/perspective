/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.scales.scale;

public class LogarithmicZoomScale extends AbstractZoomScale {
    public float update(float zoomLevel) {
        return (float) (1.0F - (Math.log(zoomLevel + 1.0F) / Math.log(100.0 + 1.0F)));
    }
}
