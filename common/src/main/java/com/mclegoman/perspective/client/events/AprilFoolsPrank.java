/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.DateHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public class AprilFoolsPrank {
	private static boolean seenWarning;
	public static void init() {
		try {
			Events.ClientResourceReload.register(Identifier.of(Data.getVersion().getID(), "prank"), new AprilFoolsPrankDataLoader());
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
	public static AprilFoolsPrankDataLoader.PrankData getData(UUID uuid) {
		return AprilFoolsPrankDataLoader.registry.get(AprilFoolsPrankDataLoader.registry.keySet().stream().toList().get(AprilFoolsPrank.getAprilFoolsIndex(uuid.getLeastSignificantBits(), AprilFoolsPrankDataLoader.registry.size())));
	}
	public static PrankTexture getTexture(UUID uuid) {
		AprilFoolsPrankDataLoader.PrankData data = getData(uuid);
		return new PrankTexture(data.textures().get(AprilFoolsPrank.getAprilFoolsIndex(uuid.getLeastSignificantBits(), data.textures().size())), data.isSlim(), data.contributor());
	}
	public static String getContributor(UUID uuid) {
		return AprilFoolsPrankDataLoader.registry.get(AprilFoolsPrankDataLoader.registry.keySet().stream().toList().get(AprilFoolsPrank.getAprilFoolsIndex(uuid.getLeastSignificantBits(), AprilFoolsPrankDataLoader.registry.size()))).contributor();
	}
	public record PrankTexture(Identifier texture, boolean isSlim, String contributor) {
	}
}