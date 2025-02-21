/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.ui;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.value.ConfigIdentifier;
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
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.getVersion().getID(), "default")).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.getVersion().getID(), "gaussian")).shaderId(Identifier.of(Data.getVersion().getID(), "gaussian")).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.getVersion().getID(), "legacy")).renderWorld(context -> {
				RenderSystem.enableBlend();
				context.fillGradient(0, 0, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), -1072689136, -804253680);
				RenderSystem.disableBlend();
			}).renderMenu(context -> {
				RenderSystem.enableBlend();
				context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.getVersion().getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				RenderSystem.disableBlend();
		}).renderPanorama(false).renderShader(false).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.getVersion().getID(), "classic")).renderWorld(context -> {
				RenderSystem.enableBlend();
				context.fillGradient(0, 0, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), -1072689136, -804253680);
				RenderSystem.disableBlend();
			}).renderMenu(context -> {
				RenderSystem.enableBlend();
				context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0.0F, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.getVersion().getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				RenderSystem.disableBlend();
			}).renderTitleScreen(context -> {
				RenderSystem.enableBlend();
				context.drawTexture(RenderLayer::getGuiTextured, getUiBackgroundTextureFromConfig(), 0, 0, 0.0F, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				context.drawTexture(RenderLayer::getGuiTextured, Identifier.of(Data.getVersion().getID(), "textures/gui/uibackground_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getScaledWidth(), ClientData.minecraft.getWindow().getScaledHeight(), 32, 32);
				RenderSystem.disableBlend();
		}).renderPanorama(false).renderTitleScreenPanorama(false).renderShader(false).build());
		registerUIBackground(new UIBackgroundData.Builder(Identifier.of(Data.getVersion().getID(), "none")).renderShader(false).renderDarkening(false).build());
	}
	public static void registerUIBackground(UIBackgroundData data) {
		if (!ClientData.minecraft.isFinishedLoading()) {
			boolean alreadyRegistered = isValidUIBackground(data.getId());
			if (!alreadyRegistered) uiBackgroundTypes.add(data);
			else Data.getVersion().sendToLog(LogType.WARN, Translation.getString("UI Background with id '{}' could not be registered: UI Background is already registered!", data.getId()));
		} else Data.getVersion().sendToLog(LogType.WARN, Translation.getString("UI Background with id '{}' could not be registered: Config has already been initialized!", data.getId()));
	}
	public static boolean isValidUIBackground(Identifier id) {
		for (UIBackgroundData uiData : uiBackgroundTypes) {
			if (id.equals(uiData.getId())) return true;
		}
		return false;
	}
	public static void cycleUIBackgroundType() {
		cycleUIBackgroundType(true);
	}
	public static void cycleUIBackgroundType(boolean direction) {
		int currentIndex = uiBackgroundTypes.indexOf(getCurrentUIBackground());
		PerspectiveConfig.config.uiBackground.setValue(ConfigIdentifier.of(uiBackgroundTypes.get(direction ? (currentIndex + 1) % uiBackgroundTypes.size() : (currentIndex - 1 + uiBackgroundTypes.size()) % uiBackgroundTypes.size()).getId()), false);
	}
	public static UIBackgroundData getCurrentUIBackground() {
		return getUIBackgroundType(PerspectiveConfig.config.uiBackground.value().getIdentifier());
	}
	public static UIBackgroundData getUIBackgroundType(Identifier type) {
		for (UIBackgroundData data : uiBackgroundTypes) {
			if (data.getId().equals(type)) return data;
		}
		return UIBackgroundData.Builder.getFallback();
	}
	public static boolean isRegisteredUIBackgroundType(Identifier type) {
		for (UIBackgroundData data : uiBackgroundTypes) {
			if (data.getId().equals(type)) return true;
		}
		return false;
	}
	public static Identifier getUiBackgroundTextureFromConfig() {
		Identifier uiBackgroundTexture = PerspectiveConfig.config.uiBackgroundTexture.value().getIdentifier();
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, uiBackgroundTexture.getNamespace());
		String key = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, uiBackgroundTexture.getPath());
		return (namespace != null && key != null) ? Identifier.of(namespace, (!key.startsWith("textures/") ? "textures/" : "") + key + (!key.endsWith(".png") ? ".png" : "")) : Identifier.of("minecraft", "textures/block/dirt.png");
	}
	public static Identifier getUIBackgroundId() {
		return Identifier.of(Data.getVersion().getID(), "ui_background");
	}
	public interface Runnable {
		void run(DrawContext context);
	}
	private static class ScrollingDirt {
		private static Identifier getID() {
			return Identifier.of(Data.getVersion().getID(), "scrolling_dirt");
		}
		private static boolean isScrollingDirt() {
			return PerspectiveConfig.config.uiBackground.value().getIdentifier().equals(getID());
		}
		private static void render(Shader.RenderType renderType) {
			Events.ShaderRender.register(getUIBackgroundId(), new ArrayList<>());
			Events.ShaderRender.modify(getUIBackgroundId(), List.of(new Shader.Data(getID(), new Shader(Shaders.get(getUIBackgroundId(), getID()), () -> renderType, ScrollingDirt::isScrollingDirt))));
		}
	}
}