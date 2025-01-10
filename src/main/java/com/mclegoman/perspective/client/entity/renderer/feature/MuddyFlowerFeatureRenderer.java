/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.model.MuddyFlowerModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MuddyFlowerFeatureRenderer<S extends PigEntityRenderState, P extends PigEntityModel> extends FeatureRenderer<S, P> {
	private final MuddyFlowerModel<S> model;
	private final Identifier texture;
	public MuddyFlowerFeatureRenderer(FeatureRendererContext<S, P> context, MuddyFlowerModel<S> model, Identifier texture) {
		super(context);
		this.model = model;
		this.texture = texture;
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TexturedEntity.getTexture(state, "", "_muddy_flower", this.texture)));
		this.model.render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(state, 0.0F), this.getContextModel());
	}
}
