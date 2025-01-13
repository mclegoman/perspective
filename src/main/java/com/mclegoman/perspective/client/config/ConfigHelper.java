/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.config.ConfigScreen;
import com.mclegoman.perspective.client.toasts.Toast;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.quiltmc.config.api.values.ValueTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigHelper {
	public static final boolean experimentsAvailable = false;
	public static boolean showReloadOverlay = false;
	private static boolean seenDevelopmentWarning = false;
	private static boolean showDowngradeWarning = false;
	private static boolean seenDowngradeWarning = false;
	private static boolean showLicenseUpdateNotice = false;
	private static boolean seenLicenseUpdateNotice = false;
	public static void init() {
	}
	public static void tick() {
		try {
			if (Keybindings.openConfig.wasPressed())
				ClientData.minecraft.setScreen(new ConfigScreen(ClientData.minecraft.currentScreen, false, 1));
			showToasts();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.WARN, "Failed to tick config!");
		}
	}
	private static void showToasts() {
		if (Data.version.isDevelopmentBuild() && !seenDevelopmentWarning) {
			Data.version.sendToLog(LogType.INFO, "Development Build: Please help us improve by submitting bug reports if you encounter any issues.");
			ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.version.getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.version.getID(), "name"), Translation.getTranslation(Data.version.getID(), "toasts.development_warning.title")}), Translation.getTranslation(Data.version.getID(), "toasts.development_warning.description")));
			seenDevelopmentWarning = true;
		}
		if (showDowngradeWarning && !seenDowngradeWarning) {
			Data.version.sendToLog(LogType.INFO, "Downgrading is not supported: You may experience configuration related issues.");
			ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.version.getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.version.getID(), "name"), Translation.getTranslation(Data.version.getID(), "toasts.downgrade_warning.title")}), Translation.getTranslation(Data.version.getID(), "toasts.downgrade_warning.description")));
			seenDowngradeWarning = true;
		}
		if (showLicenseUpdateNotice && !seenLicenseUpdateNotice) {
			Data.version.sendToLog(LogType.INFO, "License Update: Perspective is now licensed under LGPL-3.0-or-later.");
			ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.version.getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.version.getID(), "name"), Translation.getTranslation(Data.version.getID(), "toasts.license_update.title")}), Translation.getTranslation(Data.version.getID(), "toasts.license_update.description")));
			seenLicenseUpdateNotice = true;
		}
	}
	public static List<Text> getDebugConfigText(ConfigType... types) {
		List<Text> text = new ArrayList<>();
		int typeAmount = 0;
		if (Arrays.stream(types).toList().contains(ConfigType.normal)) {
			typeAmount += 1;
			text.add(Translation.getTranslation(Data.version.getID(), "debug.config.normal", new Formatting[]{Formatting.BOLD}));
			for (ValueTreeNode treeNode : PerspectiveConfig.config.nodes())
				text.add(Text.literal(treeNode.key() + ": " + PerspectiveConfig.config.getValue(treeNode.key())));
		}
//		if (Arrays.stream(types).toList().contains(ConfigType.experimental)) {
//			if (experimentsAvailable) {
//				typeAmount += 1;
//				if (typeAmount > 1) text.add(Text.empty());
//				text.add(Translation.getTranslation(Data.version.getID(), "debug.config.experimental", new Formatting[]{Formatting.BOLD}));
//				for (Couple<String, ?> couple : ExperimentalConfig.configProvider.getConfigList())
//					text.add(Text.literal(couple.getFirst() + ": " + couple.getSecond()));
//			}
//		}
//		if (Arrays.stream(types).toList().contains(ConfigType.tutorial)) {
//			typeAmount += 1;
//			if (typeAmount > 1) text.add(Text.empty());
//			text.add(Translation.getTranslation(Data.version.getID(), "debug.config.tutorial", new Formatting[]{Formatting.BOLD}));
//			for (Couple<String, ?> couple : TutorialsConfig.configProvider.getConfigList())
//				text.add(Text.literal(couple.getFirst() + ": " + couple.getSecond()));
//		}
//		if (Arrays.stream(types).toList().contains(ConfigType.warning)) {
//			typeAmount += 1;
//			if (typeAmount > 1) text.add(Text.empty());
//			text.add(Translation.getTranslation(Data.version.getID(), "debug.config.warning", new Formatting[]{Formatting.BOLD}));
//			for (Couple<String, ?> couple : WarningsConfig.configProvider.getConfigList())
//				text.add(Text.literal(couple.getFirst() + ": " + couple.getSecond()));
//		}
		return text;
	}
	public enum ConfigType {
		normal,
		experimental,
		tutorial,
		warning
	}
}