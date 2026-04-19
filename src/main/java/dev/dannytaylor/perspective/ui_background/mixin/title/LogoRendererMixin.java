/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui_background.mixin.title;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import dev.dannytaylor.perspective.ui_background.title.TitleRenderHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LogoRenderer.class, priority = 100)
public class LogoRendererMixin {
    @Inject(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At("HEAD"))
    private void perspective$adjustY_start(GuiGraphics guiGraphics, int i, float f, int j, CallbackInfo ci) {
        TitleRenderHelper.updateTitleY(true, guiGraphics);
    }

    @Redirect(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V", ordinal = 0))
    private void perspective$renderLogo(GuiGraphics guiGraphics, RenderPipeline renderPipeline, Identifier textureId, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color) {
        Identifier updateId = TitleRenderHelper.getUpdateTexture();
        if (TitleRenderHelper.textureExists(updateId)) guiGraphics.blit(renderPipeline, updateId, x, y, u, v, width, 128, textureWidth, 128, color);
        guiGraphics.blit(renderPipeline, textureId, x, y, u, v, width, height, textureWidth, textureHeight, color);
    }

    @Inject(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At("RETURN"))
    private void perspective$adjustY_finish(GuiGraphics guiGraphics, int i, float f, int j, CallbackInfo ci) {
        TitleRenderHelper.updateTitleY(false, guiGraphics);
    }
}
