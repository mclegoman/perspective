/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity;

import com.mclegoman.perspective.client.entity.model.*;
import com.mclegoman.perspective.common.util.Identifiers;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;

public class EntityModels {
	public static double entityCapeY = 0.0F;
	public static final EntityModelLayer contributorOverlaySlim = new EntityModelLayer(Identifiers.CONTRIBUTOR, "slim");
	public static final EntityModelLayer contributorOverlayWide = new EntityModelLayer(Identifiers.CONTRIBUTOR, "wide");
	public static final EntityModelLayer entityCape = new EntityModelLayer(Identifiers.ENTITY, "cape");
	public static final EntityModelLayer babyPigOverlay = new EntityModelLayer(Identifiers.PIG, "baby_outer");
	public static final EntityModelLayer pigOverlay = new EntityModelLayer(Identifiers.PIG, "outer");
	public static final EntityModelLayer pigMuddyFlower = new EntityModelLayer(Identifiers.PIG, "muddy_flower");
	public static final EntityModelLayer beeOverlay = new EntityModelLayer(Identifiers.BEE, "outer");
	public static final EntityModelLayer babyBeeOverlay = new EntityModelLayer(Identifiers.BEE, "outer_baby");
	public static final EntityModelLayer mooshroomOverlay = new EntityModelLayer(Identifiers.MOOSHROOM, "outer");
	public static final EntityModelLayer babyMooshroomOverlay = new EntityModelLayer(Identifiers.MOOSHROOM, "outer_baby");
	public static final EntityModelLayer cowOverlay = new EntityModelLayer(Identifiers.COW, "outer");
	public static final EntityModelLayer babyCowOverlay = new EntityModelLayer(Identifiers.COW, "outer_baby");
	public static final EntityModelLayer skeletonOverlay = new EntityModelLayer(Identifiers.SKELETON, "outer");
	public static final EntityModelLayer witherSkeletonOverlay = new EntityModelLayer(Identifiers.WITHER_SKELETON, "outer");
	public static final EntityModelLayer zombieOverlay = new EntityModelLayer(Identifiers.ZOMBIE, "outer");
	public static final EntityModelLayer giantOverlay = new EntityModelLayer(Identifiers.GIANT, "outer");
	public static final EntityModelLayer babyZombieOverlay = new EntityModelLayer(Identifiers.ZOMBIE, "outer_baby");
	public static final EntityModelLayer armorStandOverlay = new EntityModelLayer(Identifiers.ARMOR_STAND, "outer");
	public static final EntityModelLayer babyArmorStandOverlay = new EntityModelLayer(Identifiers.ARMOR_STAND, "outer_baby");
	public static final EntityModelLayer halloweenHat = new EntityModelLayer(Identifiers.PLAYER, "halloween_hat");
	public static final EntityModelLayer playerFace = new EntityModelLayer(Identifiers.PLAYER, "face");
	public static void init() {
		EntityModelLayerRegistry.registerModelLayer(contributorOverlaySlim, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.001F), true), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(contributorOverlayWide, () -> TexturedModelData.of(PlayerEntityModel.getTexturedModelData(new Dilation(0.001F), false), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(entityCape, () -> TexturedModelData.of(LivingEntityCapeModel.getModelData(new Dilation(0.0F)), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(babyPigOverlay, () -> PigEntityModel.getTexturedModelData(new Dilation(0.499F)).transform(PigEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(pigOverlay, () -> PigEntityModel.getTexturedModelData(new Dilation(0.499F)));
		EntityModelLayerRegistry.registerModelLayer(pigMuddyFlower, MuddyFlowerModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(beeOverlay, () -> getBeeEntityModelData(new Dilation(0.5F)));
		EntityModelLayerRegistry.registerModelLayer(babyBeeOverlay, () -> getBeeEntityModelData(new Dilation(0.5F)).transform(BeeEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(mooshroomOverlay, CowOverlayEntityModel::getTexturedOverlayModelData);
		EntityModelLayerRegistry.registerModelLayer(babyMooshroomOverlay, () -> CowOverlayEntityModel.getTexturedOverlayModelData().transform(CowEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(cowOverlay, CowOverlayEntityModel::getTexturedOverlayModelData);
		EntityModelLayerRegistry.registerModelLayer(babyCowOverlay, () -> CowOverlayEntityModel.getTexturedOverlayModelData().transform(CowEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(skeletonOverlay, () -> getBipedEntityModelData(new Dilation(0.5F), 64, 32));
		EntityModelLayerRegistry.registerModelLayer(witherSkeletonOverlay, () -> getBipedEntityModelData(new Dilation(0.5F), 64, 32));
		EntityModelLayerRegistry.registerModelLayer(zombieOverlay, () -> getBipedEntityModelData(new Dilation(0.5F), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(giantOverlay, () -> getBipedEntityModelData(new Dilation(0.5F), 64, 64).transform(new PerspectiveModelTransformer(6.0F, -20.0F)));
		EntityModelLayerRegistry.registerModelLayer(babyZombieOverlay, () -> getBipedEntityModelData(new Dilation(0.5F), 64, 64).transform(ZombieEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(armorStandOverlay, () -> TexturedModelData.of(ArmorStandOverlayEntityModel.getModelData(new Dilation(0.01F), 0.0F), 64, 64));
		EntityModelLayerRegistry.registerModelLayer(babyArmorStandOverlay, () -> TexturedModelData.of(ArmorStandOverlayEntityModel.getModelData(new Dilation(0.01F), 0.0F), 64, 64).transform(ArmorStandEntityModel.BABY_TRANSFORMER));
		EntityModelLayerRegistry.registerModelLayer(halloweenHat, HalloweenHatModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(playerFace, () -> PlayerFaceModel.getTexturedModelData(new Dilation(0.0F)));
	}
	public static void tick() {
		entityCapeY = (entityCapeY + 0.5F) % 80.0F;
	}
	public static double getEntityCapeY() {
		return entityCapeY % 80.0F;
	}
	public static TexturedModelData getBipedEntityModelData(Dilation dilation, int width, int height) {
		return TexturedModelData.of(BipedEntityModel.getModelData(dilation, 0.0F), width, height);
	}
	public static TexturedModelData getBeeEntityModelData(Dilation dilation) {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData2 = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.0F, 0.0F));
		ModelPartData modelPartData3 = modelPartData2.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -4.0F, -5.0F, 7.0F, 7.0F, 10.0F, dilation), ModelTransform.NONE);
		modelPartData3.addChild("stinger", ModelPartBuilder.create().uv(26, 7).cuboid(0.0F, -1.0F, 5.0F, 0.0F, 1.0F, 2.0F, dilation), ModelTransform.NONE);
		modelPartData3.addChild("left_antenna", ModelPartBuilder.create().uv(2, 0).cuboid(1.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, dilation), ModelTransform.pivot(0.0F, -2.0F, -5.0F));
		modelPartData3.addChild("right_antenna", ModelPartBuilder.create().uv(2, 3).cuboid(-2.5F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, dilation), ModelTransform.pivot(0.0F, -2.0F, -5.0F));
		Dilation outerDilation = dilation.add(0.001F);
		modelPartData2.addChild("right_wing", ModelPartBuilder.create().uv(0, 18).cuboid(-9.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, outerDilation), ModelTransform.of(-1.5F, -4.0F, -3.0F, 0.0F, -0.2618F, 0.0F));
		modelPartData2.addChild("left_wing", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(0.0F, 0.0F, 0.0F, 9.0F, 0.0F, 6.0F, outerDilation), ModelTransform.of(1.5F, -4.0F, -3.0F, 0.0F, 0.2618F, 0.0F));
		modelPartData2.addChild("front_legs", ModelPartBuilder.create().cuboid("front_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 1), ModelTransform.pivot(1.5F, 3.0F, -2.0F));
		modelPartData2.addChild("middle_legs", ModelPartBuilder.create().cuboid("middle_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 3), ModelTransform.pivot(1.5F, 3.0F, 0.0F));
		modelPartData2.addChild("back_legs", ModelPartBuilder.create().cuboid("back_legs", -5.0F, 0.0F, 0.0F, 7, 2, 0, 26, 5), ModelTransform.pivot(1.5F, 3.0F, 2.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
}