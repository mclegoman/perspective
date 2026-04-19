/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.mixin.background;

import com.mclegoman.luminance.mixin.client.shaders.GameRendererAccessor;
import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.events.UserInterfaceExecute;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class, priority = 100)
public class ScreenMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Inject(method = "renderMenuBackgroundTexture", at = @At("HEAD"), cancellable = true)
    private static void perspective$renderMenuBackgroundTexture(GuiGraphics guiGraphics, Identifier identifier, int i, int j, float f, float g, int k, int l, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null && !BackgroundRegistry.getBackground().shouldRenderMenuBackgroundTexture()) ci.cancel();
    }

    @Inject(method = "renderWithTooltipAndSubtitles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private void perspective$renderBackground(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        UserInterfaceExecute.renderUserInterfaceBackground(this.minecraft, guiGraphics);
    }

    @Inject(method = "renderBlurredBackground", at = @At("HEAD"), cancellable = true)
    private void perspective$renderBlur(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null) {
            if (!BackgroundRegistry.getBackground().getBlurRenderer().render(guiGraphics, ((GameRendererAccessor)this.minecraft.gameRenderer).getResourcePool())) ci.cancel();
        }
    }
}
