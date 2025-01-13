/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config;

import com.mclegoman.luminance.config.LuminanceConfigHelper;
import com.mclegoman.perspective.client.PerspectiveClient;
import com.mclegoman.perspective.common.data.Data;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.FloatRange;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;

public class PerspectiveConfig extends ReflectiveConfig {
	public static final PerspectiveConfig config = LuminanceConfigHelper.register(LuminanceConfigHelper.SerializerType.PROPERTIES, "", Data.version.getID(), PerspectiveConfig.class);
	@SerializedName("zoom_enabled")
	public final TrackedValue<Boolean> zoomEnabled = this.value(PerspectiveDefaultConfig.config.zoomEnabled.value());
	@SerializedName("zoom_level")
	@IntegerRange(min = 0, max = 100)
	public final TrackedValue<Integer> zoomLevel = this.value(PerspectiveDefaultConfig.config.zoomLevel.value());
	@SerializedName("zoom_increment_size")
	@IntegerRange(min = 0, max = 10)
	public final TrackedValue<Integer> zoomIncrementSize = this.value(PerspectiveDefaultConfig.config.zoomIncrementSize.value());
	@SerializedName("zoom_transition")
	public final TrackedValue<String> zoomTransition = this.value(PerspectiveDefaultConfig.config.zoomTransition.value());
	@SerializedName("zoom_smooth_speed_in")
	@FloatRange(min = 0, max = 2)
	public final TrackedValue<Float> zoomSmoothSpeedIn = this.value(PerspectiveDefaultConfig.config.zoomSmoothSpeedIn.value());
	@SerializedName("zoom_smooth_speed_out")
	@FloatRange(min = 0, max = 2)
	public final TrackedValue<Float> zoomSmoothSpeedOut = this.value(PerspectiveDefaultConfig.config.zoomSmoothSpeedOut.value());
	@SerializedName("zoom_scale_mode")
	public final TrackedValue<String> zoomScaleMode = this.value(PerspectiveDefaultConfig.config.zoomScaleMode.value());
	@SerializedName("zoom_hide_hud")
	public final TrackedValue<String> zoomHideHud = this.value(PerspectiveDefaultConfig.config.zoomHideHud.value());
	@SerializedName("zoom_show_percentage")
	public final TrackedValue<Boolean> zoomShowPercentage = this.value(PerspectiveDefaultConfig.config.zoomShowPercentage.value());
	@SerializedName("zoom_type")
	public final TrackedValue<String> zoomType = this.value(PerspectiveDefaultConfig.config.zoomType.value());
	@SerializedName("zoom_reset")
	public final TrackedValue<Boolean> zoomReset = this.value(PerspectiveDefaultConfig.config.zoomReset.value());
	@SerializedName("zoom_cinematic")
	public final TrackedValue<Boolean> zoomCinematic = this.value(PerspectiveDefaultConfig.config.zoomCinematic.value());
	@SerializedName("hold_perspective_back_multiplier")
	@FloatRange(min = 0.5, max = 4)
	public final TrackedValue<Float> holdPerspectiveBackMultiplier = this.value(PerspectiveDefaultConfig.config.holdPerspectiveBackMultiplier.value());
	@SerializedName("hold_perspective_front_multiplier")
	@FloatRange(min = 0.5, max = 4)
	public final TrackedValue<Float> holdPerspectiveFrontMultiplier = this.value(PerspectiveDefaultConfig.config.holdPerspectiveFrontMultiplier.value());
	@SerializedName("hold_perspective_back_hide_hud")
	public final TrackedValue<Boolean> holdPerspectiveBackHideHud = this.value(PerspectiveDefaultConfig.config.holdPerspectiveBackHideHud.value());
	@SerializedName("hold_perspective_front_hide_hud")
	public final TrackedValue<Boolean> holdPerspectiveFrontHideHud = this.value(PerspectiveDefaultConfig.config.holdPerspectiveFrontHideHud.value());
	@SerializedName("super_secret_settings_shader")
	public final TrackedValue<String> superSecretSettingsShader = this.value(PerspectiveDefaultConfig.config.superSecretSettingsShader.value());
	@SerializedName("super_secret_settings_mode")
	public final TrackedValue<String> superSecretSettingsMode = this.value(PerspectiveDefaultConfig.config.superSecretSettingsMode.value());
	@SerializedName("super_secret_settings_enabled")
	public final TrackedValue<Boolean> superSecretSettingsEnabled = this.value(PerspectiveDefaultConfig.config.superSecretSettingsEnabled.value());
	@SerializedName("super_secret_settings_sound")
	public final TrackedValue<Boolean> superSecretSettingsSound = this.value(PerspectiveDefaultConfig.config.superSecretSettingsSound.value());
	@SerializedName("super_secret_settings_show_name")
	public final TrackedValue<Boolean> superSecretSettingsShowName = this.value(PerspectiveDefaultConfig.config.superSecretSettingsShowName.value());
	@SerializedName("super_secret_settings_selection_blur")
	public final TrackedValue<Boolean> superSecretSettingsSelectionBlur = this.value(PerspectiveDefaultConfig.config.superSecretSettingsSelectionBlur.value());
	@SerializedName("textured_named_entity")
	public final TrackedValue<Boolean> texturedNamedEntity = this.value(PerspectiveDefaultConfig.config.texturedNamedEntity.value());
	@SerializedName("textured_random_entity")
	public final TrackedValue<Boolean> texturedRandomEntity = this.value(PerspectiveDefaultConfig.config.texturedRandomEntity.value());
	@SerializedName("allow_april_fools")
	public final TrackedValue<Boolean> allowAprilFools = this.value(PerspectiveDefaultConfig.config.allowAprilFools.value());
	@SerializedName("force_april_fools")
	public final TrackedValue<Boolean> forceAprilFools = this.value(PerspectiveDefaultConfig.config.forceAprilFools.value());
	@SerializedName("allow_halloween")
	public final TrackedValue<Boolean> allowHalloween = this.value(PerspectiveDefaultConfig.config.allowHalloween.value());
	@SerializedName("force_halloween")
	public final TrackedValue<Boolean> forceHalloween = this.value(PerspectiveDefaultConfig.config.forceHalloween.value());
	@SerializedName("version_overlay")
	public final TrackedValue<Boolean> versionOverlay = this.value(PerspectiveDefaultConfig.config.versionOverlay.value());
	@SerializedName("position_overlay")
	public final TrackedValue<Boolean> positionOverlay = this.value(PerspectiveDefaultConfig.config.positionOverlay.value());
	@SerializedName("time_overlay")
	public final TrackedValue<String> timeOverlay = this.value(PerspectiveDefaultConfig.config.timeOverlay.value());
	@SerializedName("day_overlay")
	public final TrackedValue<Boolean> dayOverlay = this.value(PerspectiveDefaultConfig.config.dayOverlay.value());
	@SerializedName("biome_overlay")
	public final TrackedValue<Boolean> biomeOverlay = this.value(PerspectiveDefaultConfig.config.biomeOverlay.value());
	@SerializedName("cps_overlay")
	public final TrackedValue<Boolean> cpsOverlay = this.value(PerspectiveDefaultConfig.config.cpsOverlay.value());
	@SerializedName("force_pride")
	public final TrackedValue<Boolean> forcePride = this.value(PerspectiveDefaultConfig.config.forcePride.value());
	@SerializedName("force_pride_type")
	public final TrackedValue<String> forcePrideType = this.value(PerspectiveDefaultConfig.config.forcePrideType.value());
	@SerializedName("show_death_coordinates")
	public final TrackedValue<Boolean> showDeathCoordinates = this.value(PerspectiveDefaultConfig.config.showDeathCoordinates.value());
	@SerializedName("ui_background")
	public final TrackedValue<String> uiBackground = this.value(PerspectiveDefaultConfig.config.uiBackground.value());
	@SerializedName("ui_background_texture")
	public final TrackedValue<String> uiBackgroundTexture = this.value(PerspectiveDefaultConfig.config.uiBackgroundTexture.value());
	@SerializedName("crosshair_type")
	public final TrackedValue<String> crosshairType = this.value(PerspectiveDefaultConfig.config.crosshairType.value());
	@SerializedName("hide_block_outline")
	public final TrackedValue<Boolean> hideBlockOutline = this.value(PerspectiveDefaultConfig.config.hideBlockOutline.value());
	@SerializedName("block_outline")
	public final TrackedValue<Integer> blockOutline = this.value(PerspectiveDefaultConfig.config.blockOutline.value());
	@SerializedName("rainbow_block_outline")
	public final TrackedValue<Boolean> rainbowBlockOutline = this.value(PerspectiveDefaultConfig.config.rainbowBlockOutline.value());
	@SerializedName("hide_armor")
	public final TrackedValue<Boolean> hideArmor = this.value(PerspectiveDefaultConfig.config.hideArmor.value());
	@SerializedName("hide_nametags")
	public final TrackedValue<Boolean> hideNametags = this.value(PerspectiveDefaultConfig.config.hideNametags.value());
	@SerializedName("hide_players")
	public final TrackedValue<Boolean> hidePlayers = this.value(PerspectiveDefaultConfig.config.hidePlayers.value());
	@SerializedName("hide_show_message")
	public final TrackedValue<Boolean> hideShowMessage = this.value(PerspectiveDefaultConfig.config.hideShowMessage.value());
	@SerializedName("detect_update_channel")
	public final TrackedValue<String> detectUpdateChannel = this.value(PerspectiveDefaultConfig.config.detectUpdateChannel.value());
	@SerializedName("tutorials")
	public final TrackedValue<Boolean> tutorials = this.value(PerspectiveDefaultConfig.config.tutorials.value());
	@SerializedName("debug")
	public final TrackedValue<Boolean> debug = this.value(PerspectiveDefaultConfig.config.debug.value());
	public static void init() {
		PerspectiveClient.afterInitializeConfig();
	}
	public static void toggle(TrackedValue<Boolean> value) {
		toggle(value, true);
	}
	public static void toggle(TrackedValue<Boolean> value, boolean shouldSave) {
		value.setValue(!value.value(), shouldSave);
	}
	public static void reset() {
		reset(true);
	}
	public static void reset(boolean shouldSave) {
		PerspectiveDefaultConfig defaultConfig = PerspectiveDefaultConfig.config;
		config.zoomEnabled.setValue(defaultConfig.zoomEnabled.value(), false);
		config.zoomLevel.setValue(defaultConfig.zoomLevel.value(), false);
		config.zoomIncrementSize.setValue(defaultConfig.zoomIncrementSize.value(), false);
		config.zoomTransition.setValue(defaultConfig.zoomTransition.value(), false);
		config.zoomSmoothSpeedIn.setValue(defaultConfig.zoomSmoothSpeedIn.value(), false);
		config.zoomSmoothSpeedOut.setValue(defaultConfig.zoomSmoothSpeedOut.value(), false);
		config.zoomScaleMode.setValue(defaultConfig.zoomScaleMode.value(), false);
		config.zoomHideHud.setValue(defaultConfig.zoomHideHud.value(), false);
		config.zoomShowPercentage.setValue(defaultConfig.zoomShowPercentage.value(), false);
		config.zoomType.setValue(defaultConfig.zoomType.value(), false);
		config.zoomReset.setValue(defaultConfig.zoomReset.value(), false);
		config.zoomCinematic.setValue(defaultConfig.zoomCinematic.value(), false);
		config.holdPerspectiveBackMultiplier.setValue(defaultConfig.holdPerspectiveBackMultiplier.value(), false);
		config.holdPerspectiveFrontMultiplier.setValue(defaultConfig.holdPerspectiveFrontMultiplier.value(), false);
		config.holdPerspectiveBackHideHud.setValue(defaultConfig.holdPerspectiveBackHideHud.value(), false);
		config.holdPerspectiveFrontHideHud.setValue(defaultConfig.holdPerspectiveFrontHideHud.value(), false);
		config.superSecretSettingsShader.setValue(defaultConfig.superSecretSettingsShader.value(), false);
		config.superSecretSettingsMode.setValue(defaultConfig.superSecretSettingsMode.value(), false);
		config.superSecretSettingsEnabled.setValue(defaultConfig.superSecretSettingsEnabled.value(), false);
		config.superSecretSettingsSound.setValue(defaultConfig.superSecretSettingsSound.value(), false);
		config.superSecretSettingsShowName.setValue(defaultConfig.superSecretSettingsShowName.value(), false);
		config.superSecretSettingsSelectionBlur.setValue(defaultConfig.superSecretSettingsSelectionBlur.value(), false);
		config.texturedNamedEntity.setValue(defaultConfig.texturedNamedEntity.value(), false);
		config.texturedRandomEntity.setValue(defaultConfig.texturedRandomEntity.value(), false);
		config.allowAprilFools.setValue(defaultConfig.allowAprilFools.value(), false);
		config.forceAprilFools.setValue(defaultConfig.forceAprilFools.value(), false);
		config.allowHalloween.setValue(defaultConfig.allowHalloween.value(), false);
		config.forceHalloween.setValue(defaultConfig.forceHalloween.value(), false);
		config.versionOverlay.setValue(defaultConfig.versionOverlay.value(), false);
		config.positionOverlay.setValue(defaultConfig.positionOverlay.value(), false);
		config.timeOverlay.setValue(defaultConfig.timeOverlay.value(), false);
		config.dayOverlay.setValue(defaultConfig.dayOverlay.value(), false);
		config.biomeOverlay.setValue(defaultConfig.biomeOverlay.value(), false);
		config.cpsOverlay.setValue(defaultConfig.cpsOverlay.value(), false);
		config.forcePride.setValue(defaultConfig.forcePride.value(), false);
		config.forcePrideType.setValue(defaultConfig.forcePrideType.value(), false);
		config.showDeathCoordinates.setValue(defaultConfig.showDeathCoordinates.value(), false);
		config.uiBackground.setValue(defaultConfig.uiBackground.value(), false);
		config.uiBackgroundTexture.setValue(defaultConfig.uiBackgroundTexture.value(), false);
		config.crosshairType.setValue(defaultConfig.crosshairType.value(), false);
		config.hideBlockOutline.setValue(defaultConfig.hideBlockOutline.value(), false);
		config.blockOutline.setValue(defaultConfig.blockOutline.value(), false);
		config.rainbowBlockOutline.setValue(defaultConfig.rainbowBlockOutline.value(), false);
		config.hideArmor.setValue(defaultConfig.hideArmor.value(), false);
		config.hideNametags.setValue(defaultConfig.hideNametags.value(), false);
		config.hidePlayers.setValue(defaultConfig.hidePlayers.value(), false);
		config.hideShowMessage.setValue(defaultConfig.hideShowMessage.value(), false);
		config.detectUpdateChannel.setValue(defaultConfig.detectUpdateChannel.value(), false);
		config.tutorials.setValue(defaultConfig.tutorials.value(), false);
		config.debug.setValue(defaultConfig.debug.value(), false);
		if (shouldSave) config.save();
	}
}