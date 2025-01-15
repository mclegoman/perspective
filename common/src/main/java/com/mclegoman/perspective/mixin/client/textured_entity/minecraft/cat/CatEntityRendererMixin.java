/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.cat;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.states.PerspectiveTamedRenderState;
import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import fabric.com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.state.CatEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.CatEntityRenderer.class)
public class CatEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/CatEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(CatEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		if (state != null) {
			boolean isTexturedEntity = true;
			String variant = ((PerspectiveVariantRenderState)state).perspective$getVariantId();
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(state);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							if (entitySpecific.has(variant.toLowerCase())) {
								JsonObject typeRegistry = JsonHelper.getObject(variants, variant.toLowerCase());
								if (typeRegistry != null) {
									isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(typeRegistry, "enabled", true));
								}
							}
						}
					}
					if (entitySpecific.has("tamed")) {
						isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(entitySpecific, "tamed", true));
					}
				}
			}
			if (isTexturedEntity) {
				String variantNamespace = variant != null ? IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, variant) : "minecraft";
				String variantKey = variant != null ? "_" + IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, variant) : "";
				cir.setReturnValue(((PerspectiveTamedRenderState)state).perspective$getTamed() ? TexturedEntity.getTexture(state, variantNamespace, "", variantKey + "_tame", cir.getReturnValue()) : TexturedEntity.getTexture(state, variantNamespace, "", variantKey, cir.getReturnValue()));
			}
		}
	}
}