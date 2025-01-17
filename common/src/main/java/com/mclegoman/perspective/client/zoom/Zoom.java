/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.zoom;

import com.mclegoman.luminance.client.util.MessageOverlay;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Smoother;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Zoom {
	public static final List<Identifier> zoomTypes = new ArrayList<>();
	public static final String[] zoomTransitions = new String[]{"smooth", "instant"};
	public static final String[] zoomScaleModes = new String[]{"scaled", "vanilla"};
	private static boolean isZooming;
	private static boolean hasUpdated;
	private static float prevMultiplier = 1.0F;
	private static float multiplier = 1.0F;
	public static float fov = 70.0F;
	public static float zoomFOV = 70.0F;
	public static double timeDelta = Double.MIN_VALUE;
	public static Smoother smoothX = new Smoother();
	public static Smoother smoothY = new Smoother();
	public static void addZoomType(Identifier identifier) {
		if (!zoomTypes.contains(identifier)) zoomTypes.add(identifier);
	}
	public static void init() {
		try {
			addZoomType(Logarithmic.getIdentifier());
			addZoomType(Linear.getIdentifier());
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to init zoom: {}", error));
		}
	}
	public static void tick() {
		try {
			if (Keybindings.toggleZoom.wasPressed()) isZooming = !isZooming;
			if (Keybindings.toggleZoomCinematic.wasPressed()) {
				resetCinematicZoom();
				PerspectiveConfig.toggle(PerspectiveConfig.config.zoomCinematic);
			}
			if (!isZooming()) {
				if (PerspectiveConfig.config.zoomReset.value()) {
					if (getRawZoomLevel() != getDefaultZoomLevel()) {
						reset();
						hasUpdated = true;
					}
				}
				if (hasUpdated) {
					PerspectiveConfig.config.save();
					hasUpdated = false;
				}
				resetCinematicZoom();
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to tick zoom: {}", error));
		}
	}
	public static void resetCinematicZoom() {
		smoothX = new Smoother();
		smoothY = new Smoother();
	}
	public static double getMouseSensitivity() {
		return Math.pow(ClientData.minecraft.options.getMouseSensitivity().getValue() * 0.6000000238418579F + 0.20000000298023224F, 3.0F) * 8.0F;
	}
	public static boolean isZooming() {
		return canZoom() && ClientData.minecraft.player != null && (isZooming != Keybindings.holdZoom.isPressed());
	}
	public static boolean canZoom() {
		return ClientData.minecraft.cameraEntity != null && PerspectiveConfig.config.zoomEnabled.value();
	}
	public static boolean isScaled() {
		return PerspectiveConfig.config.zoomScaleMode.value().equals("scaled");
	}
	public static boolean isSmoothCamera() {
		return PerspectiveConfig.config.zoomCinematic.value();
	}
	public static void updateMultiplier() {
		try {
			prevMultiplier = multiplier;
			if (!isZooming()) Multiplier.setMultiplier(1.0F);
			else {
				if (getZoomType().equals(Logarithmic.getIdentifier())) Logarithmic.updateMultiplier();
				if (getZoomType().equals(Linear.getIdentifier())) Linear.updateMultiplier();
			}
			multiplier = Multiplier.getMultiplier();
			updateTransition();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to update zoom multiplier: {}", error));
		}
	}
	public static void updateTransition() {
		try {
			if (PerspectiveConfig.config.zoomTransition.value().equals("smooth")) {
				float speedMultiplier = ((prevMultiplier + multiplier) * 0.5F);
				multiplier = MathHelper.lerp((prevMultiplier < speedMultiplier) ? PerspectiveConfig.config.zoomSmoothSpeedOut.value() : PerspectiveConfig.config.zoomSmoothSpeedIn.value(), prevMultiplier, speedMultiplier);
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to update zoom transition: {}", error));
		}
	}
	public static float getPrevMultiplier() {
		return prevMultiplier;
	}
	public static float getMultiplier() {
		return multiplier;
	}
	public static double getMultiplierFromFOV() {
		return zoomFOV/fov;
	}
	public static Identifier getZoomType() {
		Identifier zoomTypeIdentifier = IdentifierHelper.identifierFromString((PerspectiveConfig.config.zoomType.value()));
		while (!isValidZoomType(zoomTypeIdentifier)) zoomTypeIdentifier = IdentifierHelper.identifierFromString(cycleZoomType());
		return zoomTypeIdentifier;
	}
	public static float getZoomLevel() {
		return MathHelper.clamp(getRawZoomLevel(), 0.0F, 100.0F);
	}
	public static int getRawZoomLevel() {
		return PerspectiveConfig.config.zoomLevel.value();
	}
	public static int getDefaultZoomLevel() {
		return PerspectiveConfig.config.zoomLevel.getDefaultValue();
	}
	public static void zoom(int amount, int multiplier) {
		try {
			boolean updated = false;
			for (int i = 0; i < multiplier; i++) {
				if (!(getRawZoomLevel() <= 0) || !(getRawZoomLevel() >= 100)) {
					PerspectiveConfig.config.zoomLevel.setValue(getRawZoomLevel() + amount, false);
					updated = true;
					hasUpdated = true;
				}
			}
			if (updated) setOverlay();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to set zoom level: {}", error));
		}
	}
	public static void reset() {
		try {
			if (PerspectiveConfig.config.zoomLevel.value() != getDefaultZoomLevel()) {
				PerspectiveConfig.config.zoomLevel.setValue(getDefaultZoomLevel(), false);
				setOverlay();
				hasUpdated = true;
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to reset zoom level: {}", error));
		}
	}
	private static void setOverlay() {
		try {
			if (PerspectiveConfig.config.zoomShowPercentage.value())
				MessageOverlay.setOverlay(Text.translatable("gui.perspective.message.zoom_level", Text.literal(PerspectiveConfig.config.zoomLevel.value() + "%")).formatted(Formatting.GOLD));
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to set zoom overlay: {}", error));
		}
	}
	public static String cycleZoomType() {
		return cycleZoomType(true);
	}
	public static String cycleZoomType(boolean direction) {
		try {
			int currentIndex = zoomTypes.indexOf(getZoomType());
			String zoomType = IdentifierHelper.stringFromIdentifier(zoomTypes.get(direction ? (currentIndex + 1) % zoomTypes.size() : (currentIndex - 1 + zoomTypes.size()) % zoomTypes.size()));
			PerspectiveConfig.config.zoomType.setValue(zoomType, false);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to cycle zoom type: {}", error));
		}
		return null;
	}
	public static boolean isValidZoomType(Identifier zoomType) {
		return zoomTypes.contains(zoomType);
	}
	public static String nextTransition() {
		List<String> transitions = Arrays.stream(zoomTransitions).toList();
		return transitions.contains(PerspectiveConfig.config.zoomTransition.value()) ? zoomTransitions[(transitions.indexOf(PerspectiveConfig.config.zoomTransition.value()) + 1) % zoomTransitions.length] : zoomTransitions[0];
	}
	public static String nextScaleMode() {
		List<String> scaleModes = Arrays.stream(zoomScaleModes).toList();
		return scaleModes.contains(PerspectiveConfig.config.zoomScaleMode.value()) ? zoomScaleModes[(scaleModes.indexOf(PerspectiveConfig.config.zoomScaleMode.value()) + 1) % zoomScaleModes.length] : zoomScaleModes[0];
	}
	public static class Logarithmic {
		public static Identifier getIdentifier() {
			return Identifier.of(Data.getVersion().getID(), "logarithmic");
		}
		public static float getLimitFOV(float input) {
			return MathHelper.clamp(input, 0.1F, 179.9F);
		}
		public static void updateMultiplier() {
			Multiplier.setMultiplier((float) (1.0F - (Math.log(Zoom.getZoomLevel() + 1.0F) / Math.log(100.0 + 1.0F))));
		}
	}
	public static class Linear {
		public static Identifier getIdentifier() {
			return Identifier.of(Data.getVersion().getID(), "linear");
		}
		public static float getLimitFOV(float input) {
			return MathHelper.clamp(input, 0.1F, 179.9F);
		}
		public static void updateMultiplier() {
			Multiplier.setMultiplier(1.0F - (Zoom.getZoomLevel() / 100.0F));
		}
	}
	public static class Multiplier {
		protected static float currentMultiplier = 1.0F;
		protected static float getMultiplier() {
			return currentMultiplier;
		}
		protected static void setMultiplier(float multiplier) {
			try {
				currentMultiplier = multiplier;
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to set Zoom Multiplier: {}", error));
			}
		}
	}
}