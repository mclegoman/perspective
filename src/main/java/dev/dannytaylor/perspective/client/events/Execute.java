/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Execute extends com.mclegoman.luminance.client.events.Execute {
    public static void OnClientStartItemUse(ItemStack stack, Level level, Player user, InteractionHand hand) {
        Events.OnClientStartItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user, hand);
            } catch (Exception error) {
                Log.error("Failed to execute OnClientStartItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void OnClientFinishItemUse(ItemStack stack, Level level, LivingEntity user) {
        Events.OnClientFinishItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user);
            } catch (Exception error) {
                Log.error("Failed to execute OnClientFinishItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static boolean OnMouseScroll(long windowHandle, double horizontal, double vertical, ScrollWheelHandler scrollWheelHandler) {
        boolean shouldCancel = false;
        for (Identifier registry : Events.OnMouseScroll.registry.keySet()) {
            if (Events.OnMouseScroll.get(registry).call(windowHandle, horizontal, vertical, scrollWheelHandler)) shouldCancel = true;
        }
        return shouldCancel;
    }

    public static boolean OnMouseButton(long windowHandle, MouseButtonInfo mouseButtonInfo, @MouseButtonInfo.Action int action) {
        boolean shouldCancel = false;
        for (Identifier registry : Events.OnMouseButton.registry.keySet()) {
            if (Events.OnMouseButton.get(registry).call(windowHandle, mouseButtonInfo, action)) shouldCancel = true;
        }
        return shouldCancel;
    }
}
