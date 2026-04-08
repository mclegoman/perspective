/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms;

import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.scales.ZoomScales;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.ZoomTransitions;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.DefaultZoom;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;

public class ZoomRegistry {
    public static Zoom MAIN = register(Identifiers.MAIN, new DefaultZoom(
            () -> PerspectiveConfig.config.zoom.enabled.value() && ClientData.minecraft.hasShiftDown(), //todo: keymappings
            () -> Events.ZoomScales.get(PerspectiveConfig.config.zoom.scaleType.value().getIdentifier()),
            () -> Events.ZoomTransitions.get(PerspectiveConfig.config.zoom.transition.value().getIdentifier()),
            PerspectiveConfig.config.zoom.amount::value,
            PerspectiveConfig.config.zoom.smoothSpeedOut::value,
            PerspectiveConfig.config.zoom.smoothSpeedIn::value
    ));

    public static float handFov = 70.0F;
    public static float zoomFov = 70.0F;

    public static void onInitializeClient() {
        Log.info("Initializing zoom registries...");
        try {
            ZoomScales.onInitializeClient();
            ZoomTransitions.onInitializeClient();
        } catch (Exception error) {
            Log.error("Failed to initialize zoom registries: {}", error);
        }
    }

    public static Zoom register(Identifier identifier, Zoom zoom) {
        Events.Zooms.register(identifier, zoom);
        return zoom;
    }
}
