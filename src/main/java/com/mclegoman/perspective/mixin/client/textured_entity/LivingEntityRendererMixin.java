/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "shouldFlipUpsideDown", cancellable = true)
	private static void perspective$shouldFlipUpsideDown(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		Optional<TexturedEntityData> data = TexturedEntity.getEntity(entity);
		if (data.isPresent()) {
			boolean shouldFlip = cir.getReturnValue();
			if (data.get().getFlip()) shouldFlip = !shouldFlip;
			cir.setReturnValue(shouldFlip);
		}
	}
}