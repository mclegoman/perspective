/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.ShaderRegistryEntry;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.shaders.Uniforms;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperSecretSettings {
	private static final List<ShaderPack> registry = new ArrayList<>();
	private static Formatting prevColor;
	private static final Formatting[] colors;
	public static void init() {
		ShaderPackDataLoaderInit.init();
		Events.AfterShaderDataRegistered.register(getSuperSecretSettingsId(), SuperSecretSettings::addDefaultShaderPacks);
		initUniforms();
	}
	public static List<ShaderPack> getRegistry() {
		return registry;
	}
	public static void addToRegistry(ShaderPack.Translation translation, List<ShaderPack.Shader> shaders) {
		registry.add(new ShaderPack(translation, shaders));
	}
	public static void resetRegistry() {
		registry.clear();
	}
	private static void addDefaultShaderPacks() {
		for (ShaderRegistryEntry shader : Shaders.getRegistry()) addToRegistry(new ShaderPack.Translation(shader.getTranslatable(), shader.getID(), false), List.of(new ShaderPack.Shader(Shaders.getMainRegistryId(), shader.getID())));

		for (ShaderPack shaderPack : getRegistry()) Data.getVersion().sendToLog(LogType.INFO, shaderPack.translation().toString() + ":" + shaderPack.shaders().toString());
	}
	private static void initUniforms() {
		try {
			String path = Data.getVersion().getID();
			Uniforms.registerSingleTree(path, "zoomMultiplier", (tickDelta) -> Zoom.getMultiplier(), null, null);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize uniforms: {}", error));
		}
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
	static {
		colors = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
	}
}
