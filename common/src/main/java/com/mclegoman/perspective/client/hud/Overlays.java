/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import fabric.com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.util.Mouse;
import com.mclegoman.perspective.client.util.Position;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class Overlays {
	private static final List<String> timeOverlayTypes = new ArrayList<>();
	public static void init() {
		timeOverlayTypes.add("false");
		timeOverlayTypes.add("twelve_hour");
		timeOverlayTypes.add("twenty_four_hour");
		Mouse.ProcessCPS.register(Identifier.of(Data.version.getID(), "cps_overlay"), PerspectiveConfig.config.cpsOverlay::value);
	}
	public static String getCurrentTimeOverlay() {
		return PerspectiveConfig.config.timeOverlay.value();
	}
	public static boolean isValidTimeOverlay(String timeOverlay) {
		return timeOverlayTypes.contains(timeOverlay);
	}
	public static void cycleTimeOverlay(boolean direction) {
		int currentIndex = timeOverlayTypes.indexOf(getCurrentTimeOverlay());
		PerspectiveConfig.config.timeOverlay.setValue(timeOverlayTypes.get(direction ? (currentIndex + 1) % timeOverlayTypes.size() : (currentIndex - 1 + timeOverlayTypes.size()) % timeOverlayTypes.size()), false);
	}
	public static Text getEntityPositionTextTitle() {
		return Translation.getTranslation(Data.version.getID(), "position.title");
	}
	public static Text getEntityPositionTextDescription(Vec3d pos) {
		return Translation.getTranslation(Data.version.getID(), "position.description", new Object[]{
				Position.getX(pos, true),
				Position.getY(pos, true),
				Position.getZ(pos, true),
		});
	}
	public static void renderOverlays(DrawContext context) {
		if (!ClientData.minecraft.getDebugHud().shouldShowDebugHud() && !ClientData.minecraft.options.hudHidden && !HUDHelper.shouldHideHUD()) {
			if (DebugOverlay.debugType.equals(DebugOverlay.Type.none)) {
				// Version Overlay
				if (PerspectiveConfig.config.versionOverlay.value())
					context.drawTextWithShadow(ClientData.minecraft.textRenderer, Translation.getTranslation(Data.version.getID(), "version_overlay", new Object[]{SharedConstants.getGameVersion().getName()}), 2, 2, 0xffffff);
				// Other Overlays
				int y = 40;
				List<Text> overlayTexts = new ArrayList<>();
				if (PerspectiveConfig.config.positionOverlay.value()) {
					if (ClientData.minecraft.player != null) {
						overlayTexts.add(Translation.getTranslation(Data.version.getID(), "position_overlay", new Object[]{
								getEntityPositionTextTitle(),
								getEntityPositionTextDescription(ClientData.minecraft.player.getPos())
						}));
					}
				}
				if (!PerspectiveConfig.config.timeOverlay.value().equals("false")) {
					if (ClientData.minecraft.world != null) {
						long time = ClientData.minecraft.world.getTimeOfDay() % 24000L;
						int rawHour = (int)(time / 1000 + 6) % 24;
						int rawMinute = (int)(time / 16.666666) % 60;
						String hour = PerspectiveConfig.config.timeOverlay.value().equals("twelve_hour") ? String.valueOf(rawHour == 0 || rawHour == 12 ? 12 : rawHour % 12) : String.valueOf(rawHour);
						if (rawHour < 10 && rawHour != 0) hour = "0" + hour;
						Text timePeriod = PerspectiveConfig.config.timeOverlay.value().equals("twelve_hour") ? (rawHour < 12 ? Translation.getTranslation(Data.version.getID(), "time_overlay.am") : Translation.getTranslation(Data.version.getID(), "time_overlay.pm")) : Text.empty();
						overlayTexts.add(Translation.getTranslation(Data.version.getID(), "time_overlay", new Object[]{
								hour, (rawMinute < 10 ? "0" + rawMinute : String.valueOf(rawMinute)), timePeriod
						}));
					}
				}
				if (PerspectiveConfig.config.dayOverlay.value()) {
					if (ClientData.minecraft.world != null) {
						overlayTexts.add(Translation.getTranslation(Data.version.getID(), "day_overlay", new Object[]{
								ClientData.minecraft.world.getTimeOfDay() / 24000L
						}));
					}
				}
				if (PerspectiveConfig.config.biomeOverlay.value()) {
					if (ClientData.minecraft.player != null && ClientData.minecraft.world != null) {
						String biome = ClientData.minecraft.world.getBiome(ClientData.minecraft.player.getBlockPos()).getKeyOrValue().map((biomeKey) -> biomeKey.getValue().toString(), (biome_) -> "[unregistered " + biome_ + "]");
						overlayTexts.add(Translation.getTranslation(Data.version.getID(), "biome_overlay", new Object[]{
								Translation.getText("biome." + IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, biome) + "." + IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, biome), true)
						}));
					}
				}
				if (PerspectiveConfig.config.cpsOverlay.value()) {
					overlayTexts.add(Translation.getTranslation(Data.version.getID(), "cps_overlay", new Object[]{Mouse.getLeftCPS(), Mouse.getMiddleCPS(), Mouse.getRightCPS()}));
				}
				renderOverlays(context, overlayTexts, 0, y, false);
			} else DebugOverlay.renderDebugHUD(context);
		}
	}
	public static void renderOverlay(DrawContext context, int x, int y, Text text) {
		renderOverlay(context, x, y, text, -1873784752, 0xffffff, false);
	}
	public static void renderOverlay(DrawContext context, int x, int y, Text text, int backgroundColor, int textColor, boolean shadow) {
		context.fill(x, y, x + ClientData.minecraft.textRenderer.getWidth(text) + 4, y + 12, backgroundColor);
		context.drawText(ClientData.minecraft.textRenderer, text, x + 2, y + 2, textColor, shadow);
	}
	public static void renderOverlays(DrawContext context, List<Text> overlays, int x, int y, boolean wrap) {
		renderOverlays(context, overlays, x, y, wrap, 0);
	}
	public static void renderOverlays(DrawContext context, List<Text> overlays, int x, int y, boolean wrap, int wrapY) {
		int wrapX = 0;
		for (Text overlay : overlays) {
			if (!overlay.equals(Text.empty())) {
				wrapX = Math.max(wrapX, ClientData.minecraft.textRenderer.getWidth(overlay));
				if (wrap && (y > ClientData.minecraft.getWindow().getScaledHeight() - 2 - 9)) {
					y = wrapY;
					x += (wrapX + 4);
				}
				Overlays.renderOverlay(context, x, y, overlay);
			}
			y = HUDHelper.addY(y);
		}
	}
}