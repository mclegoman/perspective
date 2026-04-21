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

public interface Zoom {
    float getPreviousMultiplier();
    float getMultiplier();
    void setPreviousMultiplier(float value);
    void setMultiplier(float value);
    boolean isZooming();
    ZoomScale getScale();
    ZoomTransition getTransition();
    ZoomEffect getEffect();
    float getZoomAmount();
    float getTransitionSpeedOut();
    float getTransitionSpeedIn();
    void update();
    void onTickClient(Minecraft minecraft);
    void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker);
}
