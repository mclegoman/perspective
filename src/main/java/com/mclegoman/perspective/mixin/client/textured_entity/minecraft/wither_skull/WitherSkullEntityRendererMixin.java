/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.wither_skull;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.WitherSkullEntityRenderer;
import net.minecraft.client.render.entity.state.WitherSkullEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = WitherSkullEntityRenderer.class)
public class WitherSkullEntityRendererMixin {
	@Inject(at = @At("RETURN"), method = "getTexture", cancellable = true)
	private void perspective$getTexture(WitherSkullEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(state, cir.getReturnValue()));
	}
}