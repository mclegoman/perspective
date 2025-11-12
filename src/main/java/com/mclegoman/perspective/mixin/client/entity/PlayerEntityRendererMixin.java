/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.contributor.Contributor;
import com.mclegoman.perspective.client.entity.entity.PerspectivePlayerEntity;
import com.mclegoman.perspective.client.entity.states.PerspectivePlayerRenderState;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
	@Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void perspective$updateRenderState(AbstractClientPlayerEntity entity, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
		if (((PerspectivePlayerRenderState)state).perspective$getRandom() == null) ((PerspectivePlayerRenderState)state).perspective$setRandom(new Random(entity.getUuid().getLeastSignificantBits()));
		if (Contributor.canBlink(((PerspectiveRenderState)state).perspective$getUUID().toString())) {
			float prevBlink = ((PerspectivePlayerEntity)entity).perspective$getPrevBlink();
			if (!((PerspectivePlayerRenderState)state).perspective$getBlinking()) {
				if (prevBlink > 40) {
					if (prevBlink > 200 || ((PerspectivePlayerRenderState)state).perspective$getRandom().nextBoolean()) {
						((PerspectivePlayerRenderState)state).perspective$setBlinking(true);
						((PerspectivePlayerEntity)entity).perspective$setPrevBlink(0);
					}
				}
			} else {
				if (prevBlink > 2) {
					if (prevBlink > 6 || ((PerspectivePlayerRenderState)state).perspective$getRandom().nextBoolean()) {
						((PerspectivePlayerRenderState) state).perspective$setBlinking(false);
						((PerspectivePlayerEntity) entity).perspective$setPrevBlink(0);
					}
				}
			}
		}
	}
}
