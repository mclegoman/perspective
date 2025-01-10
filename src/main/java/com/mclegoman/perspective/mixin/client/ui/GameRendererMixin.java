/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.ui;

import com.mclegoman.perspective.client.ui.UIBackground;
import com.mclegoman.perspective.client.ui.UIBackgroundData;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(priority = 100, value = GameRenderer.class)
public abstract class GameRendererMixin {
	@ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderLoader;loadPostEffect(Lnet/minecraft/util/Identifier;Ljava/util/Set;)Lnet/minecraft/client/gl/PostEffectProcessor;"), method = "renderBlur")
	private void perspective$loadShader(Args args) {
		UIBackgroundData data = UIBackground.getCurrentUIBackground();
		if (data.getShaderId() != null) args.set(0, data.getShaderId());
	}
	@Inject(method = "renderBlur", at = @At("HEAD"), cancellable = true)
	private void perspective$renderShader(CallbackInfo ci) {
		if (!UIBackground.getCurrentUIBackground().getRenderShader()) ci.cancel();
	}
}