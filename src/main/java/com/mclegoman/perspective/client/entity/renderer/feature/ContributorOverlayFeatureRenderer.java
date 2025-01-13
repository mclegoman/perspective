/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.contributor.Contributor;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.events.AprilFoolsPrankDataLoader;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ContributorOverlayFeatureRenderer<P extends PlayerEntityRenderState, M extends PlayerEntityModel> extends FeatureRenderer<P, M> {
	private final M model;
	public ContributorOverlayFeatureRenderer(FeatureRendererContext<P, M> context, M model) {
		super(context);
		this.model = model;
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, P state, float limbAngle, float limbDistance) {
		String uuid = (PerspectiveConfig.config.allowAprilFools.value() && AprilFoolsPrank.isAprilFools() && !AprilFoolsPrankDataLoader.registry.isEmpty()) ? AprilFoolsPrankDataLoader.contributor : String.valueOf(((PerspectiveRenderState)state).perspective$getUUID());
		if (Contributor.shouldOverlayTexture(uuid)) {
			Identifier texture = Contributor.getOverlayTexture(uuid);
			if (texture != null) {
				PlayerEntityModel playerModel = this.getContextModel();
				this.model.head.copyTransform(playerModel.head);
				this.model.hat.copyTransform(playerModel.hat);
				this.model.body.copyTransform(playerModel.body);
				this.model.rightArm.copyTransform(playerModel.rightArm);
				this.model.leftArm.copyTransform(playerModel.leftArm);
				this.model.rightLeg.copyTransform(playerModel.rightLeg);
				this.model.leftLeg.copyTransform(playerModel.leftLeg);
				this.model.leftSleeve.copyTransform(playerModel.leftSleeve);
				this.model.rightSleeve.copyTransform(playerModel.rightSleeve);
				this.model.leftPants.copyTransform(playerModel.leftPants);
				this.model.rightPants.copyTransform(playerModel.rightPants);
				this.model.jacket.copyTransform(playerModel.jacket);
				this.model.head.visible = playerModel.head.visible;
				this.model.hat.visible = playerModel.hat.visible;
				this.model.body.visible = playerModel.body.visible;
				this.model.rightArm.visible = playerModel.rightArm.visible;
				this.model.leftArm.visible = playerModel.leftArm.visible;
				this.model.rightLeg.visible = playerModel.rightLeg.visible;
				this.model.leftLeg.visible = playerModel.leftLeg.visible;
				this.model.leftSleeve.visible = playerModel.leftSleeve.visible;
				this.model.rightSleeve.visible = playerModel.rightSleeve.visible;
				this.model.leftPants.visible = playerModel.leftPants.visible;
				this.model.rightPants.visible = playerModel.rightPants.visible;
				this.model.jacket.visible = playerModel.jacket.visible;
				playerModel.copyTransforms(this.model);
				this.model.setAngles(state);
				this.model.render(matrices, vertexConsumers.getBuffer(Contributor.isEmissive(uuid) ? RenderLayer.getEntityTranslucentEmissive(texture) : RenderLayer.getEntityTranslucent(texture)), light, LivingEntityRenderer.getOverlay(state, 0.0F));
			}
		}
	}
}