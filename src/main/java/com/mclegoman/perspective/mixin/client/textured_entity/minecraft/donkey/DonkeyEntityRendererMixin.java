/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.donkey;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.state.DonkeyEntityRenderState;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.AbstractDonkeyEntityRenderer.class)
public class DonkeyEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/DonkeyEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(DonkeyEntityRenderState entity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(entity, cir.getReturnValue()));
	}
}