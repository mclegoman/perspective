/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.mixin.events.hide_hud;

import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
	@Shadow protected abstract void renderCameraOverlays(GuiGraphics guiGraphics, DeltaTracker deltaTracker);

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	private void perspective$render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if (CoreEvents.getHideHud().ordinal() >= HideUi.handsHud.ordinal()) {
			this.renderCameraOverlays(guiGraphics, deltaTracker);
			ci.cancel();
		}
	}
}