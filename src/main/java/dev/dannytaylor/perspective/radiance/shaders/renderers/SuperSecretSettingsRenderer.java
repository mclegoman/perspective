/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance.shaders.renderers;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import com.mclegoman.luminance.client.shaders.Shaders;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.radiance.RadianceClient;
import dev.dannytaylor.perspective.radiance.config.RadianceConfig;
import dev.dannytaylor.perspective.radiance.events.RadianceEvents;
import dev.dannytaylor.perspective.radiance.keymappings.RadianceKeyMappings;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.radiance.shaders.ShaderRenderers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class SuperSecretSettingsRenderer {
    private static final List<RenderLocations.RenderLocation<?>> cyclableRenderLocations = new ArrayList<>();

    public static void registerCyclableRenderLocation(RenderLocations.RenderLocation<?> renderLocation) {
        if (!cyclableRenderLocations.contains(renderLocation)) cyclableRenderLocations.add(renderLocation);
    }

    public static void onInitializeClient(PerspectiveMod mod) {
        RadianceEvents.onInitialize(mod, "Super Secret Settings Renderer", () -> {
            registerCyclableRenderLocation(RenderLocations.GAME);
            registerCyclableRenderLocation(RenderLocations.UI);
            RadianceEvents.AfterClientResourceReload.register(getIdentifier(), SuperSecretSettingsRenderer::applyShaderStack);
        });
    }

    public static void onTickClient(Minecraft minecraft) {
        if (RadianceKeyMappings.SuperSecretSettingsKeyMappings.cycleShader.consumeClick()) cycleShaderStack(minecraft.hasShiftDown());
        if (RadianceKeyMappings.SuperSecretSettingsKeyMappings.cycleRenderLocation.consumeClick()) cycleRenderLocation(minecraft.hasShiftDown());
        if (RadianceKeyMappings.SuperSecretSettingsKeyMappings.toggleShader.consumeClick()) toggleShaderStack();
    }

    public static Identifier getIdentifier() {
        return RadianceClient.idOf("super_secret_settings");
    }

    public static void applyShaderStack() {
        RadianceEvents.ShaderRender.register(getIdentifier());
        RadianceEvents.ShaderRender.modify(getIdentifier(), getShadersFromId(getIdentifier()));
    }

    private static RadianceEvents.ShaderRenderData getShadersFromId(Identifier renderId) {
        return ShaderStacks.getShaders(
                renderId,
                ShaderStacks.getStack(RadianceConfig.instance.superSecretSettings.shaderStack.value().getIdentifier()),
                SuperSecretSettingsRenderer::getRenderLocation,
                SuperSecretSettingsRenderer::isEnabled,
                ShaderRenderers::getPhotosensitivity
        );
    }

    public static void setShaderStack(Identifier stackId) {
        setShaderStack(stackId, true);
    }

    public static void setShaderStack(Identifier stackId, boolean shouldApply) {
        RadianceConfig.instance.superSecretSettings.shaderStack.setValue(ConfigIdentifier.of(stackId), true);
        if (shouldApply) applyShaderStack();
    }

    public static RenderLocations.RenderLocation<?> getRenderLocation() {
        return RadianceEvents.RenderLocation.get(RadianceConfig.instance.superSecretSettings.renderLocation.value().getIdentifier());
    }

    public static boolean isEnabled() {
        return RadianceConfig.instance.superSecretSettings.enabled.value() != RadianceKeyMappings.SuperSecretSettingsKeyMappings.holdShader.isDown();
    }

    public static void cycleShaderStack(boolean forwards) {
        List<Identifier> shaderStacks = getShaderStacks(ShaderRenderers.canRenderPhotosensitiveShaders());
        setShaderStack(shaderStacks.get(forwards ? (shaderStacks.indexOf(RadianceConfig.instance.superSecretSettings.shaderStack.value().getIdentifier()) + 1) % shaderStacks.size() : (shaderStacks.indexOf(RadianceConfig.instance.superSecretSettings.shaderStack.value().getIdentifier()) - 1 + shaderStacks.size()) % shaderStacks.size()));
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
        RadianceConfig.instance.superSecretSettings.enabled.setValue(!RadianceConfig.instance.superSecretSettings.enabled.value(), true);
    }

    public static List<Identifier> getCyclableRenderLocations() {
        return cyclableRenderLocations.stream().map(RenderLocations.RenderLocation::identifier).toList();
    }

    public static void cycleRenderLocation(boolean forwards) {
        List<Identifier> renderLocations = getCyclableRenderLocations();
        setRenderLocation(renderLocations.get(forwards ? (renderLocations.indexOf(RadianceConfig.instance.superSecretSettings.renderLocation.value().getIdentifier()) + 1) % renderLocations.size() : (renderLocations.indexOf(RadianceConfig.instance.superSecretSettings.renderLocation.value().getIdentifier()) - 1 + renderLocations.size()) % renderLocations.size()));
    }

    public static void setRenderLocation(Identifier identifier) {
        RadianceConfig.instance.superSecretSettings.renderLocation.setValue(ConfigIdentifier.of(identifier), true);
    }
}
