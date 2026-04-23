/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.mixin.background;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Execute;
import com.mclegoman.luminance.mixin.client.shaders.GameRendererAccessor;
import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.events.UserInterfaceExecute;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Screen.class, priority = 100)
public abstract class ScreenMixin {
    @Shadow @Final protected Minecraft minecraft;

    @Inject(method = "renderMenuBackgroundTexture", at = @At("HEAD"), cancellable = true)
    private static void perspective$renderMenuBackgroundTexture(GuiGraphics guiGraphics, Identifier identifier, int i, int j, float f, float g, int k, int l, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null && !BackgroundRegistry.getBackground().shouldRenderMenuBackgroundTexture()) ci.cancel();
    }

    @Inject(method = "renderPanorama", at = @At("HEAD"), cancellable = true)
    private void perspective$renderPanorama(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null) {
            UserInterfaceExecute.renderUserInterfaceBackground(this.minecraft, guiGraphics);
            if (!BackgroundRegistry.getBackground().shouldRenderPanorama(((Object)this) instanceof TitleScreen)) {
                ci.cancel();
                // TODO: find out why this isn't working :/
                Execute.afterPanoramaRender(((GameRendererAccessor) ClientData.minecraft.gameRenderer).getResourcePool());
            }
        }
    }

    @Inject(method = "renderBlurredBackground", at = @At("HEAD"))
    private void perspective$renderBlurredBackground(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null) {
            if (this.minecraft.level != null) UserInterfaceExecute.renderUserInterfaceBackground(this.minecraft, guiGraphics);
            BackgroundRegistry.getBackground().getBlurRenderer().render().run(guiGraphics);
        }
    }

    @Inject(method = "renderTransparentBackground", at = @At("HEAD"), cancellable = true)
    private void perspective$renderTransparentBackground(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null) {
            UserInterfaceExecute.renderUserInterfaceBackground(guiGraphics, CurrentBackground.TRANSPARENT_BACKGROUND);
            if (!BackgroundRegistry.getBackground().getTransparentBackgroundRenderer().call(guiGraphics)) ci.cancel();
        }
    }
}
