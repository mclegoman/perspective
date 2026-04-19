/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.mixin.client.ui;

import dev.dannytaylor.perspective_old.client.events.TitleRenderHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SplashRenderer.class, priority = 100)
public class SplashRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void perspective$adjustY_start(GuiGraphics guiGraphics, int i, Font font, float f, CallbackInfo ci) {
        TitleRenderHelper.updateTitleY(true, guiGraphics);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void perspective$adjustY_finish(GuiGraphics guiGraphics, int i, Font font, float f, CallbackInfo ci) {
        TitleRenderHelper.updateTitleY(false, guiGraphics);
    }
}
