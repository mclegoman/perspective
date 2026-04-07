/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry;

import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.client.registry.shaders.ShaderRenderers;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;

public class ClientRegistry {
    public static void onInitializeClient() {
        Log.info("Initializing client registries...");
        try {
            KeyMappingRegistry.onInitializeClient();
            ShaderRenderers.onInitializeClient();
        } catch (Exception error) {
            Log.error("Failed to initialize client registries: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        ShaderRenderers.onTickClient(minecraft);
    }
}
