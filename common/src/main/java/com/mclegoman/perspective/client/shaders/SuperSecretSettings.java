/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.io.FileNotFoundException;
import java.util.*;

public class SuperSecretSettings {
	private static final Random random;
	protected static final Reload reload;
	private static final Map<Identifier, ShaderPack> registry;
	private static Formatting prevColor;
	private static final Formatting[] colors;
	public static void init() {
		ShaderPackDataLoaderInit.init();
		Events.AfterShaderDataRegistered.register(getSuperSecretSettingsId(), reload::reloadLuminance);
		initUniforms();
	}
	public static void tick() {
		if (reload.canReload()) reload();
		if (Keybindings.cycleShaders.wasPressed()) {
			cycle(!ClientData.minecraft.options.sneakKey.wasPressed());
			if (PerspectiveConfig.config.superSecretSettingsShowName.value() && getShader() != null) MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.shader", getShader().translation().getTranslation()).formatted(getRandomColor()));
		}
		if (Keybindings.toggleShaders.wasPressed()) {
			toggle();
			if (PerspectiveConfig.config.superSecretSettingsShowName.value()) MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.shader", Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.superSecretSettingsEnabled.value(), Translation.Type.ENDISABLE)).formatted(getRandomColor()));
		}
	}
	public static Map<Identifier, ShaderPack> getRegistry() {
		return registry;
	}
	private static void removeFromRegistry(Identifier identifier) {
		Data.getVersion().sendToLog(LogType.INFO, "Removing '" + identifier.toString() + "' from Super Secret Settings registry!");
		registry.remove(identifier);
	}
	public static List<Identifier> getRegistryIds() {
		return new ArrayList<>(getRegistry().keySet());
	}
	public static void addToRegistry(Identifier id, ShaderPack.Translation translation, List<ShaderPack.Shader> shaders) {
		registry.put(id, new ShaderPack(translation, shaders));
	}
	public static void resetRegistry() {
		registry.clear();
	}
	private static void addDefaultShaderPacks() {
		for (ShaderRegistryEntry shader : Shaders.getRegistry()) addToRegistry(shader.getID(), new ShaderPack.Translation(shader.getTranslatable(), shader.getID(), false), List.of(new ShaderPack.Shader(Shaders.getMainRegistryId(), shader.getID())));
	}
	private static void initUniforms() {
		try {
			String path = Data.getVersion().getID();
			Uniforms.registerSingleTree(path, "zoomMultiplier", (tickDelta) -> Zoom.getMultiplier(), null, null);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize uniforms: {}", error));
		}
	}
	public static ShaderPack getShader() {
		return getRegistry().get(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
	}
	public static void setShader(Identifier id) {
		PerspectiveConfig.config.superSecretSettingsShader.setValue(ConfigIdentifier.of(id), true);
		applyShader();
	}
	protected static void applyShader() {
		Events.ShaderRender.register(getSuperSecretSettingsId(), new ArrayList<>());
		Events.ShaderRender.modify(getSuperSecretSettingsId(), getShaders());
	}
	private static List<Shader.Data> getShaders() {
		List<Shader.Data> shaders = new ArrayList<>();
		ShaderPack shaderPack = getShaderPack(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
		if (shaderPack != null) {
			int i = 0;
			for (ShaderPack.Shader shader : shaderPack.shaders()) shaders.add(new Shader.Data(getSuperSecretSettingsId(String.valueOf(i++)), new Shader(Shaders.get(shader.registry(), shader.luminanceId()), () -> PerspectiveConfig.config.superSecretSettingsMode.value().getRenderType(), PerspectiveConfig.config.superSecretSettingsEnabled::value)));
		} else Data.getVersion().sendToLog(LogType.WARN, "Could not locate the current shader pack!");
		return shaders;
	}
	public static ShaderPack getShaderPack(Identifier id) {
		return getRegistry().get(id);
	}
	public static Formatting getRandomColor() {
		Formatting color = prevColor;
		while (color == prevColor) color = colors[(random.nextInt(colors.length))];
		prevColor = color;
		return color;
	}
	public static void cycleShaderMode() {
		switch (PerspectiveConfig.config.superSecretSettingsMode.value()) {
			case ShaderRenderType.screen -> PerspectiveConfig.config.superSecretSettingsMode.setValue(ShaderRenderType.game, true);
			case ShaderRenderType.game -> PerspectiveConfig.config.superSecretSettingsMode.setValue(ShaderRenderType.screen, true);
		}
	}
	public static Identifier getSuperSecretSettingsId() {
		return Identifier.of(Data.getVersion().getID(), "super_secret_settings");
	}
	public static Identifier getSuperSecretSettingsId(String string) {
		return getSuperSecretSettingsId().withPath(getSuperSecretSettingsId().getPath() + "_" + string);
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
		if (isShadersEnabled()) {
			setShader(getRegistryIds().get(forwards ? (getRegistryIds().indexOf(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier()) + 1) % getRegistryIds().size() : (getRegistryIds().indexOf(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier()) - 1 + getRegistryIds().size()) % getRegistryIds().size()));
			PerspectiveConfig.config.superSecretSettingsEnabled.setValue(true, true);
		}
	}
	public static void toggle() {
		PerspectiveConfig.config.superSecretSettingsEnabled.setValue(!PerspectiveConfig.config.superSecretSettingsEnabled.value(), true);
	}
	protected static void reload() {
		reload.finishReload();
		addDefaultShaderPacks();
		clean();
		applyShader();
	}
	private static void clean() {
		List<Identifier> remove = new ArrayList<>();
		getRegistry().forEach((id, shaderPack) -> {
			for (ShaderPack.Shader shader : shaderPack.shaders()) {
				try {
					ShaderRegistryEntry shaderRegistryEntry = Shaders.get(shader.registry(), shader.luminanceId());
					if (shaderRegistryEntry == null) {
						remove.add(id);
						break;
					}
					else ClientData.minecraft.getResourceManager().getResourceOrThrow(shaderRegistryEntry.getPostEffect(true));
				} catch (FileNotFoundException error) {
					remove.add(id);
					break;
				}
			}
		});
		remove.forEach(SuperSecretSettings::removeFromRegistry);
	}
	static {
		random = new Random();
		reload = new Reload();
		registry = new HashMap<>();
		colors = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
	}
	protected static class Reload {
		protected boolean perspective;
		protected boolean luminance;
		protected void reloadPerspective() {
			this.perspective = true;
		}
		protected void reloadLuminance() {
			this.luminance = true;
		}
		protected void finishReload() {
			this.perspective = false;
			this.luminance = false;
		}
		protected boolean canReload() {
			return this.perspective && this.luminance;
		}
	}
}
