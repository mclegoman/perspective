/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.luminance.common.util.DateHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.config.PerspectiveConfig;

import java.time.LocalDate;
import java.time.Month;

public class Halloween {
	private static boolean seenWarning;
	public static void tick() {
		boolean shouldSave = false;
		if (!seenWarning && ClientData.minecraft.world != null) {
			if (isHalloween()) {
//				if (!(boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.warning, "halloween")) {
//					ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.halloween.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.halloween.description", new Object[]{KeyBindingHelper.getBoundKeyOf(Keybindings.openConfig).getLocalizedText()})));
//					ConfigHelper.setConfig(ConfigHelper.ConfigType.warning, "halloween", true);
//					shouldSave = true;
					seenWarning = true;
				//}
			} else {
//				if ((boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.warning, "halloween")) {
//					ConfigHelper.setConfig(ConfigHelper.ConfigType.warning, "halloween", false);
//					shouldSave = true;
//				}
			}
		}
		//if (shouldSave) ConfigHelper.saveConfig();
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