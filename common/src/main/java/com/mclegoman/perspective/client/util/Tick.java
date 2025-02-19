/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.util;

import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.entity.Entity;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.events.Halloween;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.hud.HUDHelper;
import com.mclegoman.perspective.client.panorama.Panorama;
import com.mclegoman.perspective.client.perspective.Perspective;
import com.mclegoman.perspective.client.shaders.SuperSecretSettings;
import com.mclegoman.perspective.client.toasts.ToastHelper;
import com.mclegoman.perspective.client.zoom.Zoom;

public class Tick {
	public static void init() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			PerspectiveConfig.tick();
			HUDHelper.tick();
			AprilFoolsPrank.tick();
			Halloween.tick();
			Perspective.tick();
			SuperSecretSettings.tick();
			Zoom.tick();
			Entity.tick();
			Panorama.tick();
			Hide.tick();
			ToastHelper.tick();
		});
	}
}