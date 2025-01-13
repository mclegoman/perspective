/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.skeleton;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.SkeletonOverlayFeatureRenderer;
import net.minecraft.client.render.entity.state.SkeletonEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = SkeletonOverlayFeatureRenderer.class)
public class SkeletonOverlayFeatureRendererMixin<S extends SkeletonEntityRenderState> {
	@Mutable @Shadow @Final private Identifier texture;
	@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/SkeletonEntityRenderState;FF)V")
	private void perspective$getTexture(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S skeletonEntityRenderState, float f, float g, CallbackInfo ci) {
		Identifier entityType = Registries.ENTITY_TYPE.getId(((PerspectiveRenderState)skeletonEntityRenderState).perspective$getType());
		texture = TexturedEntity.getTexture(skeletonEntityRenderState, "", "_overlay", Identifier.of(entityType.getNamespace(), "textures/entity/skeleton/" + entityType.getPath() + "_overlay.png"));
	}
}