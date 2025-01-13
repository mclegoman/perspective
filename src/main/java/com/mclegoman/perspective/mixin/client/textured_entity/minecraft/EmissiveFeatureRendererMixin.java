/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EmissiveFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = EmissiveFeatureRenderer.class)
public abstract class EmissiveFeatureRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<S>> {
	@Mutable @Shadow @Final private Identifier texture;
	@Inject(at = @At("RETURN"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V", cancellable = true)
	private void perspective$replaceTexture(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S livingEntityRenderState, float f, float g, CallbackInfo ci) {
		this.texture = TexturedEntity.getTexture(livingEntityRenderState, "", "_emissive", this.texture);
	}
}