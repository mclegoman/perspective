/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.mixin.client.registry.cameratypes;

import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.registry.cameratypes.CameraTypeRegistry;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.joml.Vector2i;
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
    private void perspective$onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
            boolean discreteMouseScroll = ClientData.minecraft.options.discreteMouseScroll().get();
            double mouseWheelSensitivity = ClientData.minecraft.options.mouseWheelSensitivity().get();
            double calculatedScroll = (discreteMouseScroll ? Math.signum(vertical) : vertical) * mouseWheelSensitivity;
            Vector2i vector2i = this.scrollWheelHandler.onMouseScroll(calculatedScroll, calculatedScroll);
            if (vector2i.y != 0) {
                CameraTypeRegistry.adjustMultiplier(ClientData.minecraft, -vector2i.y / 100.0F);
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onButton", cancellable = true)
    private void perspective$onButton(long l, MouseButtonInfo mouseButtonInfo, int i, CallbackInfo ci) {
        if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
            if (mouseButtonInfo.button() == 2) {
                CameraTypeRegistry.resetMultiplier(ClientData.minecraft);
                ci.cancel();
            }
        }
    }
}
