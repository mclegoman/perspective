/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common.data;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.common.util.ReleaseType;
import com.mclegoman.luminance.common.util.Version;
import com.mclegoman.perspective.client.translation.Translation;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.commons.lang3.StringUtils;

public class Data {
	public static final Version version = Version.create("Perspective", "perspective", 1, 2, 3, ReleaseType.RELEASE, 1, "6CTGnrNg");
	public static boolean isModInstalled(String MOD_ID) {
		return FabricLoader.getInstance().isModLoaded(MOD_ID);
	}
	public static boolean isModInstalledVersionOrHigher(String modId, String REQUIRED_VERSION, boolean SUBSTRING) {
		try {
			if (isModInstalled(modId)) {
				return checkModVersion(getModContainer(modId).getMetadata().getVersion().getFriendlyString(), REQUIRED_VERSION, SUBSTRING);
			}
		} catch (Exception error) {
			version.sendToLog(LogType.ERROR, Translation.getString("Failed to check mod version for " + modId + ": {}", error));
		}
		return false;
	}
	public static boolean checkModVersion(String CURRENT_VERSION, String REQUIRED_VERSION, boolean SUBSTRING) {
		try {
			String modVersion = SUBSTRING ? StringUtils.substringBefore(CURRENT_VERSION, "-") : CURRENT_VERSION;
			net.fabricmc.loader.api.Version MOD_VER = net.fabricmc.loader.api.Version.parse(modVersion);
			net.fabricmc.loader.api.Version REQ_VER = net.fabricmc.loader.api.Version.parse(REQUIRED_VERSION);
			return REQ_VER.compareTo(MOD_VER) <= 0;
		} catch (Exception error) {
			version.sendToLog(LogType.ERROR, Translation.getString("Failed to check mod version: {}", error));
		}
		return false;
	}
	public static ModContainer getModContainer(String modId) {
		return FabricLoader.getInstance().getModContainer(modId).get();
	}
}