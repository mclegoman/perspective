/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.entity.model.HalloweenHatModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class HalloweenHatFeatureRenderer<S extends PlayerEntityRenderState, P extends PlayerEntityModel> extends FeatureRenderer<S, P> {
	private final HalloweenHatModel<S> model;
	private final Identifier texture;
	public HalloweenHatFeatureRenderer(FeatureRendererContext<S, P> context, HalloweenHatModel<S> model, Identifier texture) {
		super(context);
		this.model = model;
		this.texture = texture;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
		matrices.push();
		matrices.scale(0.9375F, 0.9375F, 0.9375F);
		this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(texture)), light, OverlayTexture.DEFAULT_UV, this.getContextModel());
		matrices.pop();
	}
}
