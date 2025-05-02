/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PerspectiveExecute extends com.mclegoman.luminance.client.events.Execute {
	public static void onStartItemUse(ItemStack stack, World world, PlayerEntity user, Hand hand) {
		PerspectiveEvents.OnStartItemUse.registry.forEach((id, runnable) -> {
			try {
				runnable.run(stack, world, user, hand);
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to execute OnStartItemUse event with id: {}: {}", id, error));
			}
		});
	}
	public static void onFinishItemUse(ItemStack stack, World world, LivingEntity user) {
		PerspectiveEvents.OnFinishItemUse.registry.forEach((id, runnable) -> {
			try {
				runnable.run(stack, world, user);
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to execute OnFinishItemUse event with id: {}: {}", id, error));
			}
		});
	}
}
