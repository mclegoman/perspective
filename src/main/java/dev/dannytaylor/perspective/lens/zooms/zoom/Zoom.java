/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.overlays.overlays.ZoomAV;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface Zoom {
    float getPreviousMultiplier();
    float getMultiplier();
    void setPreviousMultiplier(float value);
    void setMultiplier(float value);
    boolean isZooming();
    boolean wasZooming();
    void onStartZooming(Zoom zoom);
    void onFinishZooming(Zoom zoom);
    ZoomScale getScale();
    ZoomTransition getTransition();
    ZoomEffect getEffect();
    ZoomAV getAudioVisual();
    boolean shouldEffect();
    float getZoomAmount();
    void update();
    void draw(GuiGraphics graphics, DeltaTracker deltaTracker);
    void onTickClient();
    boolean isEnabled();
}
