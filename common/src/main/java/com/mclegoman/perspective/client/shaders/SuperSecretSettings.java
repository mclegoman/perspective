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
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.*;

public class SuperSecretSettings {
	private static final Map<Identifier, ShaderPack> registry = new HashMap<>();
	private static Formatting prevColor;
	private static final Formatting[] colors;
	public static void init() {
		ShaderPackDataLoaderInit.init();
		Events.AfterShaderDataRegistered.register(getSuperSecretSettingsId(), () -> {
			addDefaultShaderPacks();
			applyShader();
		});
		initUniforms();
	}
	public static Map<Identifier, ShaderPack> getRegistry() {
		return registry;
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
		}
		return shaders;
	}
	public static ShaderPack getShaderPack(Identifier id) {
		return getRegistry().get(id);
	}
	public static Formatting getRandomColor() {
		Random random = new Random();
		Formatting color = prevColor;
		while (color == prevColor) color = colors[(random.nextInt(colors.length))];
		prevColor = color;
		return color;
	}
	public static Identifier getSuperSecretSettingsId() {
		return Identifier.of(Data.getVersion().getID(), "super_secret_settings");
	}
	public static Identifier getSuperSecretSettingsId(String string) {
		return getSuperSecretSettingsId().withPath(getSuperSecretSettingsId().getPath() + "_" + string);
	}
	static {
		colors = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
	}
}
