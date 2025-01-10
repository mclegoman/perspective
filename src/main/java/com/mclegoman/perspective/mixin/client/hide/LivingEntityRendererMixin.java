/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.hide;

import com.mclegoman.perspective.client.entity.PerspectiveRenderState;
import com.mclegoman.perspective.config.ConfigHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.hide.HideNameTagsDataLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
	@Inject(method = "hasLabel(Lnet/minecraft/entity/LivingEntity;D)Z", at = @At("HEAD"), cancellable = true)
	private void perspective$hide_nametag(T entity, double d, CallbackInfoReturnable<Boolean> cir) {
		if (ClientData.minecraft.gameRenderer.isRenderingPanorama() || (boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.normal, "hide_nametags") || (entity instanceof PlayerEntity && HideNameTagsDataLoader.REGISTRY.contains(String.valueOf((((PlayerEntity) entity).getGameProfile().getId())))))
			cir.setReturnValue(false);
		if (entity instanceof PlayerEntity) {
			if (Hide.shouldHidePlayer(entity.getUuid())) cir.setReturnValue(false);
		}
	}
	@Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
	private void perspective$hide_nametag(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		if (livingEntityRenderState instanceof PlayerEntityRenderState && Hide.shouldHidePlayer(((PerspectiveRenderState)livingEntityRenderState).perspective$getUUID())) ci.cancel();
	}
}