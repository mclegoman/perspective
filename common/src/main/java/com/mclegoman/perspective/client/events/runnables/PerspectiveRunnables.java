/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events.runnables;

import com.mclegoman.luminance.client.events.Runnables;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PerspectiveRunnables extends Runnables {
	public interface UseItem {
		void run(ItemStack stack, World world, PlayerEntity user, Hand hand);
	}
	public interface FinishUsingItem {
		void run(ItemStack stack, World world, LivingEntity user);
	}
}
