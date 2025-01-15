/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.parrot;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.ParrotEntityRenderer;
import net.minecraft.client.render.entity.feature.ShoulderParrotFeatureRenderer;
import net.minecraft.client.render.entity.state.ParrotEntityRenderState;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(priority = 100, value = ShoulderParrotFeatureRenderer.class)
public class ShoulderParrotFeatureRendererMixin {
	@Shadow @Final private ParrotEntityRenderState parrotState;
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/ParrotEntityRenderer;getTexture(Lnet/minecraft/entity/passive/ParrotEntity$Variant;)Lnet/minecraft/util/Identifier;"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/entity/passive/ParrotEntity$Variant;FFZ)V")
	private Identifier perspective$replaceTexture(ParrotEntity.Variant variant) {
		return TexturedEntity.getTexture(this.parrotState, ParrotEntityRenderer.getTexture(variant));
	}
}