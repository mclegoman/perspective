/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.iron_golem;

import com.google.common.collect.ImmutableMap;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.Cracks;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(priority = 100, value = IronGolemCrackFeatureRenderer.class)
public class IronGolemCrackFeatureRendererMixin {
	@Mutable
	@Shadow @Final private static Map<Cracks.CrackLevel, Identifier> CRACK_TEXTURES;
	@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;FF)V")
	private void perspective$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IronGolemEntityRenderState ironGolemEntityRenderState, float f, float g, CallbackInfo ci) {
		if (ironGolemEntityRenderState != null) {
			CRACK_TEXTURES = ImmutableMap.of(
					Cracks.CrackLevel.LOW, TexturedEntity.getTexture(ironGolemEntityRenderState, "", "_crackiness_low", Identifier.of("textures/entity/iron_golem/iron_golem_crackiness_low.png")),
					Cracks.CrackLevel.MEDIUM, TexturedEntity.getTexture(ironGolemEntityRenderState, "", "_crackiness_medium", Identifier.of("textures/entity/iron_golem/iron_golem_crackiness_medium.png")),
					Cracks.CrackLevel.HIGH, TexturedEntity.getTexture(ironGolemEntityRenderState, "", "_crackiness_high", Identifier.of("textures/entity/iron_golem/iron_golem_crackiness_high.png"))
			);
		}
	}
}