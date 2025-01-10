/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class MuddyFlowerModel<T extends PigEntityRenderState> extends EntityModel<T> {
	private final ModelPart main;
	public MuddyFlowerModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.main = root.getChild("mud");
	}
	public static TexturedModelData getTexturedModelData() {
		return getTexturedModelData(new Dilation(0.0F));
	}
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData muddy_flower = modelPartData.addChild("mud", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -5.0F, -7.0F, 4.0F, 1.0F, 4.0F, dilation), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		muddy_flower.addChild("flower", ModelPartBuilder.create().uv(0, 1).cuboid(0.0F, -6.0F, -2.0F, 0.0F, 6.0F, 4.0F, dilation), ModelTransform.of(1.0F, -5.0F, -5.0F, 0.0F, -1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, PigEntityModel entityModel) {
		Optional<ModelPart> head = entityModel.getPart("head");
		if (head.isPresent()) {
			this.main.copyTransform(head.get());
			this.main.render(matrices, vertices, light, overlay);
		}
	}
}
