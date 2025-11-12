/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.luminance.common.util.DateHelper;
import com.mclegoman.perspective.client.config.PerspectiveWarnings;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.toasts.PerspectiveToast;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import java.time.LocalDate;
import java.time.Month;

public class Halloween {
	private static boolean seenWarning;
	public static void tick() {
		if (!seenWarning && ClientData.minecraft.world != null) {
			if (isHalloween()) {
				if (!PerspectiveWarnings.config.halloween.value()) {
					PerspectiveToast.show(ClientData.minecraft, PerspectiveToast.Type.WARNING, Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.halloween.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.halloween.description", new Object[]{KeyBindingHelper.getBoundKeyOf(Keybindings.openConfig).getLocalizedText()}));
					PerspectiveWarnings.config.halloween.setValue(true, true);
					seenWarning = true;
				}
			} else {
				if (PerspectiveWarnings.config.halloween.value()) {
					PerspectiveWarnings.config.halloween.setValue(false, true);
				}
			}
		}
	}
	public static boolean isHalloween() {
		if (!PerspectiveConfig.config.allowHalloween.value()) return false;
		else {
			LocalDate date = DateHelper.getDate();
			return ((date.getMonth() == Month.OCTOBER && date.getDayOfMonth() == 31) || (date.getMonth() == Month.NOVEMBER && date.getDayOfMonth() == 1)) || isForceHalloween();
		}
	}
	public static boolean isForceHalloween() {
		return PerspectiveConfig.config.forceHalloween.value();
	}
}