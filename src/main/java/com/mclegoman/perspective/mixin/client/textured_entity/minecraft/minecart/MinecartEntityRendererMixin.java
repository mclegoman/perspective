/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.minecart;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.entity.vehicle.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = MinecartEntityRenderer.class)
public class MinecartEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(AbstractMinecartEntity entity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(entity, cir.getReturnValue()));
	}
}