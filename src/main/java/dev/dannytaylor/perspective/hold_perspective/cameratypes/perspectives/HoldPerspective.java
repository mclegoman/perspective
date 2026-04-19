/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.hold_perspective.keymappings.HoldPerspectiveKeyMappings;
import dev.dannytaylor.perspective_old.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.CameraTypeRegistry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;

public class HoldPerspective {
    public static CameraType beforePressed;
    public static boolean wasBackPressed;
    public static boolean wasFrontPressed;

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Hold Perspective", () -> {});
    }

    public static void onTickClient(Minecraft minecraft) {
        // Back
        if (!wasBackPressed && HoldPerspectiveKeyMappings.HoldPerspective.thirdPersonBack.isDown()) {
            wasBackPressed = true;
            setCameraType(minecraft, wasFrontPressed, false);
        } else if (wasBackPressed && !HoldPerspectiveKeyMappings.HoldPerspective.thirdPersonBack.isDown()) {
            wasBackPressed = false;
            minecraft.options.setCameraType(wasFrontPressed ? CameraType.THIRD_PERSON_FRONT : beforePressed);
        }

        // Front
        if (!wasFrontPressed && HoldPerspectiveKeyMappings.HoldPerspective.thirdPersonFront.isDown()) {
            wasFrontPressed = true;
            setCameraType(minecraft, wasBackPressed, true);
        } else if (wasFrontPressed && !HoldPerspectiveKeyMappings.HoldPerspective.thirdPersonFront.isDown()) {
            wasFrontPressed = false;
            minecraft.options.setCameraType(wasBackPressed ? CameraType.THIRD_PERSON_BACK : beforePressed);
        }

        if (!wasFrontPressed && !wasBackPressed && beforePressed != null) beforePressed = null;
    }

    private static void setCameraType(Minecraft minecraft, boolean isHolding, boolean isFront) {
        if (!isHolding) beforePressed = minecraft.options.getCameraType();
        minecraft.options.setCameraType(isFront ? CameraType.THIRD_PERSON_FRONT : CameraType.THIRD_PERSON_BACK);
    }

    public static boolean isHolding(Minecraft minecraft) {
        return isHoldingBack(minecraft) || isHoldingFront(minecraft);
    }

    public static boolean isHoldingBack(Minecraft minecraft) {
        return wasBackPressed && minecraft.options.getCameraType().equals(CameraType.THIRD_PERSON_BACK);
    }

    public static boolean isHoldingFront(Minecraft minecraft) {
        return wasFrontPressed && minecraft.options.getCameraType().equals(CameraType.THIRD_PERSON_FRONT);
    }

    public static float getBackMultiplier() {
        return CameraTypeRegistry.clampMultiplier(PerspectiveConfig.config.cameraType.holdPerspective.backMultiplier.value());
    }

    public static float getFrontMultiplier() {
        return CameraTypeRegistry.clampMultiplier(PerspectiveConfig.config.cameraType.holdPerspective.frontMultiplier.value());
    }
}
