/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.wolf;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.states.PerspectiveTamedRenderState;
import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import net.minecraft.client.render.entity.WolfEntityRenderer;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = WolfEntityRenderer.class)
public class WolfEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/WolfEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(WolfEntityRenderState wolfEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (wolfEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(wolfEntityRenderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				String variant = ((PerspectiveVariantRenderState)wolfEntityRenderState).perspective$getVariantId();
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
				}
				if (isTexturedEntity) {
					String variantNamespace = variant != null ? IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, variant) : "minecraft";
					String variantKey = variant != null ? "_" + IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, variant) : "";
					cir.setReturnValue(((PerspectiveTamedRenderState)wolfEntityRenderState).perspective$getTamed() ? TexturedEntity.getTexture(wolfEntityRenderState, variantNamespace, "", variantKey + "_tame", cir.getReturnValue()) : (wolfEntityRenderState.angerTime ? TexturedEntity.getTexture(wolfEntityRenderState, variantNamespace, "", variantKey + "_angry", cir.getReturnValue()) : TexturedEntity.getTexture(wolfEntityRenderState, variantNamespace, "", variantKey, cir.getReturnValue())));
				}
			}
		}
	}
}