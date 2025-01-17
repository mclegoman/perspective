/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.luminance.common.util.DateHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class AprilFoolsPrank {
	private static boolean seenWarning;
	public static void init() {
		try {
			ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new AprilFoolsPrankDataLoader());
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize april fools prank: {}", error));
		}
	}
	public static void tick() {
		boolean shouldSave = false;
		if (!seenWarning && ClientData.minecraft.world != null) {
			if (isAprilFools()) {
				//if (!(boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.warning, "prank")) {
					//ClientData.minecraft.getToastManager().add(new Toast(Translation.getTranslation(Data.getVersion().getID(), "toasts.title", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.prank.title")}), Translation.getTranslation(Data.getVersion().getID(), "toasts.tutorial.prank.description", new Object[]{KeyBindingHelper.getBoundKeyOf(Keybindings.openConfig).getLocalizedText()})));
					//ConfigHelper.setConfig(ConfigHelper.ConfigType.warning, "prank", true);
					//shouldSave = true;
					seenWarning = true;
				//}
			} else {
				//if ((boolean) ConfigHelper.getConfig(ConfigHelper.ConfigType.warning, "prank")) {
					//ConfigHelper.setConfig(ConfigHelper.ConfigType.warning, "prank", false);
					//shouldSave = true;
				//}
			}
		}
		//if (shouldSave) ConfigHelper.saveConfig();
	}
	public static boolean isAprilFools() {
		if (!PerspectiveConfig.config.allowAprilFools.value()) return false;
		return DateHelper.isAprilFools() || isForceAprilFools();
	}
	public static boolean isForceAprilFools() {
		return PerspectiveConfig.config.forceAprilFools.value();
	}
	public static int getAprilFoolsIndex(long getLeastSignificantBits, int registrySize) {
		// We add the current year to the player's uuid, so they get a different skin each year.
		return Math.floorMod(getLeastSignificantBits + DateHelper.getDate().getYear(), registrySize);
	}
}