/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.cameratypes;

import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.cameratypes.perspectives.HoldPerspective;
import dev.dannytaylor.perspective.client.registry.cameratypes.perspectives.SwapPerspective;
import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScrollWheelHandler;
import org.joml.Vector2i;

public class CameraTypeRegistry {
    private static boolean wasConfigUpdated;

    public static void onInitializeClient() {
        Log.info("Initializing camera type registries...");
        try {
            HoldPerspective.onInitializeClient();
            SwapPerspective.onInitializeClient();
            Events.OnMouseScroll.register(Identifiers.CAMERA_TYPE, (long windowHandle, double horizontal, double vertical, ScrollWheelHandler scrollWheelHandler) -> {
                if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
                    boolean discreteMouseScroll = ClientData.minecraft.options.discreteMouseScroll().get();
                    double mouseWheelSensitivity = ClientData.minecraft.options.mouseWheelSensitivity().get();
                    double calculatedScroll = (discreteMouseScroll ? Math.signum(vertical) : vertical) * mouseWheelSensitivity;
                    Vector2i vector2i = scrollWheelHandler.onMouseScroll(calculatedScroll, calculatedScroll);
                    if (vector2i.y != 0) {
                        CameraTypeRegistry.adjustMultiplier(ClientData.minecraft, -vector2i.y / 100.0F);
                        return true;
                    }
                }
                return false;
            });
            Events.OnMouseButton.register(Identifiers.CAMERA_TYPE, (windowHandle, mouseButtonInfo, action) -> {
                if (CameraTypeRegistry.isMultiplierAdjustable(ClientData.minecraft)) {
                    if (mouseButtonInfo.button() == 2) {
                        CameraTypeRegistry.resetMultiplier(ClientData.minecraft);
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception error) {
            Log.error("Failed to initialize camera type registries: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        HoldPerspective.onTickClient(minecraft);
        SwapPerspective.onTickClient(minecraft);
        if (!isMultiplierAdjustable(minecraft) && wasConfigUpdated) {
            PerspectiveConfig.config.save();
            wasConfigUpdated = false;
        }
    }

    public static boolean isHoldingAdjust() {
        return KeyMappingRegistry.CameraType.adjustMultiplier.isDown();
    }

    public static boolean isMultiplierAdjustable(Minecraft minecraft) {
        return (HoldPerspective.isHolding(minecraft) || SwapPerspective.isMultiplierAdjustable(minecraft)) && isHoldingAdjust();
    }

    public static float clampMultiplier(float multiplier) {
        return Math.clamp(multiplier, 0.5F, 16.0F);
    }

    public static float getAdjustedMultiplier(float currentMultiplier, float scrollAmount) {
        return getAdjustedMultiplier(currentMultiplier, scrollAmount, PerspectiveConfig.config.cameraType.multiplierIncrementSize.value());
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
            if (HoldPerspective.isHoldingBack(minecraft)) setMultiplier(true, true, PerspectiveConfig.config.cameraType.backMultiplier.getDefaultValue());
            if (HoldPerspective.isHoldingFront(minecraft)) setMultiplier(true, false, PerspectiveConfig.config.cameraType.frontMultiplier.getDefaultValue());
        } else if (SwapPerspective.isMultiplierAdjustable(minecraft)) {
            switch (minecraft.options.getCameraType()) {
                case THIRD_PERSON_BACK -> setMultiplier(false, true, PerspectiveConfig.config.cameraType.holdPerspective.backMultiplier.getDefaultValue());
                case THIRD_PERSON_FRONT -> setMultiplier(false, false, PerspectiveConfig.config.cameraType.holdPerspective.frontMultiplier.getDefaultValue());
            }
        }
    }

    private static void setMultiplier(boolean isHolding, boolean isBack, float multiplier) {
        if (isHolding) {
            if (isBack) PerspectiveConfig.config.cameraType.holdPerspective.backMultiplier.setValue(clampMultiplier(multiplier), false);
            else PerspectiveConfig.config.cameraType.holdPerspective.frontMultiplier.setValue(clampMultiplier(multiplier), false);
        } else {
            if (isBack) PerspectiveConfig.config.cameraType.backMultiplier.setValue(clampMultiplier(multiplier), false);
            else PerspectiveConfig.config.cameraType.frontMultiplier.setValue(clampMultiplier(multiplier), false);
        }
        wasConfigUpdated = true;
    }
}
