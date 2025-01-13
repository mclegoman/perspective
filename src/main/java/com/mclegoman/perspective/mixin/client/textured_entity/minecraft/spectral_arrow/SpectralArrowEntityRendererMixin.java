/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.spectral_arrow;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.SpectralArrowEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = SpectralArrowEntityRenderer.class)
public class SpectralArrowEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture", cancellable = true)
	private void perspective$getTexture(ProjectileEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(state, cir.getReturnValue()));
	}
}