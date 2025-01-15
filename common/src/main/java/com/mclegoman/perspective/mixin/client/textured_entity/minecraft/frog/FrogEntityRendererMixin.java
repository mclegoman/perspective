/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.frog;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import fabric.com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.state.FrogEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.FrogEntityRenderer.class)
public class FrogEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/FrogEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(FrogEntityRenderState frogEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (frogEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(frogEntityRenderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							if (entitySpecific.has((((PerspectiveVariantRenderState)frogEntityRenderState).perspective$getVariantId()).toLowerCase())) {
								JsonObject typeRegistry = JsonHelper.getObject(variants, (((PerspectiveVariantRenderState)frogEntityRenderState).perspective$getVariantId()).toLowerCase());
								if (typeRegistry != null) {
									isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(typeRegistry, "enabled", true));
								}
							}
						}
					}
				}
				if (isTexturedEntity) {
					String variant = (((PerspectiveVariantRenderState)frogEntityRenderState).perspective$getVariantId());
					if (variant != null) cir.setReturnValue(TexturedEntity.getTexture(frogEntityRenderState, IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, variant), cir.getReturnValue()));
				}
			}
		}
	}
}