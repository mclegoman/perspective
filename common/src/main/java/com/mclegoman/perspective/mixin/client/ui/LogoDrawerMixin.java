/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.ui;

import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = LogoDrawer.class)
public abstract class LogoDrawerMixin {
	@Shadow @Final private boolean ignoreAlpha;

	@Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIFFIIIII)V", ordinal = 0))
	private void perspective$addExtraLogoTexture(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
		context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.version.getID(), "textures/gui/title/update.png"), screenWidth / 2 - 128, y, 0.0F, 0.0F, 256, 128, 256, 128, ColorHelper.getWhite(this.ignoreAlpha ? 1.0F : alpha));
	}
}