/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.leash_knot;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LeashKnotEntityRenderer;
import net.minecraft.client.render.entity.model.LeashKnotEntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
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

@Mixin(priority = 100, value = LeashKnotEntityRenderer.class)
public class LeashKnotEntityRendererMixin {
	@Shadow @Final private LeashKnotEntityModel field_53192;
	@Inject(at = @At("HEAD"), method = "render")
	private void perspective$getState(EntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		this.state = state;
	}
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/LeashKnotEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "render")
	private RenderLayer perspective$replaceTexture(LeashKnotEntityModel instance, Identifier identifier) {
		return this.field_53192.getLayer(this.state != null ? TexturedEntity.getTexture(this.state, identifier): identifier);
	}
	@Unique
	EntityRenderState state;
}