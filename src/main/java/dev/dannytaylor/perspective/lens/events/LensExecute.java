/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.events.CoreExecute;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.util.SmoothDouble;

public class LensExecute extends CoreExecute {
    public static double mouseDelta;
    private static final SmoothDouble smoothTurnX = new SmoothDouble();
    private static final SmoothDouble smoothTurnY = new SmoothDouble();

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

    public static double updateXSensitivity(double x) {
        if (ClientData.minecraft.player != null) {
            float multiplier = Math.max(ZoomRegistry.getCombinedMouseMultiplier(), 0.001F);
            if (ZoomRegistry.isCinematic()) {
                double sensitivity = getSensitivity();
                x = smoothTurnX.getNewDeltaValue(x * sensitivity, mouseDelta * sensitivity);
            }
            if (ZoomRegistry.shouldMouseXUseCos()) {
                double angle = Mth.cos((ClientData.minecraft.player.getXRot() / 180.0F) * Mth.PI);
                x = (x * (1.0F / Math.max((angle < 0) ? angle * -1.0F : angle, (Math.max(multiplier, 0.0F) + 1.0F) / 11.0F))) * multiplier;
            } else x *= multiplier;
        }
        return x;
    }

    public static double updateYSensitivity(double y) {
        if (ClientData.minecraft.player != null) {
            if (ZoomRegistry.isCinematic()) {
                double sensitivity = getSensitivity();
                y = smoothTurnY.getNewDeltaValue(y * sensitivity, mouseDelta * sensitivity);
            }
            y *= Math.max(ZoomRegistry.getCombinedMouseMultiplier(), 0.001F);
        }
        return y;
    }

    public static void resetCinematic() {
        smoothTurnX.reset();
        smoothTurnY.reset();
    }

    private static double getSensitivity() {
        return Math.pow(ClientData.minecraft.options.sensitivity().get() * 0.6000000238418579F + 0.20000000298023224F, 3.0F) * 8.0F;
    }
}
