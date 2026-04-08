/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.cameratypes.perspectives;

import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;

public class HoldPerspective {
    public static CameraType beforePressed;
    public static boolean wasBackPressed;
    public static boolean wasFrontPressed;

    public static void onInitializeClient() {
        Log.info("Initializing hold perspective...");
    }

    public static void onTickClient(Minecraft minecraft) {
        // Back
        if (!wasBackPressed && KeyMappingRegistry.CameraType.HoldPerspective.thirdPersonBack.isDown()) {
            wasBackPressed = true;
            setCameraType(minecraft, wasFrontPressed, false);
        } else if (wasBackPressed && !KeyMappingRegistry.CameraType.HoldPerspective.thirdPersonBack.isDown()) {
            wasBackPressed = false;
            minecraft.options.setCameraType(wasFrontPressed ? CameraType.THIRD_PERSON_FRONT : beforePressed);
        }

        // Front
        if (!wasFrontPressed && KeyMappingRegistry.CameraType.HoldPerspective.thirdPersonFront.isDown()) {
            wasFrontPressed = true;
            setCameraType(minecraft, wasBackPressed, true);
        } else if (wasFrontPressed && !KeyMappingRegistry.CameraType.HoldPerspective.thirdPersonFront.isDown()) {
            wasFrontPressed = false;
            minecraft.options.setCameraType(wasBackPressed ? CameraType.THIRD_PERSON_BACK : beforePressed);
        }

        if (!wasFrontPressed && !wasBackPressed && beforePressed != null) beforePressed = null;
    }

    private static void setCameraType(Minecraft minecraft, boolean isHolding, boolean isFront) {
        if (!isHolding) beforePressed = minecraft.options.getCameraType();
        minecraft.options.setCameraType(isFront ? CameraType.THIRD_PERSON_FRONT : CameraType.THIRD_PERSON_BACK);
    }
}
