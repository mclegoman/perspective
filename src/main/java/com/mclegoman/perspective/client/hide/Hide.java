/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hide;

import com.mclegoman.luminance.client.util.MessageOverlay;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.perspective.Perspective;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Hide {
	public static final String[] zoomHideHudModes = new String[]{"true", "hand", "false"};
	public static final String[] hideCrosshairModes = new String[]{"vanilla", "dynamic", "hidden"};
	public static float rainbowTime = 0.0F;
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HideArmorDataLoader());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HideNameTagsDataLoader());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HidePlayerDataLoader());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new DynamicCrosshairItemsDataLoader());
	}
	public static void tick() {
		if (Keybindings.toggleArmour.wasPressed()) {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideArmor, true);
			if (PerspectiveConfig.config.hideShowMessage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.armor", Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hideArmor.value(), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.toggleBlockOutline.wasPressed()) {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideBlockOutline, true);
			if (PerspectiveConfig.config.hideShowMessage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.block_outline", Translation.getVariableTranslation(Data.version.getID(), !PerspectiveConfig.config.hideBlockOutline.value(), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.cycleCrosshair.wasPressed()) {
			PerspectiveConfig.config.crosshairType.setValue(nextCrosshairMode(), true);
			if (PerspectiveConfig.config.hideShowMessage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.crosshair", Translation.getCrosshairTranslation(Data.version.getID(), PerspectiveConfig.config.crosshairType.value())).formatted(Formatting.GOLD));
		}
		if (Keybindings.toggleNametags.wasPressed()) {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideNametags, true);
			if (PerspectiveConfig.config.hideShowMessage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.nametags", Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hideNametags.value(), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.togglePlayers.wasPressed()) {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hidePlayers, true);
			if (PerspectiveConfig.config.hideShowMessage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.players", Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hidePlayers.value(), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		rainbowTime += 1.0F % 20.0F;
	}
	public static boolean shouldHidePlayer(UUID uuid) {
		if (ClientData.minecraft.player != null) {
			if (!uuid.equals(ClientData.minecraft.player.getGameProfile().getId()))
				return PerspectiveConfig.config.hidePlayers.value() || HidePlayerDataLoader.REGISTRY.contains(String.valueOf(uuid));
		}
		return false;
	}
	public static String nextZoomHideHudMode() {
		List<String> modes = Arrays.stream(zoomHideHudModes).toList();
		return modes.contains(PerspectiveConfig.config.zoomHideHud.value()) ? zoomHideHudModes[(modes.indexOf(PerspectiveConfig.config.zoomHideHud.value()) + 1) % zoomHideHudModes.length] : zoomHideHudModes[0];
	}
	public static String nextCrosshairMode() {
		List<String> modes = Arrays.stream(hideCrosshairModes).toList();
		return modes.contains(PerspectiveConfig.config.crosshairType.value()) ? hideCrosshairModes[(modes.indexOf(PerspectiveConfig.config.crosshairType.value()) + 1) % hideCrosshairModes.length] : hideCrosshairModes[0];
	}
	public static boolean shouldHideHand(HideHudTypes type) {
		if (type == HideHudTypes.zoom) return Zoom.isZooming() && PerspectiveConfig.config.zoomHideHud.value().equalsIgnoreCase("hand");
		return false;
	}
	public static boolean shouldHideHud(HideHudTypes type) {
		switch (type) {
			case zoom -> {
				return Zoom.isZooming() && PerspectiveConfig.config.zoomHideHud.value().equalsIgnoreCase("true");
			}
			case holdPerspectiveBack -> {
				return Perspective.isHoldingPerspectiveBack() && PerspectiveConfig.config.holdPerspectiveBackHideHud.value();
			}
			case holdPerspectiveFront -> {
				return Perspective.isHoldingPerspectiveFront() && PerspectiveConfig.config.holdPerspectiveFrontHideHud.value();
			}
			default -> {return false;}
		}
	}
	public static boolean shouldHideArmor(UUID uuid) {
		return PerspectiveConfig.config.hideArmor.value() || HideArmorDataLoader.registry.contains(String.valueOf(uuid));
	}
	public static int getBlockOutlineLevel() {
		return PerspectiveConfig.config.blockOutline.value();
	}
	public static boolean getRainbowBlockOutline() {
		return PerspectiveConfig.config.rainbowBlockOutline.value();
	}
	public static int getRainbowOutline() {
		return Color.getHSBColor(rainbowTime / 20.0F, 1.0F, 1.0F).getRGB();
	}
	public static int getARGB(int color, int alpha) {
		return (Math.clamp(alpha, 0, 255) << 24) | (color & 0x00FFFFFF);
	}
}