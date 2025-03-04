/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.shaders.Uniforms;
import com.mclegoman.luminance.client.util.MessageOverlay;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.config.value.ConfigIdentifier;
import com.mclegoman.perspective.client.config.value.ShaderRenderType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SuperSecretSettings {
	private static final Random random;
	private static final Map<Identifier, Map<Identifier, ShaderPackEntry>> registries;
	private static Formatting prevColor;
	private static final Formatting[] colors;
	public static void init() {
		Events.AfterShaderDataRegistered.register(getShadersId(), SuperSecretSettings::reload);
		initUniforms();
	}
	public static void tick() {
		if (Keybindings.cycleShaders.wasPressed()) {
			cycle(!ClientData.minecraft.options.sneakKey.isPressed());
			if (PerspectiveConfig.config.superSecretSettingsShowName.value()) {
				ShaderPackEntry pack = getShader();
				if (pack != null) MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.shader", pack.translation().getTranslation(shouldShowNamespace(getShadersId(), pack.translation().id()))).formatted(getRandomColor()));
			}
		}
		if (Keybindings.toggleShaders.wasPressed()) {
			toggle();
			if (PerspectiveConfig.config.superSecretSettingsShowName.value()) MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.shader", Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.superSecretSettingsEnabled.value(), Translation.Type.ENDISABLE)).formatted(getRandomColor()));
		}
	}
	public static boolean shouldShowNamespace(Identifier registryId, Identifier shaderId) {
		List<String> shaderNames = new ArrayList<>();
		for (Identifier shader : getRegistry(registryId).keySet()) if (shaderId.getPath().equals(shader.getPath())) shaderNames.add(shaderId.getPath());
		return shaderNames.size() > 1;
	}
	public static Map<Identifier, ShaderPackEntry> getRegistry() {
		return getRegistry(getShadersId());
	}
	public static Map<Identifier, ShaderPackEntry> getRegistry(Identifier registryId) {
		if (!registries.containsKey(registryId)) registries.put(registryId, new HashMap<>());
		return registries.get(registryId);
	}
	private static void removeFromRegistry(Identifier identifier) {
		Data.getVersion().sendToLog(LogType.INFO, "Removing '" + identifier.toString() + "' from Super Secret Settings registry!");
		registries.remove(identifier);
	}
	public static List<Identifier> getRegistryIds(Identifier registryId) {
		return getRegistry(registryId).keySet().stream().sorted().toList();
	}
	public static List<Identifier> getRegistryIds() {
		return getRegistryIds(getShadersId());
	}
	public static void addToRegistry(Identifier shaderId, ShaderPackEntry.Translation translation, List<ShaderPackEntry.Shader> shaders, JsonObject customData) {
		addToRegistry(getShadersId(), shaderId, translation, shaders, customData);
	}
	public static void addToRegistry(Identifier registryId, Identifier shaderId, ShaderPackEntry.Translation translation, List<ShaderPackEntry.Shader> shaders, JsonObject customData) {
		getRegistry(registryId).put(shaderId, new ShaderPackEntry(translation, shaders, customData));
	}
	public static void resetRegistry() {
		registries.clear();
	}
	private static void addDefaultShaderPacks() {
		for (ShaderRegistryEntry shader : Shaders.getRegistry()) addToRegistry(shader.getID(), new ShaderPackEntry.Translation(shader.getTranslatable(), shader.getID(), false, shader.getDescription()), List.of(new ShaderPackEntry.Shader(Shaders.getMainRegistryId(), shader.getID(), new ArrayList<>())), new JsonObject());
	}
	private static void initUniforms() {
		try {
			String path = Data.getVersion().getID();
			Uniforms.registerSingleTree(path, "zoomMultiplier", (tickDelta) -> Zoom.getMultiplier(), null, null);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize uniforms: {}", error));
		}
	}
	public static ShaderPackEntry getShader() {
		return getRegistry().get(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
	}
	public static void setShader(Identifier id) {
		setShader(id, true);
	}
	public static void setShader(Identifier id, boolean applyShader) {
		PerspectiveConfig.config.superSecretSettingsShader.setValue(ConfigIdentifier.of(id), true);
		if (applyShader) applyShader();
		PerspectiveConfig.config.superSecretSettingsEnabled.setValue(true, true);
	}
	protected static void applyShader() {
		Events.ShaderRender.register(getShadersId(), new ArrayList<>());
		Events.ShaderRender.modify(getShadersId(), getShaders());
	}
	private static List<Shader.Data> getShaders() {
		List<Shader.Data> shaders = new ArrayList<>();
		ShaderPackEntry shaderPack = getShaderPack(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
		if (shaderPack != null) {
			int i = 0;
			for (ShaderPackEntry.Shader shader : shaderPack.shaders()) {
				shaders.add(new Shader.Data(getShadersId(String.valueOf(i++)), new Shader(Shaders.get(shader.registry(), shader.luminance()), () -> PerspectiveConfig.config.superSecretSettingsMode.value().getRenderType(), PerspectiveConfig.config.superSecretSettingsEnabled::value)));
			}
		} else Data.getVersion().sendToLog(LogType.WARN, "Could not locate the current shader pack!");
		return shaders;
	}
	public static ShaderPackEntry getShaderPack(Identifier registryId, Identifier id) {
		return getRegistry(registryId).get(id);
	}
	public static ShaderPackEntry getShaderPack(Identifier id) {
		return getShaderPack(getShadersId(), id);
	}
	public static Optional<JsonObject> getCustom(String namespace) {
		return getCustom(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier(), namespace);
	}
	public static Optional<JsonObject> getCustom(Identifier id, String namespace) {
		ShaderPackEntry pack = getShaderPack(id);
		if (pack != null) {
			JsonObject customData = pack.customData();
			if (customData != null && customData.has(namespace)) return Optional.of(JsonHelper.getObject(customData, namespace));
		}
		return Optional.empty();
	}
	public static Formatting getRandomColor() {
		return getRandomColor(List.of(Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.DARK_GRAY));
	}
	public static Formatting getRandomColor(List<Formatting> forbiddenFormatting) {
		List<Formatting> formatting = new ArrayList<>();
		for (Formatting color : colors) if (!forbiddenFormatting.contains(color) && color != prevColor) formatting.add(color);
		Formatting color = formatting.get(random.nextInt(formatting.size()));
		prevColor = color;
		return color;
	}
	public static void cycleShaderMode() {
		switch (PerspectiveConfig.config.superSecretSettingsMode.value()) {
			case ShaderRenderType.screen -> PerspectiveConfig.config.superSecretSettingsMode.setValue(ShaderRenderType.game, true);
			case ShaderRenderType.game -> PerspectiveConfig.config.superSecretSettingsMode.setValue(ShaderRenderType.screen, true);
		}
	}
	public static Identifier getShadersId() {
		return Identifier.of(Data.getVersion().getID(), "main");
	}
	public static Identifier getShadersId(String string) {
		return getShadersId().withPath(getShadersId().getPath() + "_" + string);
	}
	public static int getShaderAmount() {
		return getRegistry().size();
	}
	public static boolean isShadersEnabled() {
		return getShaderAmount() > 0;
	}
	public static void randomize() {
		if (isShadersEnabled()) {
			Identifier shaderId = PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier();
			while (shaderId == PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier()) shaderId = getRegistryIds().get(random.nextInt(getRegistryIds().size()));
			setShader(shaderId);
		}
	}
	public static void cycle(boolean forwards) {
		if (isShadersEnabled()) setShader(getRegistryIds().get(forwards ? (getRegistryIds().indexOf(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier()) + 1) % getRegistryIds().size() : (getRegistryIds().indexOf(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier()) - 1 + getRegistryIds().size()) % getRegistryIds().size()));
	}
	public static void toggle() {
		PerspectiveConfig.config.superSecretSettingsEnabled.setValue(!PerspectiveConfig.config.superSecretSettingsEnabled.value(), true);
	}
	protected static void reload() {
		try {
			resetRegistry();
			ClientData.minecraft.getResourceManager().findResources("perspective/shader_packs", identifier -> identifier.getPath().endsWith(".json")).forEach((identifier, resource) -> {
				try (InputStream stream = resource.getInputStream()) {
					JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
					JsonObject reader = jsonElement.getAsJsonObject();
					JsonArray defaultRegistryIds = new JsonArray();
					defaultRegistryIds.add(getShadersId().toString());
					JsonArray registryIds = JsonHelper.getArray(reader, "registries", defaultRegistryIds);
					List<ShaderPackEntry.Shader> shaders = new ArrayList<>();
					for (JsonElement element : JsonHelper.getArray(reader, "shaders", new JsonArray())) {
						if (element instanceof JsonObject shaderData) {
							List<ShaderPackEntry.Uniform> uniforms = new ArrayList<>();
							for (JsonElement uniformElement : JsonHelper.getArray(shaderData, "uniforms", new JsonArray())) {
								if (uniformElement instanceof JsonObject uniform) {
									List<Float> uniformValues = new ArrayList<>();
									List<String> uniformOverrides = new ArrayList<>();
									for (JsonElement uniformValue : JsonHelper.getArray(uniform, "values")) uniformValues.add(uniformValue.getAsFloat());
									for (JsonElement uniformOverride : JsonHelper.getArray(uniform, "override")) uniformOverrides.add(uniformOverride.getAsString());
									uniforms.add(new ShaderPackEntry.Uniform(Identifier.of(JsonHelper.getString(uniform, "post_effect")), JsonHelper.getString(uniform, "name"), uniformValues, uniformOverrides));
								}
							}
							shaders.add(new ShaderPackEntry.Shader(
									Identifier.of(JsonHelper.getString(shaderData, "registry", Shaders.getMainRegistryId().toString())),
									Identifier.of(JsonHelper.getString(shaderData, "luminance")),
									uniforms
							));
						}
					}
					Identifier id = identifier.withPath(identifier.getPath().substring(identifier.getPath().lastIndexOf("/") + 1, identifier.getPath().lastIndexOf(".json")));
					registryIds.forEach((registryId) -> addToRegistry(Identifier.of(registryId.getAsString()), id, new ShaderPackEntry.Translation(JsonHelper.getBoolean(reader, "translatable", false), id, true, JsonHelper.getBoolean(reader, "description", false)), shaders, JsonHelper.getObject(reader, "custom", new JsonObject())));
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to load shader pack '{}': {}", identifier.withPath(identifier.getPath().substring(identifier.getPath().lastIndexOf("/") + 1, identifier.getPath().lastIndexOf(".json"))), error.getLocalizedMessage()));
				}
			});
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, com.mclegoman.luminance.client.translation.Translation.getString("Failed to apply shader packs dataloader: {}", error));
		}
		addDefaultShaderPacks();
		clean();
		applyShader();
	}
	private static void clean() {
		List<Identifier> remove = new ArrayList<>();
		registries.forEach((registryId, registry) -> {
			registry.forEach((id, shaderPack) -> {
				for (ShaderPackEntry.Shader shader : shaderPack.shaders()) {
					try {
						ShaderRegistryEntry shaderRegistryEntry = Shaders.get(shader.registry(), shader.luminance());
						if (shaderRegistryEntry == null) {
							Data.getVersion().sendToLog(LogType.WARN, shader.registry() + ":" + shader.luminance() + " returned null!");
							remove.add(id);
							break;
						}
						else ClientData.minecraft.getResourceManager().getResourceOrThrow(shaderRegistryEntry.getPostEffect(true));
					} catch (FileNotFoundException error) {
						Data.getVersion().sendToLog(LogType.WARN, error.getLocalizedMessage());
						remove.add(id);
						break;
					}
				}
			});
			remove.forEach(SuperSecretSettings::removeFromRegistry);
		});
	}
	public static Tooltip getTooltip() {
		return getTooltip(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
	}
	public static Tooltip getTooltip(Identifier shaderId) {
		ShaderPackEntry pack = getRegistry().get(shaderId);
		return pack != null && pack.translation().description() ? Tooltip.of(pack.translation().getDescription(SuperSecretSettings.shouldShowNamespace(getShadersId(), pack.translation().id()))) : null;
	}
	static {
		random = new Random();
		registries = new HashMap<>();
		colors = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
	}
}
