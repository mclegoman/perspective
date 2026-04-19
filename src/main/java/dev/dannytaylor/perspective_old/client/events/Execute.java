/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.events;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.log.Log;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.Camera;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Execute extends com.mclegoman.luminance.client.events.Execute {
    public static void onClientStartItemUse(Log logger, ItemStack stack, Level level, Player user, InteractionHand hand) {
        Events.OnClientStartItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user, hand);
            } catch (Exception error) {
                logger.error("Failed to execute OnClientStartItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void onClientFinishItemUse(Log logger, ItemStack stack, Level level, LivingEntity user) {
        Events.OnClientFinishItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user);
            } catch (Exception error) {
                logger.error("Failed to execute OnClientFinishItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void updateZoomMultipliers() {
        for (Zoom zoom : Events.Zooms.registry.values()) zoom.update();
    }

    public static float getFov(float fov, Camera camera, float tickDelta) {
        if (camera != null) {
            ZoomRegistry.fov = fov;
            float updatedFov = fov;
            for (Zoom zoom : Events.Zooms.registry.values()) {
                if (zoom != null && zoom.getScale() != null && zoom.getTransition() != null) {
                    updatedFov = zoom.getScale().getLimitFov(zoom.getTransition().updateFov(updatedFov, zoom, tickDelta));
                }
            }
            return updatedFov;
        }
        return fov;
    }
}
