/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;

public class PerspectiveZoom extends AbstractZoom {
    public boolean isZooming() {
        return PerspectiveConfig.config.zoom.enabled.value(); // todo: && keybindings
    }

    @Override
    public ZoomScale getScale() {
        return Events.ZoomScales.get(PerspectiveConfig.config.zoom.scaleType.value().getIdentifier());
    }

    @Override
    public float getZoomAmount() {
        return PerspectiveConfig.config.zoom.amount.value();
    }
}
