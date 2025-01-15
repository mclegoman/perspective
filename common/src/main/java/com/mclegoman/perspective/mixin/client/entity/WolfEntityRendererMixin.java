/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveTamedRenderState;
import net.minecraft.client.render.entity.CatEntityRenderer;
import net.minecraft.client.render.entity.state.CatEntityRenderState;
import net.minecraft.entity.passive.CatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatEntityRenderer.class)
public abstract class WolfEntityRendererMixin {
	@Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/CatEntity;Lnet/minecraft/client/render/entity/state/CatEntityRenderState;F)V", at = @At("TAIL"))
	private void perspective$updateRenderState(CatEntity entity, CatEntityRenderState state, float f, CallbackInfo ci) {
		((PerspectiveTamedRenderState)state).perspective$setTamed(entity.isTamed());
	}
}
