/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.ArmorStandEntityRenderState;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ArmorStandOverlayEntityModel extends EntityModel<ArmorStandEntityRenderState> {
	public final ModelPart head;
	public final ModelPart body;
	public final ModelPart rightArm;
	public final ModelPart leftArm;
	public final ModelPart rightLeg;
	public final ModelPart leftLeg;
	public ArmorStandOverlayEntityModel(ModelPart root) {
		this(root, RenderLayer::getEntityTranslucent);
	}
	public ArmorStandOverlayEntityModel(ModelPart root, Function<Identifier, RenderLayer> renderLayerFactory) {
		super(root, renderLayerFactory);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = root.getChild("right_arm");
		this.leftArm = root.getChild("left_arm");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}
	public static ModelData getModelData(Dilation dilation, float pivotOffsetY) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -7.99F, -4.0F, 8.0F, 8.0F, 8.0F, dilation), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(0.0F, 0.0F + pivotOffsetY, 0.0F));
		modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(-5.0F, 2.0F + pivotOffsetY, 0.0F));
		modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(5.0F, 2.0F + pivotOffsetY, 0.0F));
		modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(-1.9F, 12.0F + pivotOffsetY, 0.0F));
		modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, dilation), ModelTransform.pivot(1.9F, 12.0F + pivotOffsetY, 0.0F));
		return modelData;
	}

	public void setAngles(ArmorStandEntityRenderState armorStandEntityRenderState) {
		float multiplier = 0.017453292F;
		super.setAngles(armorStandEntityRenderState);
		this.head.pitch = multiplier * armorStandEntityRenderState.headRotation.getPitch();
		this.head.yaw = multiplier * armorStandEntityRenderState.headRotation.getYaw();
		this.head.roll = multiplier * armorStandEntityRenderState.headRotation.getRoll();

		this.body.pitch = multiplier * armorStandEntityRenderState.bodyRotation.getPitch();
		this.body.yaw = multiplier * armorStandEntityRenderState.bodyRotation.getYaw();
		this.body.roll = multiplier * armorStandEntityRenderState.bodyRotation.getRoll();

		this.leftArm.pitch = multiplier * armorStandEntityRenderState.leftArmRotation.getPitch();
		this.leftArm.yaw = multiplier * armorStandEntityRenderState.leftArmRotation.getYaw();
		this.leftArm.roll = multiplier * armorStandEntityRenderState.leftArmRotation.getRoll();

		this.rightArm.pitch = multiplier * armorStandEntityRenderState.rightArmRotation.getPitch();
		this.rightArm.yaw = multiplier * armorStandEntityRenderState.rightArmRotation.getYaw();
		this.rightArm.roll = multiplier * armorStandEntityRenderState.rightArmRotation.getRoll();

		this.leftLeg.pitch = multiplier * armorStandEntityRenderState.leftLegRotation.getPitch();
		this.leftLeg.yaw = multiplier * armorStandEntityRenderState.leftLegRotation.getYaw();
		this.leftLeg.roll = multiplier * armorStandEntityRenderState.leftLegRotation.getRoll();

		this.rightLeg.pitch = multiplier * armorStandEntityRenderState.rightLegRotation.getPitch();
		this.rightLeg.yaw = multiplier * armorStandEntityRenderState.rightLegRotation.getYaw();
		this.rightLeg.roll = multiplier * armorStandEntityRenderState.rightLegRotation.getRoll();
	}
}