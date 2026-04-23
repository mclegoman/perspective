/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective.lens.zooms.effects.effect.AbstractZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.AbstractZoomAV;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.ZoomAV;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.AbstractZoomScale;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.AbstractZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public abstract class AbstractZoom implements Zoom {
    private float previousMultiplier = 1.0F;
    private float multiplier = 1.0F;
    private boolean wasZooming;

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

    public boolean wasZooming() {
        return this.wasZooming;
    }

    public void onStartZooming(Zoom zoom) {
    }

    public void onFinishZooming(Zoom zoom) {
    }

    public ZoomScale getScale() {
        return new AbstractZoomScale() {
        };
    }

    public ZoomTransition getTransition() {
        return new AbstractZoomTransition() {
        };
    }

    public ZoomAV getAudioVisual() {
        return new AbstractZoomAV() {};
    }

    public ZoomEffect getEffect() {
        return new AbstractZoomEffect() {};
    }

    public boolean shouldEffect() {
        return this.isZooming();
    }

    public float getZoomAmount() {
        return 0.0F;
    }

    public void update() {
        this.setPreviousMultiplier(this.getMultiplier());
        this.setMultiplier(this.isZooming() && this.getScale() != null ? this.getScale().update(this.getZoomAmount()) : 1.0F);
        if (this.getTransition() != null) this.setMultiplier(this.getTransition().updateMultiplier(this));
    }

    public void draw(GuiGraphics graphics, DeltaTracker deltaTracker) {
        if (this.isEnabled()) {
            if (!this.wasZooming() && this.isZooming()) {
                if (this.getAudioVisual() != null) this.getAudioVisual().onStart(graphics, deltaTracker, this);
                this.onStartZooming(this);
                this.wasZooming = true;
            }
            if (this.getAudioVisual() != null) this.getAudioVisual().draw(graphics, deltaTracker, this);
            if (!this.isZooming() && this.wasZooming()) {
                if (this.getAudioVisual() != null) this.getAudioVisual().onFinish(graphics, deltaTracker, this);
                this.onFinishZooming(this);
                this.wasZooming = false;
            }
        } else {
            if (this.getAudioVisual() != null) this.getAudioVisual().onZoomDisabled(this);
            this.wasZooming = false;
        }
    }

    public void onTickClient() {
    }

    public boolean isCinematic(Zoom zoom) {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }
}
