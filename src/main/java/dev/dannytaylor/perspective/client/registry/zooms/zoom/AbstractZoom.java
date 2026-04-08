/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;

public abstract class AbstractZoom implements Zoom {
    private float previousMultiplier = 1.0F;
    private float multiplier = 1.0F;

    public float getPreviousMultiplier() {
        return this.previousMultiplier;
    }

    public float getMultiplier() {
        return this.multiplier;
    }

    public void setPreviousMultiplier(float value) {
        this.previousMultiplier = value;
    }

    public void setMultiplier(float value) {
        this.multiplier = value;
    }

    public boolean isZooming() {
        return false;
    }

    public ZoomScale getScale() {
        return null;
    }

    public float getZoomAmount() {
        return 0;
    }

    public void update() {
        this.setPreviousMultiplier(this.getMultiplier());
        this.setMultiplier(this.isZooming() && this.getScale() != null ? this.getScale().update(this.getZoomAmount()) : 1.0F);
        // TODO: After update do smooth transitions!
        // This should be in it's own thing!
    }
}
