/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.events.CoreExecute;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class LensExecute extends CoreExecute {
    public static void updateZoomMultipliers() {
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom.isEnabled()) zoom.update();
            else {
                zoom.setPreviousMultiplier(1.0F);
                zoom.setMultiplier(1.0F);
            }
        }
    }

    public static float getFov(float fov, Camera camera, float tickDelta) {
        if (camera != null) {
            ZoomRegistry.fov = fov;
            float updatedFov = fov;
            for (Zoom zoom : LensEvents.Zooms.registry.values()) {
                if (zoom != null && zoom.isEnabled() && zoom.getScale() != null && zoom.getTransition() != null) {
                    updatedFov = zoom.getScale().getLimitFov(zoom.getTransition().updateFov(updatedFov, zoom, tickDelta));
                }
            }
            return updatedFov;
        }
        return fov;
    }

    public static void renderZoom(GuiGraphics graphics, DeltaTracker deltaTracker) {
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom.isEnabled()) zoom.draw(graphics, deltaTracker);
        }
    }
}
