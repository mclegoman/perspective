/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.appearance.Appearance;
import com.mclegoman.perspective.client.entity.Entity;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.contributor.Contributor;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.hud.Overlays;
import com.mclegoman.perspective.client.panorama.Panorama;
import com.mclegoman.perspective.client.shaders.Shaders;
import com.mclegoman.perspective.client.texture.TextureHelper;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.ui.UIBackground;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.logo.PerspectiveLogo;
import com.mclegoman.perspective.client.util.Tick;
import com.mclegoman.perspective.client.update.Update;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.ConfigHelper;

public class PerspectiveClient {
	public static void init() {
		try {
			Data.getVersion().sendToLog(LogType.INFO, Translation.getString("Initializing {}:client", Data.getVersion().getName()));
			TextureHelper.init();
			AprilFoolsPrank.init();
			Appearance.init();
			UIBackground.init();
			Overlays.init();
			Zoom.init();
			Contributor.init();
			Hide.init();
			Keybindings.init();
			Panorama.init();
			PerspectiveLogo.init();
			Entity.init();
			Tick.init();
			ConfigHelper.init();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to run client:init: {}", error));
		}
	}
	public static void afterInitConfig() {
		try {
			Data.getVersion().sendToLog(LogType.INFO, Translation.getString("AfterConfiging {}", Data.getVersion().getName()));
			Update.checkForUpdates(Data.getVersion());
			Shaders.init();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to run afterInitializeConfig: {}", error));
		}
	}
}