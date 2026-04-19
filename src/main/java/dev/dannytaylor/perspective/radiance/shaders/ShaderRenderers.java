/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance.shaders;

import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.radiance.shaders.renderers.KaleidoscopeRenderer;
import dev.dannytaylor.perspective.radiance.shaders.renderers.SuperSecretSettingsRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ShaderRenderers {
    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Shader Renderers", () -> {
            initUniforms();
            KaleidoscopeRenderer.onInitializeClient(mod);
            SuperSecretSettingsRenderer.onInitializeClient(mod);
        });
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

    public static void initUniforms() {
        // TODO: zoom multiplier
    }
}
