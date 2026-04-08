package dev.dannytaylor.perspective.client.registry.zooms.effects.effect;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public interface ZoomEffect {
    float getBobViewMultiplier(Zoom zoom);
    float getMouseMultiplier(Zoom zoom);
}
