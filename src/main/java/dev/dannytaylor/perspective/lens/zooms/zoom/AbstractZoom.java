/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

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

    public ZoomTransition getTransition() {
        return null;
    }

    public ZoomEffect getEffect() {
        return null;
    }

    public float getZoomAmount() {
        return 0.0F;
    }

    public float getTransitionSpeedOut() {
        return 1.0F;
    }

    public float getTransitionSpeedIn() {
        return 1.0F;
    }

    public void update() {
        this.setPreviousMultiplier(this.getMultiplier());
        this.setMultiplier(this.isZooming() && this.getScale() != null ? this.getScale().update(this.getZoomAmount()) : 1.0F);
        if (this.getTransition() != null) this.setMultiplier(this.getTransition().updateMultiplier(this));
    }

    public void onTickClient(Minecraft minecraft) {
    }

    public void draw(GuiGraphics graphics, DeltaTracker deltaTracker) {
    }
}
