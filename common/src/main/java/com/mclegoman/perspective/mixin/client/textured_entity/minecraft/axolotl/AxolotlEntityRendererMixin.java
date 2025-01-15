/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.axolotl;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.state.AxolotlEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.AxolotlEntityRenderer.class)
public class AxolotlEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/AxolotlEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(AxolotlEntityRenderState entity, CallbackInfoReturnable<Identifier> cir) {
		if (entity != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(entity);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants", new JsonObject());
						if (variants.has(entity.variant.getName().toLowerCase())) {
							JsonObject typeRegistry = JsonHelper.getObject(variants, entity.variant.getName().toLowerCase(), new JsonObject());
							isTexturedEntity = JsonHelper.getBoolean(typeRegistry, "enabled", true);
						}
					}
				}
			}
			if (isTexturedEntity) {
				String variant = entity.variant.getName() != null ? "_" + entity.variant.getName().toLowerCase() : "";
				cir.setReturnValue(TexturedEntity.getTexture(entity, "", variant, cir.getReturnValue()));
			}
		}
	}
}