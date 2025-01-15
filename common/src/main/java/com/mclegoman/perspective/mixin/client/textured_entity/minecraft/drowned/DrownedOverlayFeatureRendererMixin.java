/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.drowned;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer.class)
public class DrownedOverlayFeatureRendererMixin {
	@Mutable @Shadow @Final private static Identifier SKIN;
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/ZombieEntityRenderState;FF)V", at = @At("RETURN"))
	private void perspective$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ZombieEntityRenderState zombieEntityRenderState, float f, float g, CallbackInfo ci) {
		SKIN = TexturedEntity.getTexture(zombieEntityRenderState, "", "_outer_layer", Identifier.of("textures/entity/zombie/drowned_outer_layer.png"));
	}
}