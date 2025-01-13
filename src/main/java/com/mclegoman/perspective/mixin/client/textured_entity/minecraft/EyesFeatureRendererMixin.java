/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = EyesFeatureRenderer.class)
public class EyesFeatureRendererMixin<S extends EntityRenderState> {
	@Unique
	private EntityRenderState state;
	@Inject(method = "render", at = @At("HEAD"))
	public void perspective$getEntity(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance, CallbackInfo ci) {
		this.state = state;
	}
	@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/EyesFeatureRenderer;getEyesTexture()Lnet/minecraft/client/render/RenderLayer;"))
	public RenderLayer perspective$render(RenderLayer renderLayer) {
		Identifier texture = null;
		EntityType<?> entityType = ((PerspectiveRenderState)state).perspective$getType();
		if (entityType.equals(EntityType.ENDERMAN)) texture = Identifier.of("textures/entity/enderman/enderman_eyes.png");
		else if (entityType.equals(EntityType.SPIDER) || entityType.equals(EntityType.CAVE_SPIDER)) texture = Identifier.of("textures/entity/spider_eyes.png");
		else if (entityType.equals(EntityType.PHANTOM)) texture = Identifier.of("textures/entity/phantom_eyes.png");
		return texture != null ? RenderLayer.getEyes(TexturedEntity.getTexture(this.state, "", "_eyes", texture)) : renderLayer;
	}
}