/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import net.minecraft.client.render.entity.FrogEntityRenderer;
import net.minecraft.client.render.entity.state.FrogEntityRenderState;
import net.minecraft.entity.passive.FrogEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrogEntityRenderer.class)
public abstract class FrogEntityRendererMixin {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/FrogEntity;Lnet/minecraft/client/render/entity/state/FrogEntityRenderState;F)V", at = @At("TAIL"))
	private void perspective$updateRenderState(FrogEntity entity, FrogEntityRenderState state, float f, CallbackInfo ci) {
		((PerspectiveVariantRenderState)state).perspective$setVariantId(entity.getVariant().getIdAsString());
	}
}
