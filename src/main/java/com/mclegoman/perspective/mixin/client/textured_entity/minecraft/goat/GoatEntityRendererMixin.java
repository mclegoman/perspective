/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.goat;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.states.PerspectiveGoatRenderState;
import net.minecraft.client.render.entity.state.GoatEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.GoatEntityRenderer.class)
public class GoatEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/GoatEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(GoatEntityRenderState goatEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (goatEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(goatEntityRenderState);
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
							if (variants.has("screaming")) {
								JsonObject screaming = JsonHelper.getObject(variants, "screaming");
								if (screaming != null)
									isTexturedEntity = TexturedEntity.setTexturedEntity(isTexturedEntity, JsonHelper.getBoolean(screaming, "enabled", true));
							}
						}
					}
				}
				if (isTexturedEntity) {
					String variant = ((PerspectiveGoatRenderState)goatEntityRenderState).perspective$getScreaming() ? "_screaming" : "";
					cir.setReturnValue(TexturedEntity.getTexture(goatEntityRenderState, "", variant, cir.getReturnValue()));
				}
			}
		}
	}
}