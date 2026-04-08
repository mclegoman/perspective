/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.effects.effect;

import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public interface ZoomEffect {
    float getBobViewMultiplier(Zoom zoom);
    float getMouseMultiplier(Zoom zoom);
}
