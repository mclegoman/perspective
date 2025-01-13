/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.panda;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.client.render.entity.PandaEntityRenderer;
import net.minecraft.client.render.entity.state.PandaEntityRenderState;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(priority = 100, value = PandaEntityRenderer.class)
public class PandaEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/PandaEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(PandaEntityRenderState pandaEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
		if (pandaEntityRenderState != null) {
			boolean isTexturedEntity = true;
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(pandaEntityRenderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants", new JsonObject());
						if (variants.has(pandaEntityRenderState.gene.asString().toLowerCase())) {
							JsonObject typeRegistry = JsonHelper.getObject(variants, pandaEntityRenderState.gene.asString().toLowerCase(), new JsonObject());
							isTexturedEntity = JsonHelper.getBoolean(typeRegistry, "enabled", true);
						}
					}
				}
			}
			if (isTexturedEntity) {
				String variant = pandaEntityRenderState.gene.asString() != null ? "_" + pandaEntityRenderState.gene.asString().toLowerCase() : "";
				cir.setReturnValue(TexturedEntity.getTexture(pandaEntityRenderState, "", variant, cir.getReturnValue()));
			}
		}
	}
}