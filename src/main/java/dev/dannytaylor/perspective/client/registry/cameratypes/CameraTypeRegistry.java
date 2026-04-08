/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.cameratypes;

import dev.dannytaylor.perspective.client.registry.cameratypes.perspectives.HoldPerspective;
import dev.dannytaylor.perspective.client.registry.cameratypes.perspectives.SwapPerspective;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;

public class CameraTypeRegistry {
    public static void onInitializeClient() {
        Log.info("Initializing camera type registries...");
        try {
            HoldPerspective.onInitializeClient();
            SwapPerspective.onInitializeClient();
        } catch (Exception error) {
            Log.error("Failed to initialize camera type registries: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        HoldPerspective.onTickClient(minecraft);
        SwapPerspective.onTickClient(minecraft);
    }
}
