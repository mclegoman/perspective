/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.fox;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import com.mclegoman.perspective.client.entity.renderer.feature.EntityCapeFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.entity.state.FoxEntityRenderState;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.FoxEntityRenderer.class)
public abstract class FoxEntityRendererMixin extends MobEntityRenderer<FoxEntity, FoxEntityRenderState, FoxEntityModel> {
	public FoxEntityRendererMixin(EntityRendererFactory.Context context, FoxEntityModel entityModel, float f) {
		super(context, entityModel, f);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new EntityCapeFeatureRenderer.Builder(this, new LivingEntityCapeModel(context.getPart(EntityModels.entityCape)), Identifier.of("perspective", "textures/entity/minecraft/fox/fox_cape.png")).offsetZ(-0.1225F).offsetY(0.845F).rotation(RotationAxis.POSITIVE_X.rotationDegrees(90.0F).scale(0.8F)).build());
	}
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/FoxEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(FoxEntityRenderState entity, CallbackInfoReturnable<Identifier> cir) {
		if (entity != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(entity);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							if (variants.has(entity.type.asString().toLowerCase())) {
								JsonObject typeRegistry = JsonHelper.getObject(variants, entity.type.asString().toLowerCase());
								if (typeRegistry != null) {
									isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(typeRegistry, "enabled", true));
								}
							}
						}
					}
				}
				if (isTexturedEntity) {
					String variant = entity.type != null ? "_" + entity.type.asString() : "";
					cir.setReturnValue(entity.sleeping ? TexturedEntity.getTexture(entity, "", variant + "_sleep", cir.getReturnValue()) : TexturedEntity.getTexture(entity, "", variant, cir.getReturnValue()));
				}
			}
		}
	}
}