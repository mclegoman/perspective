/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveGoatRenderState;
import net.minecraft.client.render.entity.GoatEntityRenderer;
import net.minecraft.client.render.entity.state.GoatEntityRenderState;
import net.minecraft.entity.passive.GoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GoatEntityRenderer.class)
public abstract class GoatEntityRendererMixin {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/GoatEntity;Lnet/minecraft/client/render/entity/state/GoatEntityRenderState;F)V", at = @At("TAIL"))
	private void perspective$updateRenderState(GoatEntity entity, GoatEntityRenderState state, float f, CallbackInfo ci) {
		((PerspectiveGoatRenderState)state).perspective$setScreaming(entity.isScreaming());
	}
}
