/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.registry.zooms.ZoomRegistry;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Camera;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Execute extends com.mclegoman.luminance.client.events.Execute {
    public static void onClientStartItemUse(ItemStack stack, Level level, Player user, InteractionHand hand) {
        Events.OnClientStartItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user, hand);
            } catch (Exception error) {
                Log.error("Failed to execute OnClientStartItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void onClientFinishItemUse(ItemStack stack, Level level, LivingEntity user) {
        Events.OnClientFinishItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user);
            } catch (Exception error) {
                Log.error("Failed to execute OnClientFinishItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void updateZoomMultipliers() {
        for (Zoom zoom : Events.Zooms.registry.values()) zoom.update();
    }

    public static float getFov(float fov, Camera camera, float tickDelta) {
        if (camera != null) {
            ZoomRegistry.handFov = fov;
            float updatedFov = fov;
            for (Zoom zoom : Events.Zooms.registry.values()) updatedFov = zoom.getScale().getLimitFov(zoom.getTransition().updateFov(updatedFov, zoom, tickDelta));
            return updatedFov;
        }
        return fov;
    }
}
