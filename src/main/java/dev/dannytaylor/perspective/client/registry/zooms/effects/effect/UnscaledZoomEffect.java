package dev.dannytaylor.perspective.client.registry.zooms.effects.effect;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public class UnscaledZoomEffect implements ZoomEffect {
    public float getBobViewMultiplier(Zoom zoom) {
        return 1.0F;
    }

    public float getMouseMultiplier(Zoom zoom) {
        return 1.0F;
    }
}
