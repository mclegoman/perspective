/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.shaders;

import com.mclegoman.perspective.client.events.PerspectiveExecute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@Inject(method = "use", at = @At("HEAD"))
	private void perspective$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		PerspectiveExecute.onStartItemUse((ItemStack)(Object)this, world, user, hand);
	}
	@Inject(method = "finishUsing", at = @At("HEAD"))
	private void perspective$finishUsing(World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		PerspectiveExecute.onFinishItemUse((ItemStack)(Object)this, world, user);
	}
}
