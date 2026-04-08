/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.mixin.client.registry.zooms;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.dannytaylor.perspective.client.events.Execute;
import dev.dannytaylor.perspective.client.registry.zooms.ZoomRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = GameRenderer.class)
public abstract class GameRendererMixin {
    @Shadow public abstract boolean isPanoramicMode();

    @Inject(method = "tickFov", at = @At("TAIL"))
    private void perspective$updateZoomMultipliers(CallbackInfo ci) {
        if (!isPanoramicMode()) Execute.updateZoomMultipliers();
    }

    @ModifyReturnValue(method = "getFov", at = @At("RETURN"))
    private float perspective$getFov(float fov, Camera camera, float tickDelta, boolean changingFov) {
        return !isPanoramicMode() ? ZoomRegistry.zoomFov = Execute.getFov(fov, camera, tickDelta) : fov;
    }
}
