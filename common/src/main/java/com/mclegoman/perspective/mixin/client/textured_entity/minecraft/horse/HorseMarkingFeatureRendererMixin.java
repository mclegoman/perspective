/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.horse;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HorseMarkingFeatureRenderer;
import net.minecraft.client.render.entity.state.HorseEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseMarking;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(priority = 100, value = HorseMarkingFeatureRenderer.class)
public class HorseMarkingFeatureRendererMixin {
	@Shadow @Final private static Map<HorseMarking, Identifier> TEXTURES;
	@Unique private HorseEntityRenderState state;
	@Inject(method = "<init>", at = @At("RETURN"))
	private void perspective$init(FeatureRendererContext featureRendererContext, CallbackInfo ci) {
		TEXTURES.remove(HorseMarking.NONE, null);
		TEXTURES.putIfAbsent(HorseMarking.NONE, Identifier.of("textures/entity/horse/horse_markings_none.png"));
	}
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/HorseEntityRenderState;FF)V", at = @At(value = "HEAD"))
	public void perspective$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, HorseEntityRenderState horseEntityRenderState, float f, float g, CallbackInfo ci) {
		state = horseEntityRenderState;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/HorseEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityTranslucent(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer perspective$getEntityTranslucent(RenderLayer renderLayer) {
		return RenderLayer.getEntityTranslucent(TexturedEntity.getTexture(state, "", perspective$getHorseMarking(state.marking), TEXTURES.get(state.marking)));
	}
	@Unique
	private String perspective$getHorseMarking(HorseMarking marking) {
		if (marking.equals(HorseMarking.WHITE)) return "_markings_white";
		else if (marking.equals(HorseMarking.WHITE_FIELD)) return "_markings_whitefield";
		else if (marking.equals(HorseMarking.WHITE_DOTS)) return "_markings_whitedots";
		else if (marking.equals(HorseMarking.BLACK_DOTS)) return "_markings_blackdots";
		else return "_markings_none";
	}
}