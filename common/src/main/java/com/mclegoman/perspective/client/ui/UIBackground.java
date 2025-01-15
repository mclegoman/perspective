/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.ui;

import fabric.com.mclegoman.luminance.common.util.IdentifierHelper;
import fabric.com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class UIBackground {
	private static final List<UIBackgroundData> uiBackgroundTypes = new ArrayList<>();
	public static void init() {
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.version.getID(), "default")).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.version.getID(), "gaussian")).shaderId(Identifier.of(Data.version.getID(), "gaussian")).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.version.getID(), "legacy")).renderWorld(new Runnable() {
			public void run(DrawContext context) {
				RenderSystem.enableBlend();
				context.fillGradient(0, 0, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), -1072689136, -804253680);
				RenderSystem.disableBlend();
			}
		}).renderMenu(new Runnable() {
			public void run(DrawContext context) {
				RenderSystem.enableBlend();
				context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.version.getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				RenderSystem.disableBlend();
			}
		}).renderPanorama(false).renderShader(false).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.version.getID(), "classic")).renderWorld(
			new Runnable() {
				public void run(DrawContext context) {
					RenderSystem.enableBlend();
					context.fillGradient(0, 0, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), -1072689136, -804253680);
					RenderSystem.disableBlend();
				}
			}).renderMenu(new Runnable() {
				public void run(DrawContext context) {
					RenderSystem.enableBlend();
					context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
					context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.version.getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
					RenderSystem.disableBlend();
				}
			}).renderTitleScreen(new Runnable() {
				public void run(DrawContext context) {
					RenderSystem.enableBlend();
					context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
					context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.version.getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
					RenderSystem.disableBlend();
				}
			}).renderPanorama(false).renderTitleScreenPanorama(false).renderShader(false).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.version.getID(), "none")).renderShader(false).renderDarkening(false).build());
	}
	public static void registerUIBackground(UIBackgroundData data) {
		if (!ClientData.minecraft.isFinishedLoading()) {
			boolean alreadyRegistered = isValidUIBackground(data.getId());
			if (!alreadyRegistered) uiBackgroundTypes.add(data);
			else Data.version.sendToLog(LogType.WARN, Translation.getString("UI Background with id '{}' could not be registered: UI Background is already registered!", data.getId()));
		} else Data.version.sendToLog(LogType.WARN, Translation.getString("UI Background with id '{}' could not be registered: Config has already been initialized!", data.getId()));
	}
	public static boolean isValidUIBackground(String id) {
		for (UIBackgroundData uiData : uiBackgroundTypes) {
			if (id.equalsIgnoreCase(uiData.getId())) return true;
		}
		return false;
	}
	public static void cycleUIBackgroundType() {
		cycleUIBackgroundType(true);
	}
	public static void cycleUIBackgroundType(boolean direction) {
		int currentIndex = uiBackgroundTypes.indexOf(getCurrentUIBackground());
		PerspectiveConfig.config.uiBackground.setValue(uiBackgroundTypes.get(direction ? (currentIndex + 1) % uiBackgroundTypes.size() : (currentIndex - 1 + uiBackgroundTypes.size()) % uiBackgroundTypes.size()).getId(), false);
	}
	public static UIBackgroundData getCurrentUIBackground() {
		return getUIBackgroundType(PerspectiveConfig.config.uiBackground.value());
	}
	public static UIBackgroundData getUIBackgroundType(String type) {
		for (UIBackgroundData data : uiBackgroundTypes) {
			if (data.getId().equals(type)) return data;
		}
		return UIBackgroundData.Builder.getFallback();
	}
	public static boolean isRegisteredUIBackgroundType(String type) {
		for (UIBackgroundData data : uiBackgroundTypes) {
			if (data.getId().equals(type)) return true;
		}
		return false;
	}
	public static Identifier getUiBackgroundTextureFromConfig() {
		String uiBackgroundTexture = PerspectiveConfig.config.uiBackgroundTexture.value();
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, uiBackgroundTexture);
		String key = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, uiBackgroundTexture);
		return (namespace != null && key != null) ? Identifier.of(namespace, (!key.startsWith("textures/") ? "textures/" : "") + key + (!key.endsWith(".png") ? ".png" : "")) : Identifier.of("minecraft", "textures/block/dirt.png");
	}
	public interface Runnable {
		default void run(DrawContext context) {
		}
	}
}