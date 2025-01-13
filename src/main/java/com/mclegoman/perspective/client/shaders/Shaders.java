/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.shaders.ShaderDataloader;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Random;

public class Shaders {
	public static Identifier superSecretSettingsId;
	private static final Formatting[] colors;
	private static Formatting lastColor;
	public static void init() {
		Uniforms.init();
	}
	public static void tick() {
		Uniforms.tick();
	}
	public static Formatting getRandomColor() {
		Random random = new Random();
		Formatting color = lastColor;
		while (color == lastColor) color = colors[(random.nextInt(colors.length))];
		lastColor = color;
		return color;
	}
	private static void showToasts() {
//		boolean save = false;
//		if ((boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.normal, "tutorials")) {
//			if (!(boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.tutorial, "super_secret_settings")) {
//				ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.version.getID(), "toasts.tutorial.title", new Object[]{Translation.getTranslation(Data.version.getID(), "name"), Translation.getTranslation(Data.version.getID(), "toasts.tutorial.super_secret_settings.title")}), Translation.getTranslation(Data.version.getID(), "toasts.tutorial.super_secret_settings.description", new Object[]{KeyBindingHelper.getBoundKeyOf(Keybindings.cycleShaders).getLocalizedText(), KeyBindingHelper.getBoundKeyOf(Keybindings.toggleShaders).getLocalizedText(), KeyBindingHelper.getBoundKeyOf(Keybindings.openConfig).getLocalizedText()})));
//				ConfigHelper.setConfig(ConfigHelper.ConfigType.tutorial, "super_secret_settings", true);
//				save = true;
//			}
//		}
//		if (!(boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.warning, "photosensitivity")) {
//			ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.version.getID(), "toasts.warning.title", new Object[]{Translation.getTranslation(Data.version.getID(), "name"), Translation.getTranslation(Data.version.getID(), "toasts.warning.photosensitivity.title")}), Translation.getTranslation(Data.version.getID(), "toasts.warning.photosensitivity.description")));
//			ConfigHelper.setConfig(ConfigHelper.ConfigType.warning, "photosensitivity", true);
//			save = true;
//		}
//		if (save) ConfigHelper.saveConfig();
	}
	public static boolean isShaderButtonsEnabled() {
		return ShaderDataloader.getShaderAmount() > 1;
	}
	static {
		superSecretSettingsId = Identifier.of(Data.version.getID(), "super_secret_settings");
		colors = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
	}
}
