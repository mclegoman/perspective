/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.shulker;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.state.ShulkerEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = ShulkerEntityRenderer.class)
public class ShulkerEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/ShulkerEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(ShulkerEntityRenderState shulkerEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (shulkerEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(shulkerEntityRenderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							if (shulkerEntityRenderState.color != null) {
								if (variants.has(shulkerEntityRenderState.color.getName())) {
									JsonObject color = JsonHelper.getObject(variants, shulkerEntityRenderState.color.getName());
									if (color != null) isTexturedEntity = JsonHelper.getBoolean(color, "enabled", true);
								}
							} else {
								if (variants.has("normal")) {
									JsonObject normal = JsonHelper.getObject(variants, "normal");
									if (normal != null) isTexturedEntity = JsonHelper.getBoolean(normal, "enabled", true);
								}
							}
						}
					}
				}
				if (isTexturedEntity) {
					String variant = shulkerEntityRenderState.color != null ? "_" + shulkerEntityRenderState.color.getName() : "";
					cir.setReturnValue(TexturedEntity.getTexture(shulkerEntityRenderState, "", variant, cir.getReturnValue()));
				}
			}
		}
	}
}