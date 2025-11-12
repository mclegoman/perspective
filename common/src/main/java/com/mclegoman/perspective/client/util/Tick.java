/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
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
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import com.mclegoman.perspective.client.toasts.PerspectiveToast;
import com.mclegoman.perspective.client.zoom.Zoom;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Tick {
	public static void init() {
		ClientTickEvents.END_CLIENT_TICK.register((client) -> {
			if (client.isFinishedLoading()) {
				PerspectiveConfig.tick();
				HUDHelper.tick();
				AprilFoolsPrank.tick();
				Halloween.tick();
				Perspective.tick();
				ShaderPacks.tick();
				Zoom.tick();
				Entity.tick();
				Panorama.tick();
				Hide.tick();
				PerspectiveToast.Helper.tick();
			}
		});
	}
}