/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.mixin.zooms;

import dev.dannytaylor.perspective.lens.events.LensExecute;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = MouseHandler.class)
public abstract class MouseHandlerMixin {
    @Inject(method = "turnPlayer", at = @At("HEAD"))
    private void perspective$updateTime(double d, CallbackInfo ci) {
        LensExecute.mouseDelta = d;
    }

    @ModifyVariable(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"), ordinal = 1)
    private double perspective$updateXSensitivity(double x) {
        return LensExecute.updateXSensitivity(x);
    }

    @ModifyVariable(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTutorial()Lnet/minecraft/client/tutorial/Tutorial;"), ordinal = 2)
    private double perspective$updateYSensitivity(double y) {
        return LensExecute.updateYSensitivity(y);
    }
}
