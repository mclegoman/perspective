/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hide;

import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.config.ConfigHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hud.MessageOverlay;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.util.Keybindings;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Hide {
	private static final String[] hideCrosshairModes = new String[]{"false", "dynamic", "true"};
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HideArmorDataLoader());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HideNameTagsDataLoader());
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new HidePlayerDataLoader());
	}
	public static void tick() {
		if (Keybindings.TOGGLE_ARMOR.wasPressed()) {
			ConfigHelper.setConfig("hide_armor", !(boolean) ConfigHelper.getConfig("hide_armor"));
			if ((boolean) ConfigHelper.getConfig("hide_show_message"))
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.armor", Translation.getVariableTranslation(Data.version.getID(), (boolean) ConfigHelper.getConfig("hide_armor"), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.TOGGLE_BLOCK_OUTLINE.wasPressed()) {
			ConfigHelper.setConfig("hide_block_outline", !(boolean) ConfigHelper.getConfig("hide_block_outline"));
			if ((boolean) ConfigHelper.getConfig("hide_show_message"))
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.block_outline", Translation.getVariableTranslation(Data.version.getID(), (boolean) ConfigHelper.getConfig("hide_block_outline"), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.CYCLE_CROSSHAIR.wasPressed()) {
			ConfigHelper.setConfig("hide_crosshair", nextCrosshairMode());
			if ((boolean) ConfigHelper.getConfig("hide_show_message"))
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.crosshair", Translation.getHideCrosshairModeTranslation(Data.version.getID(), (String) ConfigHelper.getConfig("hide_crosshair"))).formatted(Formatting.GOLD));
		}
		if (Keybindings.TOGGLE_NAMETAGS.wasPressed()) {
			ConfigHelper.setConfig("hide_nametags", !(boolean) ConfigHelper.getConfig("hide_nametags"));
			if ((boolean) ConfigHelper.getConfig("hide_show_message"))
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.nametags", Translation.getVariableTranslation(Data.version.getID(), (boolean) ConfigHelper.getConfig("hide_nametags"), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
		if (Keybindings.TOGGLE_PLAYERS.wasPressed()) {
			ConfigHelper.setConfig("hide_players", !(boolean) ConfigHelper.getConfig("hide_players"));
			if ((boolean) ConfigHelper.getConfig("hide_show_message"))
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.hide.players", Translation.getVariableTranslation(Data.version.getID(), (boolean) ConfigHelper.getConfig("hide_nametags"), Translation.Type.ENDISABLE)).formatted(Formatting.GOLD));
		}
	}
	public static boolean shouldHidePlayer(PlayerEntity player) {
		if (ClientData.minecraft.player != null) {
			UUID uuid = player.getGameProfile().getId();
			if (!uuid.equals(ClientData.minecraft.player.getGameProfile().getId()))
				return (boolean) ConfigHelper.getConfig("hide_players") || HidePlayerDataLoader.REGISTRY.contains(String.valueOf(player.getGameProfile().getId()));
		}
		return false;
	}
	public static String nextCrosshairMode() {
		List<String> crosshairModes = Arrays.stream(hideCrosshairModes).toList();
		return crosshairModes.contains((String) ConfigHelper.getConfig("hide_crosshair")) ? hideCrosshairModes[(crosshairModes.indexOf((String) ConfigHelper.getConfig("hide_crosshair")) + 1) % hideCrosshairModes.length] : hideCrosshairModes[0];
	}
}