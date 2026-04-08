package dev.dannytaylor.perspective.client.registry.zooms.effects.effect;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public class ScaledZoomEffect implements ZoomEffect {
    public float getBobViewMultiplier(Zoom zoom) {
        return zoom.isZooming() ? zoom.getMultiplier() : 1.0F;
    }

    public float getMouseMultiplier(Zoom zoom) {
        return zoom.isZooming() ? zoom.getMultiplier() : 1.0F;
    }
}
