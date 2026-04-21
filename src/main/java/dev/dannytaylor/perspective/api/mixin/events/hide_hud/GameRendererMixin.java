/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.mixin.events.hide_hud;

import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "renderItemInHand", at = @At("HEAD"), cancellable = true)
	private void perspective$renderItemInHand(float f, boolean bl, Matrix4f matrix4f, CallbackInfo ci) {
		if (CoreEvents.getHideHud().ordinal() >= HideUi.hands.ordinal()) ci.cancel();
	}
}