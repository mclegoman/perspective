/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.mixin.client.events;

import dev.dannytaylor.perspective.client.events.Execute;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = MouseHandler.class)
public class MouseHandlerMixin {
    @Shadow @Final private ScrollWheelHandler scrollWheelHandler;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z"), method = "onScroll", cancellable = true)
    private void perspective$onScroll(long windowHandle, double horizontal, double vertical, CallbackInfo ci) {
        if (Execute.OnMouseScroll(windowHandle, horizontal, vertical, this.scrollWheelHandler)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "onButton", cancellable = true)
    private void perspective$onButton(long windowHandle, MouseButtonInfo mouseButtonInfo, int i, CallbackInfo ci) {
        if (Execute.OnMouseButton(windowHandle, mouseButtonInfo, i)) ci.cancel();
    }
}
