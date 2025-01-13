/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.shaders.Shaders;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.hide.HideHudTypes;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.config.PerspectiveConfig;

public class HUDHelper {
	public static void tick() {
		if (Keybindings.cycleDebug != null) {
			if (Keybindings.cycleDebug.wasPressed()) {
				DebugOverlay.shaderColor = Shaders.getRandomColor();
				DebugOverlay.debugType = ClientData.minecraft.options.sneakKey.isPressed() ? DebugOverlay.debugType.prev() : DebugOverlay.debugType.next();
			}
		}
		if (Keybindings.toggleVerOverlay.wasPressed()) PerspectiveConfig.toggle(PerspectiveConfig.config.versionOverlay);
		if (Keybindings.togglePosOverlay.wasPressed()) PerspectiveConfig.toggle(PerspectiveConfig.config.positionOverlay);
		if (Keybindings.toggleDayOverlay.wasPressed()) PerspectiveConfig.toggle(PerspectiveConfig.config.dayOverlay);
		if (Keybindings.toggleBiomeOverlay.wasPressed()) PerspectiveConfig.toggle(PerspectiveConfig.config.biomeOverlay);
		if (Keybindings.toggleCPSOverlay.wasPressed()) PerspectiveConfig.toggle(PerspectiveConfig.config.cpsOverlay);
	}
	public static boolean shouldHideHUD() {
		return Hide.shouldHideHud(HideHudTypes.zoom) || Hide.shouldHideHud(HideHudTypes.holdPerspectiveBack) || Hide.shouldHideHud(HideHudTypes.holdPerspectiveFront);
	}
	public static boolean shouldHideHand() {
		return Hide.shouldHideHand(HideHudTypes.zoom);
	}
	public static int addY(int y) {
		return y + 12;
	}
}