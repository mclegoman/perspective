/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.mixin.background;

import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PanoramaRenderer.class, priority = 100)
public class PanoramaRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void perspective$render(GuiGraphics guiGraphics, int width, int height, boolean bl, CallbackInfo ci) {
        if (BackgroundRegistry.getBackground() != null && !BackgroundRegistry.getBackground().shouldRenderPanorama(this.minecraft.screen instanceof TitleScreen)) ci.cancel();
    }
}
