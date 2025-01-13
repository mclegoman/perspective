/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class OverlayFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends FeatureRenderer<S, M> {
	private final M model;
	private final M babyModel;
	private final Identifier texture;
	public OverlayFeatureRenderer(FeatureRendererContext<S, M> context, M model, M babyModel, Identifier texture) {
		super(context);
		this.model = model;
		this.babyModel = babyModel;
		this.texture = texture;
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
		M entityModel = state.baby ? this.babyModel : this.model;
		if (entityModel != null) {
			entityModel.setAngles(state);
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TexturedEntity.getTexture(state, "", "_overlay", this.texture)));
			entityModel.render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(state, 0.0F));
		}
	}
}
