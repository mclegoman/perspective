/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.state.MooshroomEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.Optional;

public class MooshroomOverlayFeatureRenderer<T extends MooshroomEntityRenderState, M extends CowEntityModel> extends FeatureRenderer<T, M> {
	private final M model;
	private final M babyModel;
	public MooshroomOverlayFeatureRenderer(FeatureRendererContext<T, M> context, M model, M babyModel) {
		super(context);
		this.model = model;
		this.babyModel = babyModel;
	}
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T state, float limbAngle, float limbDistance) {
		M entityModel = state.baby ? this.babyModel : this.model;
		entityModel.setAngles(state);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(getFinalTexture(state)));
		entityModel.render(matrices, vertexConsumer, light, LivingEntityRenderer.getOverlay(state, 0.0F));
	}
	public Identifier getFinalTexture(MooshroomEntityRenderState state) {
		boolean isTexturedEntity = true;
		Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(state);
		Identifier defaultId = Identifier.of("minecraft", "textures/state/mooshroom/" + state.type.asString().toLowerCase() + "_mooshroom_overlay.png");
		if (entityData.isPresent()) {
			JsonObject entitySpecific = entityData.get().getEntitySpecific();
			if (entitySpecific != null) {
				String type = String.valueOf(state.type).toLowerCase();
				if (entitySpecific.has(type)) {
					JsonObject typeRegistry = JsonHelper.getObject(entitySpecific, String.valueOf(state.type).toLowerCase());
					if (typeRegistry != null) {
						isTexturedEntity = JsonHelper.getBoolean(typeRegistry, "enabled", true);
					}
				}
			}
			if (isTexturedEntity) return TexturedEntity.getTexture(state, state.type.asString().toLowerCase() + "_", "_overlay", defaultId);
		}
		return defaultId;
	}
}