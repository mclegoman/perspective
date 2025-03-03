/*
    perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.contributor.Contributor;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.PlayerFaceModel;
import com.mclegoman.perspective.client.entity.states.PerspectivePlayerRenderState;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PlayerFaceFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
	private final PlayerFaceModel<PlayerEntityRenderState> model;
	public PlayerFaceFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, LoadedEntityModels entityModels) {
		super(context);
		this.model = new PlayerFaceModel<>(entityModels.getModelPart(EntityModels.playerFace));
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityRenderState state, float limbAngle, float limbDistance) {
		if (!state.invisible) {
			if (((PerspectivePlayerRenderState)state).perspective$getBlinking()) {
				// We don't need to check if we can blink, since we do that when setting blinking.
				//if (Contributor.getContributorData(uuid).getShouldBlink()) {}
				this.model.face.copyTransform(this.getContextModel().head);
				Identifier texture = Contributor.getBlinkTexture(((PerspectiveRenderState)state).perspective$getUUID().toString(), state.skinTextures.texture());
				this.model.render(matrices, vertexConsumers.getBuffer(texture.equals(state.skinTextures.texture()) ? RenderLayer.getEntitySolid(texture) : RenderLayer.getEntityTranslucent(texture)), light, LivingEntityRenderer.getOverlay(state, 0.0F));
			}
		}
	}
}