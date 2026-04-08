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

public class SwapPerspective {
    public static void onInitializeClient() {
        Log.info("Initializing swap perspective...");
    }

    public static void onTickClient(Minecraft minecraft) {
        if (KeyMappingRegistry.CameraType.SwapPerspective.firstPerson.consumeClick()) setCameraType(minecraft, CameraType.FIRST_PERSON);
        else if (KeyMappingRegistry.CameraType.SwapPerspective.thirdPersonBack.consumeClick()) setCameraType(minecraft, CameraType.THIRD_PERSON_BACK);
        else if (KeyMappingRegistry.CameraType.SwapPerspective.thirdPersonFront.consumeClick()) setCameraType(minecraft, CameraType.THIRD_PERSON_FRONT);
    }

    private static void setCameraType(Minecraft minecraft, CameraType cameraType) {
        if (HoldPerspective.wasBackPressed || HoldPerspective.wasFrontPressed) HoldPerspective.beforePressed = cameraType;
        else minecraft.options.setCameraType(cameraType);
    }
}
