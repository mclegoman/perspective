/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.model;

import com.mclegoman.perspective.client.entity.states.PerspectiveLivingRenderState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class LivingEntityCapeModel<T extends LivingEntityRenderState> extends EntityModel<T> {
	private final ModelPart cloak;
	public LivingEntityCapeModel(ModelPart root) {
		this(root, RenderLayer::getEntityTranslucent);
	}
	public LivingEntityCapeModel(ModelPart root, Function<Identifier, RenderLayer> renderLayerFactory) {
		super(root, renderLayerFactory);
		this.cloak = root.getChild("cloak");
	}
	public static ModelData getModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("cloak", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -1.0F, 10.0F, 16.0F, 1.0F, dilation, 1.0F, 0.5F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return modelData;
	}
	public void renderCape(MatrixStack matrices, VertexConsumer vertices, LivingEntityRenderState state, int light, int overlay) {
		if (((PerspectiveLivingRenderState)state).perspective$getChestEmpty()) {
			if (state.sneaking) {
				this.cloak.pivotZ = 1.4F;
				this.cloak.pivotY = 1.85F;
			} else {
				this.cloak.pivotZ = 0.0F;
				this.cloak.pivotY = 0.0F;
			}
		} else if (state.sneaking) {
			this.cloak.pivotZ = 0.3F;
			this.cloak.pivotY = 0.8F;
		} else {
			this.cloak.pivotZ = -1.1F;
			this.cloak.pivotY = -0.85F;
		}
		this.cloak.render(matrices, vertices, light, overlay);
	}
}