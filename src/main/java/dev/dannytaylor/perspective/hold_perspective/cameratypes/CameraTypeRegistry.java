/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.cameratypes;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.hold_perspective.HoldPerspectiveClient;
import dev.dannytaylor.perspective.hold_perspective.config.HoldPerspectiveConfig;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import dev.dannytaylor.perspective.hold_perspective.keymappings.HoldPerspectiveKeyMappings;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives.HoldPerspective;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives.SwapPerspective;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

public class CameraTypeRegistry {
    private static boolean wasConfigUpdated;

    public static void onInitializeClient(PerspectiveMod mod) {
        HoldPerspectiveEvents.onInitialize(mod, "Camera Type", () -> {
            HoldPerspective.onInitializeClient(mod);
            SwapPerspective.onInitializeClient(mod);
            HoldPerspectiveEvents.OnMouseScroll.register(getIdentifier(), (long windowHandle, double horizontal, double vertical, Vector2i vector2i) -> {
                if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
                    if (vector2i.y != 0) {
                        CameraTypeRegistry.adjustMultiplier(ClientData.minecraft, -vector2i.y / 100.0F);
                        return true;
                    }
                }
                return false;
            });

            HoldPerspectiveEvents.OnMouseButton.register(getIdentifier(), (windowHandle, mouseButtonInfo, action) -> {
                if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
                    if (mouseButtonInfo.button() == 2) {
                        CameraTypeRegistry.resetMultiplier(ClientData.minecraft);
                        return true;
                    }
                }
                return false;
            });
        });
    }

    public static Identifier getIdentifier() {
        return HoldPerspectiveClient.idOf("camera_type");
    }

    public static void onTickClient(Minecraft minecraft) {
        HoldPerspective.onTickClient(minecraft);
        SwapPerspective.onTickClient(minecraft);
        if (!isMultiplierAdjustable(minecraft) && wasConfigUpdated) {
            HoldPerspectiveConfig.instance.save();
            wasConfigUpdated = false;
        }
    }

    public static boolean isHoldingAdjust() {
        return HoldPerspectiveKeyMappings.adjustMultiplier.isDown();
    }

    public static boolean isMultiplierAdjustable(Minecraft minecraft) {
        return (HoldPerspective.isHolding(minecraft) || SwapPerspective.isMultiplierAdjustable(minecraft)) && isHoldingAdjust();
    }

    public static float clampMultiplier(float multiplier) {
        return Math.clamp(multiplier, 0.5F, 16.0F);
    }

    public static float getAdjustedMultiplier(float currentMultiplier, float scrollAmount) {
        return getAdjustedMultiplier(currentMultiplier, scrollAmount, HoldPerspectiveConfig.instance.multiplierIncrementSize.value());
    }

    public static float getAdjustedMultiplier(float currentMultiplier, float scrollAmount, float incrementSize) {
        return clampMultiplier(currentMultiplier + (scrollAmount * incrementSize));
    }

    public static void adjustMultiplier(Minecraft minecraft, float scrollAmount) {
        if (HoldPerspective.isHolding(minecraft)) {
            if (HoldPerspective.isHoldingBack(minecraft)) setMultiplier(true, true, getAdjustedMultiplier(HoldPerspective.getBackMultiplier(), scrollAmount));
            if (HoldPerspective.isHoldingFront(minecraft)) setMultiplier(true, false, getAdjustedMultiplier(HoldPerspective.getFrontMultiplier(), scrollAmount));
        } else if (SwapPerspective.isMultiplierAdjustable(minecraft)) {
            switch (minecraft.options.getCameraType()) {
                case THIRD_PERSON_BACK -> setMultiplier(false, true, getAdjustedMultiplier(SwapPerspective.getBackMultiplier(), scrollAmount));
                case THIRD_PERSON_FRONT -> setMultiplier(false, false, getAdjustedMultiplier(SwapPerspective.getFrontMultiplier(), scrollAmount));
            }
        }
    }

    public static void resetMultiplier(Minecraft minecraft) {
        if (HoldPerspective.isHolding(minecraft)) {
            if (HoldPerspective.isHoldingBack(minecraft)) setMultiplier(true, true, HoldPerspectiveConfig.instance.backMultiplier.getDefaultValue());
            if (HoldPerspective.isHoldingFront(minecraft)) setMultiplier(true, false, HoldPerspectiveConfig.instance.frontMultiplier.getDefaultValue());
        } else if (SwapPerspective.isMultiplierAdjustable(minecraft)) {
            switch (minecraft.options.getCameraType()) {
                case THIRD_PERSON_BACK -> setMultiplier(false, true, HoldPerspectiveConfig.instance.holdPerspective.backMultiplier.getDefaultValue());
                case THIRD_PERSON_FRONT -> setMultiplier(false, false, HoldPerspectiveConfig.instance.holdPerspective.frontMultiplier.getDefaultValue());
            }
        }
    }

    private static void setMultiplier(boolean isHolding, boolean isBack, float multiplier) {
        if (isHolding) {
            if (isBack) HoldPerspectiveConfig.instance.holdPerspective.backMultiplier.setValue(clampMultiplier(multiplier), false);
            else HoldPerspectiveConfig.instance.holdPerspective.frontMultiplier.setValue(clampMultiplier(multiplier), false);
        } else {
            if (isBack) HoldPerspectiveConfig.instance.backMultiplier.setValue(clampMultiplier(multiplier), false);
            else HoldPerspectiveConfig.instance.frontMultiplier.setValue(clampMultiplier(multiplier), false);
        }
        wasConfigUpdated = true;
    }
}
