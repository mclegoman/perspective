/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.zombie;

import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import com.mclegoman.perspective.client.entity.renderer.feature.EntityCapeFeatureRenderer;
import com.mclegoman.perspective.client.entity.renderer.feature.OverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = ZombieEntityRenderer.class)
public abstract class ZombieEntityRendererMixin extends ZombieBaseEntityRenderer<ZombieEntity, ZombieEntityRenderState, ZombieEntityModel<ZombieEntityRenderState>> {
	protected ZombieEntityRendererMixin(EntityRendererFactory.Context context, ZombieEntityModel<ZombieEntityRenderState> mainModel, ZombieEntityModel<ZombieEntityRenderState> babyMainModel, ZombieEntityModel<ZombieEntityRenderState> armorInnerModel, ZombieEntityModel<ZombieEntityRenderState> armorOuterModel, ZombieEntityModel<ZombieEntityRenderState> babyArmorInnerModel, ZombieEntityModel<ZombieEntityRenderState> babyArmorOuterModel) {
		super(context, mainModel, babyMainModel, armorInnerModel, armorOuterModel, babyArmorInnerModel, babyArmorOuterModel);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new OverlayFeatureRenderer<>(this, new ZombieEntityModel<>(context.getPart(EntityModels.zombieOverlay)), new ZombieEntityModel<>(context.getPart(EntityModels.babyZombieOverlay)), Identifier.of("textures/entity/zombie/zombie_overlay.png")));
		this.addFeature(new EntityCapeFeatureRenderer.Builder(this, new LivingEntityCapeModel(context.getPart(EntityModels.entityCape)), Identifier.of("perspective", "textures/entity/minecraft/zombie/zombie_cape.png")).build());
	}
}