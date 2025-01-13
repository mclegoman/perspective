/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.shulker_bullet;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ShulkerBulletEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerBulletEntityModel;
import net.minecraft.client.render.entity.state.ShulkerBulletEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = ShulkerBulletEntityRenderer.class)
public class ShulkerBulletEntityRendererMixin {
	@Shadow @Final private static Identifier TEXTURE;
	@Shadow @Final private ShulkerBulletEntityModel model;
	@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/render/entity/state/ShulkerBulletEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
	private void perspective$getState(ShulkerBulletEntityRenderState shulkerBulletEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		this.state = shulkerBulletEntityRenderState;
	}
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;", ordinal = 0), method = "render(Lnet/minecraft/client/render/entity/state/ShulkerBulletEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
	private VertexConsumer perspective$replaceTexture(VertexConsumerProvider vertexConsumerProvider, RenderLayer renderLayer) {
		return vertexConsumerProvider.getBuffer(this.state != null ? this.model.getLayer(TexturedEntity.getTexture(this.state, TEXTURE)) : renderLayer);
	}
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;", ordinal = 1), method = "render(Lnet/minecraft/client/render/entity/state/ShulkerBulletEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
	private VertexConsumer perspective$replaceTexture2(VertexConsumerProvider vertexConsumerProvider, RenderLayer renderLayer) {
		return vertexConsumerProvider.getBuffer(this.state != null ? RenderLayer.getEntityTranslucent(TexturedEntity.getTexture(this.state, TEXTURE)) : renderLayer);
	}
	@Unique private ShulkerBulletEntityRenderState state;
}