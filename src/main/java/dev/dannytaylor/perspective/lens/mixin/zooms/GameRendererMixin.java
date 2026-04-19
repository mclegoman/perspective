/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.mixin.zooms;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.dannytaylor.perspective.lens.events.LensExecute;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
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
        if (!isPanoramicMode()) LensExecute.updateZoomMultipliers();
    }

    @ModifyReturnValue(method = "getFov", at = @At("RETURN"))
    private float perspective$getFov(float fov, Camera camera, float tickDelta, boolean changingFov) {
        return !isPanoramicMode() ? ZoomRegistry.zoomFov = LensExecute.getFov(fov, camera, tickDelta) : fov;
    }

    @ModifyExpressionValue(method = "bobHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getHurtDir()F"))
    private float perspective$bobHurtDir(float hurtDir) {
        if (ZoomRegistry.isZooming()) hurtDir *= Math.max(ZoomRegistry.getCombinedBobViewMultiplier(), 0.001F);
        return hurtDir;
    }

    @ModifyExpressionValue(method = "bobHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;"))
    private <T> T perspective$bobHurtStrength(T original) {
        if (ZoomRegistry.isZooming() && original instanceof Double damageTiltStrength) return (T) Double.valueOf(damageTiltStrength * Math.max(ZoomRegistry.getCombinedBobViewMultiplier(), 0.001));
        return original;
    }

    @ModifyExpressionValue(method = "bobView", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/ClientAvatarState;getInterpolatedBob(F)F"))
    private float perspective$bobView(float interpolatedBob) {
        if (ZoomRegistry.isZooming()) interpolatedBob *= Math.max(ZoomRegistry.getCombinedBobViewMultiplier(), 0.001F);
        return interpolatedBob;
    }
}
