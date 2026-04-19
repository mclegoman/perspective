/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.cameratypes.perspectives;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.hold_perspective.config.HoldPerspectiveConfig;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import dev.dannytaylor.perspective.hold_perspective.keymappings.HoldPerspectiveKeyMappings;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.CameraTypeRegistry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;

public class SwapPerspective {
    public static void onInitializeClient(PerspectiveMod mod) {
        HoldPerspectiveEvents.onInitialize(mod, "Swap Perspective", () -> {});
    }

    public static void onTickClient(Minecraft minecraft) {
        if (HoldPerspectiveKeyMappings.SwapPerspective.firstPerson.consumeClick()) setCameraType(minecraft, CameraType.FIRST_PERSON);
        else if (HoldPerspectiveKeyMappings.SwapPerspective.thirdPersonBack.consumeClick()) setCameraType(minecraft, CameraType.THIRD_PERSON_BACK);
        else if (HoldPerspectiveKeyMappings.SwapPerspective.thirdPersonFront.consumeClick()) setCameraType(minecraft, CameraType.THIRD_PERSON_FRONT);
    }

    private static void setCameraType(Minecraft minecraft, CameraType cameraType) {
        if (HoldPerspective.wasBackPressed || HoldPerspective.wasFrontPressed) HoldPerspective.beforePressed = cameraType;
        else minecraft.options.setCameraType(cameraType);
    }

    public static boolean isMultiplierAdjustable(Minecraft minecraft) {
        return !minecraft.options.getCameraType().isFirstPerson();
    }

    public static float getBackMultiplier() {
        return CameraTypeRegistry.clampMultiplier(HoldPerspectiveConfig.instance.backMultiplier.value());
    }

    public static float getFrontMultiplier() {
        return CameraTypeRegistry.clampMultiplier(HoldPerspectiveConfig.instance.frontMultiplier.value());
    }
}
