/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.cow;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import com.mclegoman.perspective.client.entity.renderer.feature.CowFlowerFeatureRenderer;
import com.mclegoman.perspective.client.entity.renderer.feature.EntityCapeFeatureRenderer;
import com.mclegoman.perspective.client.entity.renderer.feature.CowOverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.CowEntityRenderer.class)
public abstract class CowEntityRendererMixin extends MobEntityRenderer<CowEntity, LivingEntityRenderState, CowEntityModel> {
	public CowEntityRendererMixin(EntityRendererFactory.Context context, CowEntityModel entityModel, float f) {
		super(context, entityModel, f);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new CowOverlayFeatureRenderer<>(this, new CowEntityModel(context.getPart(EntityModels.cowOverlay)), new CowEntityModel(context.getPart(EntityModels.babyCowOverlay))));
		this.addFeature(new EntityCapeFeatureRenderer.Builder(this, new LivingEntityCapeModel(context.getPart(EntityModels.entityCape)), Identifier.of("perspective", "textures/entity/minecraft/cow/cow_cape.png")).offsetZ(-0.50125F).offsetY(0.125F).rotation(RotationAxis.POSITIVE_X.rotationDegrees(90.0F)).build());
		this.addFeature(new CowFlowerFeatureRenderer(this, context.getBlockRenderManager()));
	}
	@Inject(method = "getTexture", at = @At("RETURN"), cancellable = true)
	public void perspective$getTexture(LivingEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		boolean isTexturedEntity = true;
		Optional<TexturedEntityEntry> entityData = TexturedEntity.getEntity(state);
		if (entityData.isPresent()) {
			JsonObject entitySpecific = entityData.get().getEntitySpecific();
			String cowVariant = "temperate";
			if (entitySpecific != null) {
				if (entitySpecific.has("variants")) {
					JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
					if (variants != null) {
						if (entitySpecific.has(cowVariant)) {
							JsonObject typeRegistry = JsonHelper.getObject(variants, cowVariant);
							if (typeRegistry != null) {
								isTexturedEntity = JsonHelper.getBoolean(typeRegistry, "enabled", true);
							}
						}
					}
				}
			}
			if (isTexturedEntity) {
				String variant = cowVariant + "_";
				cir.setReturnValue(TexturedEntity.getTexture(state, variant, "", cir.getReturnValue()));
			}
		}
	}
}