/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.tropical_fish;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TropicalFishColorFeatureRenderer;
import net.minecraft.client.render.entity.state.TropicalFishEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = TropicalFishColorFeatureRenderer.class)
public class TropicalFishColorFeatureRendererMixin {
	@Mutable @Shadow @Final private static Identifier KOB_TEXTURE;
	@Mutable @Shadow @Final private static Identifier SUNSTREAK_TEXTURE;
	@Mutable @Shadow @Final private static Identifier SNOOPER_TEXTURE;
	@Mutable @Shadow @Final private static Identifier DASHER_TEXTURE;
	@Mutable @Shadow @Final private static Identifier BRINELY_TEXTURE;
	@Mutable @Shadow @Final private static Identifier SPOTTY_TEXTURE;
	@Mutable @Shadow @Final private static Identifier FLOPPER_TEXTURE;
	@Mutable @Shadow @Final private static Identifier STRIPEY_TEXTURE;
	@Mutable @Shadow @Final private static Identifier GLITTER_TEXTURE;
	@Mutable @Shadow @Final private static Identifier BLOCKFISH_TEXTURE;
	@Mutable @Shadow @Final private static Identifier BETTY_TEXTURE;
	@Mutable @Shadow @Final private static Identifier CLAYFISH_TEXTURE;
	@Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/TropicalFishEntityRenderState;FF)V", at = @At("RETURN"))
	private void perspective$render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, TropicalFishEntityRenderState state, float f, float g, CallbackInfo ci) {
		// TODO: Add entity specific.
		if (state.variety.equals(TropicalFishEntity.Variety.KOB))
			KOB_TEXTURE = TexturedEntity.getTexture(state, "", "_kob", Identifier.of("textures/entity/fish/tropical_a_pattern_1.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.SUNSTREAK))
			SUNSTREAK_TEXTURE = TexturedEntity.getTexture(state, "", "_sunstreak", Identifier.of("textures/entity/fish/tropical_a_pattern_2.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.SNOOPER))
			SNOOPER_TEXTURE = TexturedEntity.getTexture(state, "", "_snooper", Identifier.of("textures/entity/fish/tropical_a_pattern_3.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.DASHER))
			DASHER_TEXTURE = TexturedEntity.getTexture(state, "", "_dasher", Identifier.of("textures/entity/fish/tropical_a_pattern_4.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.BRINELY))
			BRINELY_TEXTURE = TexturedEntity.getTexture(state, "", "_brinely", Identifier.of("textures/entity/fish/tropical_a_pattern_5.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.SPOTTY))
			SPOTTY_TEXTURE = TexturedEntity.getTexture(state, "", "_spotty", Identifier.of("textures/entity/fish/tropical_a_pattern_6.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.FLOPPER))
			FLOPPER_TEXTURE = TexturedEntity.getTexture(state, "", "_flopper", Identifier.of("textures/entity/fish/tropical_b_pattern_1.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.STRIPEY))
			STRIPEY_TEXTURE = TexturedEntity.getTexture(state, "", "_stripey", Identifier.of("textures/entity/fish/tropical_b_pattern_2.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.GLITTER))
			GLITTER_TEXTURE = TexturedEntity.getTexture(state, "", "_glitter", Identifier.of("textures/entity/fish/tropical_b_pattern_3.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.BLOCKFISH))
			BLOCKFISH_TEXTURE = TexturedEntity.getTexture(state, "", "_blockfish", Identifier.of("textures/entity/fish/tropical_b_pattern_4.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.BETTY))
			BETTY_TEXTURE = TexturedEntity.getTexture(state, "", "_betty", Identifier.of("textures/entity/fish/tropical_b_pattern_5.png"));
		else if (state.variety.equals(TropicalFishEntity.Variety.CLAYFISH))
			CLAYFISH_TEXTURE = TexturedEntity.getTexture(state, "", "_clayfish", Identifier.of("textures/entity/fish/tropical_b_pattern_6.png"));
	}
}