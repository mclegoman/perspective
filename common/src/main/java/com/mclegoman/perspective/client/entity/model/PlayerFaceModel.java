/*
    perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

public class PlayerFaceModel<S extends PlayerEntityRenderState> extends EntityModel<S> {
	public final ModelPart face;
	public PlayerFaceModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.face = root.getChild("face");
	}
	public static TexturedModelData getTexturedModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("face", ModelPartBuilder.create().uv(8, 8).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 0.0F, dilation).uv(0, 0).cuboid(-4.0F, -8.0F, -4.0125F, 8.0F, 8.0F, 0.0F, dilation), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}
