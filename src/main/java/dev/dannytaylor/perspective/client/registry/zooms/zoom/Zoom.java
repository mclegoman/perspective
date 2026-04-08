/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.registry.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.Minecraft;

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
}
