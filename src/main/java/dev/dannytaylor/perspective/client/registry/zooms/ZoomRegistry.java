/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms;

import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.scales.ZoomScales;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.PerspectiveZoom;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;

public class ZoomRegistry {
    public static Zoom MAIN = register(Identifiers.MAIN, new PerspectiveZoom());

    public static void onInitializeClient() {
        Log.info("Initializing zoom registries...");
        try {
            ZoomScales.onInitializeClient();
        } catch (Exception error) {
            Log.error("Failed to initialize zoom registries: {}", error);
        }
    }

    public static Zoom register(Identifier identifier, Zoom zoom) {
        Events.Zooms.register(identifier, zoom);
        return zoom;
    }
}
