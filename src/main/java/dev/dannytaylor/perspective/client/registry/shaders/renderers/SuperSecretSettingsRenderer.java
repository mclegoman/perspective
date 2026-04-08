/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.shaders.renderers;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import com.mclegoman.luminance.client.shaders.Shaders;
import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.client.registry.shaders.ShaderRenderers;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SuperSecretSettingsRenderer {
    private static final List<RenderLocations.RenderLocation> cyclableRenderLocations = new ArrayList<>();

    public static void registerCyclableRenderLocation(RenderLocations.RenderLocation renderLocation) {
        if (!cyclableRenderLocations.contains(renderLocation)) cyclableRenderLocations.add(renderLocation);
    }

    public static void onInitializeClient() {
        Log.info("Initializing super secret settings renderer...");
        try {
            registerCyclableRenderLocation(RenderLocations.WORLD);
            registerCyclableRenderLocation(RenderLocations.UI);
            Events.AfterClientResourceReload.register(getId(), SuperSecretSettingsRenderer::applyShaderStack);
        } catch (Exception error) {
            Log.error("Failed to initialize super secret settings renderer {}: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        if (KeyMappingRegistry.ShaderKeyMappings.SuperSecretSettingsKeyMappings.cycleShader.consumeClick()) cycleShaderStack(minecraft.hasShiftDown());
        if (KeyMappingRegistry.ShaderKeyMappings.SuperSecretSettingsKeyMappings.cycleRenderLocation.consumeClick()) cycleRenderLocation(minecraft.hasShiftDown());
        if (KeyMappingRegistry.ShaderKeyMappings.SuperSecretSettingsKeyMappings.toggleShader.consumeClick()) toggleShaderStack();
    }

    public static Identifier getId() {
        return Identifiers.SUPER_SECRET_SETTINGS;
    }

    public static void applyShaderStack() {
        Events.ShaderRender.register(getId());
        Events.ShaderRender.modify(getId(), getShadersFromId(getId()));
    }

    private static Events.ShaderRenderData getShadersFromId(Identifier renderId) {
        return ShaderStacks.getShaders(
                renderId,
                ShaderStacks.getStack(PerspectiveConfig.config.shaders.superSecretSettings.shaderStack.value().getIdentifier()),
                SuperSecretSettingsRenderer::getRenderLocation,
                SuperSecretSettingsRenderer::isEnabled,
                ShaderRenderers::getPhotosensitivity
        );
    }

    public static void setShaderStack(Identifier stackId) {
        setShaderStack(stackId, true);
    }

    public static void setShaderStack(Identifier stackId, boolean shouldApply) {
        PerspectiveConfig.config.shaders.superSecretSettings.shaderStack.setValue(ConfigIdentifier.of(stackId), true);
        if (shouldApply) applyShaderStack();
    }

    public static RenderLocations.RenderLocation getRenderLocation() {
        return Events.RenderLocation.get(PerspectiveConfig.config.shaders.superSecretSettings.renderLocation.value().getIdentifier());
    }

    public static boolean isEnabled() {
        return PerspectiveConfig.config.shaders.superSecretSettings.enabled.value() != KeyMappingRegistry.ShaderKeyMappings.SuperSecretSettingsKeyMappings.holdShader.isDown();
    }

    public static void cycleShaderStack(boolean forwards) {
        List<Identifier> shaderStacks = getShaderStacks(ShaderRenderers.canRenderPhotosensitiveShaders());
        setShaderStack(shaderStacks.get(forwards ? (shaderStacks.indexOf(PerspectiveConfig.config.shaders.superSecretSettings.shaderStack.value().getIdentifier()) + 1) % shaderStacks.size() : (shaderStacks.indexOf(PerspectiveConfig.config.shaders.superSecretSettings.shaderStack.value().getIdentifier()) - 1 + shaderStacks.size()) % shaderStacks.size()));
        if (!isEnabled()) toggleShaderStack();
    }

    public static List<Identifier> getShaderStacks(boolean isPhotosensitive) {
        if (!isPhotosensitive) return ShaderStacks.getShaderStacks();
        else {
            List<Identifier> shaderStacks = new ArrayList<>();
            ShaderStacks.getRegistry().forEach((stackId, stackEntry) -> {
                List<ShaderStacks.Entry.ShaderInfo> shaderInfos = stackEntry.shaders();
                boolean allPhotosensitive = true;
                for (ShaderStacks.Entry.ShaderInfo shaderInfo : shaderInfos) {
                    ShaderRegistryEntry shaderEntry = Shaders.get(shaderInfo.shaderRegistryId(), shaderInfo.shaderId());
                    if (shaderEntry == null || !shaderEntry.isPhotosensitive()) {
                        allPhotosensitive = false;
                        break;
                    }
                }
                if (!allPhotosensitive) shaderStacks.add(stackId);
            });
            return shaderStacks;
        }
    }

    public static void toggleShaderStack() {
        PerspectiveConfig.config.shaders.superSecretSettings.enabled.setValue(!PerspectiveConfig.config.shaders.superSecretSettings.enabled.value(), true);
    }

    public static List<Identifier> getCyclableRenderLocations() {
        return cyclableRenderLocations.stream().map(RenderLocations.RenderLocation::identifier).toList();
    }

    public static void cycleRenderLocation(boolean forwards) {
        List<Identifier> renderLocations = getCyclableRenderLocations();
        setRenderLocation(renderLocations.get(forwards ? (renderLocations.indexOf(PerspectiveConfig.config.shaders.superSecretSettings.renderLocation.value().getIdentifier()) + 1) % renderLocations.size() : (renderLocations.indexOf(PerspectiveConfig.config.shaders.superSecretSettings.renderLocation.value().getIdentifier()) - 1 + renderLocations.size()) % renderLocations.size()));
    }

    public static void setRenderLocation(Identifier identifier) {
        PerspectiveConfig.config.shaders.superSecretSettings.renderLocation.setValue(ConfigIdentifier.of(identifier), true);
    }
}
