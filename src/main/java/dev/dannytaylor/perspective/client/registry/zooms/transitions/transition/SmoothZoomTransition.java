package dev.dannytaylor.perspective.client.registry.zooms.transitions.transition;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;
import net.minecraft.util.Mth;

public class SmoothZoomTransition implements ZoomTransition {
    public float updateFov(float fov, Zoom zoom, float tickDelta) {
        return fov * Mth.lerp(tickDelta, zoom.getPreviousMultiplier(), zoom.getMultiplier());
    }

    public float updateMultiplier(Zoom zoom) {
        float speedMultiplier = ((zoom.getPreviousMultiplier() + zoom.getMultiplier()) * 0.5F);
        return Mth.lerp(zoom.getPreviousMultiplier() < speedMultiplier ? zoom.getTransitionSpeedOut() : zoom.getTransitionSpeedIn(), zoom.getPreviousMultiplier(), speedMultiplier);
    }
}
