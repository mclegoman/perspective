/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.creaking;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.CreakingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = CreakingEntityRenderer.class)
public abstract class CreakingEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(LivingEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(state, cir.getReturnValue()));
	}
}