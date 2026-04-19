/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.mixin.cameratypes;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives.HoldPerspective;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives.SwapPerspective;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(priority = 100, value = Camera.class)
public abstract class CameraMixin {
    @Shadow private boolean detached;
    @Shadow protected abstract float getMaxZoom(float f);

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;getMaxZoom(F)F"), method = "setup")
    private float perspective$setup(float original) {
        if (this.detached) {
            if (HoldPerspective.isHolding(ClientData.minecraft)) {
                if (HoldPerspective.isHoldingBack(ClientData.minecraft)) {
                    return HoldPerspective.getBackMultiplier() != 1.0F ? this.getMaxZoom(original * HoldPerspective.getBackMultiplier()) : original;
                } else if (HoldPerspective.isHoldingFront(ClientData.minecraft)) {
                    return HoldPerspective.getFrontMultiplier() != 1.0F ? this.getMaxZoom(original * HoldPerspective.getFrontMultiplier()) : original;
                }
            } else if (SwapPerspective.isMultiplierAdjustable(ClientData.minecraft)) {
                return switch (ClientData.minecraft.options.getCameraType()) {
                    case THIRD_PERSON_BACK -> SwapPerspective.getBackMultiplier() != 1.0F ? this.getMaxZoom(original * SwapPerspective.getBackMultiplier()) : original;
                    case THIRD_PERSON_FRONT -> SwapPerspective.getFrontMultiplier() != 1.0F ? this.getMaxZoom(original * SwapPerspective.getFrontMultiplier()) : original;
                    default -> original;
                };
            }
        }
        return original;
    }
}
