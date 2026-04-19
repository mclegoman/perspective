package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.events.CoreExecute;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.Camera;

public class LensExecute extends CoreExecute {
    public static void updateZoomMultipliers() {
        for (Zoom zoom : LensEvents.Zooms.registry.values()) zoom.update();
    }

    public static float getFov(float fov, Camera camera, float tickDelta) {
        if (camera != null) {
            ZoomRegistry.fov = fov;
            float updatedFov = fov;
            for (Zoom zoom : LensEvents.Zooms.registry.values()) {
                if (zoom != null && zoom.getScale() != null && zoom.getTransition() != null) {
                    updatedFov = zoom.getScale().getLimitFov(zoom.getTransition().updateFov(updatedFov, zoom, tickDelta));
                }
            }
            return updatedFov;
        }
        return fov;
    }
}
