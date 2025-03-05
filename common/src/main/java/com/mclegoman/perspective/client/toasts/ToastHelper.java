/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.toasts;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.keybindings.KeybindingHelper;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;

public class ToastHelper {
	public static final boolean experimentsAvailable = false;
	private static boolean seenDevelopmentWarning = false;
	private static boolean showDowngradeWarning = false;
	private static boolean seenDowngradeWarning = false;
	public static boolean seenConflictingKeybindingToasts = false;
	public static void showDowngradeWarning() {
		showDowngradeWarning = true;
	}
	public static void tick() {
		if (!seenDevelopmentWarning && Data.getVersion().isDevelopmentBuild()) {
			Data.getVersion().sendToLog(LogType.INFO, "Development Build: Please submit a bug report if you encounter any issues.");
			Toast.add(Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.development_warning.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.development_warning.description"));
			seenDevelopmentWarning = true;
		}
		if (!seenDowngradeWarning && showDowngradeWarning) {
			Data.getVersion().sendToLog(LogType.INFO, "Downgrading is not supported: You may experience configuration related issues.");
			Toast.add(Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.downgrade_warning.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.downgrade_warning.description"));
			seenDowngradeWarning = true;
		}
		if (!seenConflictingKeybindingToasts && KeybindingHelper.hasKeybindingConflicts(Keybindings.allKeybindings)) {
			Data.getVersion().sendToLog(LogType.INFO, Translation.getString("Conflicting Keybinding: Keybinding conflicts have been detected."));
			Toast.add(Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.keybinding_conflicts.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.keybinding_conflicts.description"));
			seenConflictingKeybindingToasts = true;
		}
	}
}