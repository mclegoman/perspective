/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.shaders;

import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.client.registry.shaders.renderers.KaleidoscopeRenderer;
import dev.dannytaylor.perspective.client.registry.shaders.renderers.SuperSecretSettingsRenderer;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ShaderRenderers {
    public static void onInitializeClient() {
        Log.info("Initializing shader renderers...");
        try {
            //initUniforms();
            KaleidoscopeRenderer.onInitializeClient();
            SuperSecretSettingsRenderer.onInitializeClient();
        } catch (Exception error) {
            Log.error("Failed to initialize shader registries: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        SuperSecretSettingsRenderer.onTickClient(minecraft);
    }

    public static boolean getPhotosensitivity(ShaderRegistryEntry shaderRegistryEntry) {
        return false; // todo;
    }

    public static boolean canRenderPhotosensitiveShaders() {
        return false; // todo;
    }

    public static Optional<Identifier> guessShaderStackId(@NotNull String id) {
        return ShaderStacks.guessStackId(id.toLowerCase().replace(" ", "_"));
    }
}
