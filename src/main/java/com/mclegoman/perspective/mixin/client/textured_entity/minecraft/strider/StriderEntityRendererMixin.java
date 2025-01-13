/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.strider;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.states.PerspectiveGoatRenderState;
import net.minecraft.client.render.entity.StriderEntityRenderer;
import net.minecraft.client.render.entity.state.StriderEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = StriderEntityRenderer.class)
public class StriderEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/StriderEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(StriderEntityRenderState striderEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (striderEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(striderEntityRenderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							if (variants.has("normal")) {
								JsonObject normal = JsonHelper.getObject(variants, "normal");
								if (normal != null) isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(normal, "enabled", true));
							}
							if (variants.has("cold")) {
								JsonObject cold = JsonHelper.getObject(variants, "cold");
								if (cold != null)
									isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(cold, "enabled", true));
							}
						}
					}
				}
				if (isTexturedEntity) {
					String variant = striderEntityRenderState.cold ? "_cold" : "";
					cir.setReturnValue(TexturedEntity.getTexture(striderEntityRenderState, "", variant, cir.getReturnValue()));
				}
			}
		}
	}
}