/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.magma_cube;

import com.mclegoman.perspective.client.textured_entity.TexturedEntity;
import net.minecraft.client.render.entity.MagmaCubeEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = MagmaCubeEntityRenderer.class)
public class MagmaCubeEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(Entity entity, CallbackInfoReturnable<Identifier> cir) {
		if (entity instanceof MagmaCubeEntity)
			cir.setReturnValue(TexturedEntity.getTexture(entity, "minecraft:magma_cube", "", cir.getReturnValue()));
	}
}