/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.mixin.client.registry.zooms;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(priority = 100, value = MouseHandler.class)
public abstract class MouseHandlerMixin {
    @ModifyVariable(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"), ordinal = 1)
    private double perspective$updateXSensitivity(double x) {
        if (ZoomRegistry.isZooming() && ClientData.minecraft.player != null) {
            double angle = Mth.cos((ClientData.minecraft.player.getXRot() / 180.0F) * Mth.PI);
            float multiplier = Math.max(ZoomRegistry.getCombinedMouseMultiplier(), 0.001F);
            x = (x * (1.0F / Math.max((angle < 0) ? angle * -1.0F : angle, (Math.max(multiplier, 0.0F) + 1.0F) / 11.0F))) * multiplier;
        }
        return x;
    }

    @ModifyVariable(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"), ordinal = 2)
    private double perspective$updateYSensitivity(double y) {
        if (ZoomRegistry.isZooming() && ClientData.minecraft.player != null) y *= Math.max(ZoomRegistry.getCombinedMouseMultiplier(), 0.001F);
        return y;
    }
}
