/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config;

import fabric.com.mclegoman.luminance.config.LuminanceConfigHelper;
import com.mclegoman.perspective.client.config.value.ConfigIdentifier;
import com.mclegoman.perspective.client.config.value.ShaderRenderType;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.FloatRange;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;

public class PerspectiveDefaultConfig extends ReflectiveConfig {
	public static final PerspectiveDefaultConfig config = LuminanceConfigHelper.register(LuminanceConfigHelper.SerializerType.PROPERTIES, "", Data.version.getID() + "_defaults", PerspectiveDefaultConfig.class);
	@SerializedName("zoom_enabled")
	public final TrackedValue<Boolean> zoomEnabled = this.value(true);
	@SerializedName("zoom_level")
	@IntegerRange(min = 0, max = 100)
	public final TrackedValue<Integer> zoomLevel = this.value(40);
	@SerializedName("zoom_increment_size")
	@IntegerRange(min = 0, max = 10)
	public final TrackedValue<Integer> zoomIncrementSize = this.value(2);
	@SerializedName("zoom_transition")
	public final TrackedValue<String> zoomTransition = this.value("smooth");
	@SerializedName("zoom_smooth_speed_in")
	@FloatRange(min = 0, max = 2)
	public final TrackedValue<Float> zoomSmoothSpeedIn = this.value(1.0F);
	@SerializedName("zoom_smooth_speed_out")
	@FloatRange(min = 0, max = 2)
	public final TrackedValue<Float> zoomSmoothSpeedOut = this.value(1.0F);
	@SerializedName("zoom_scale_mode")
	public final TrackedValue<String> zoomScaleMode = this.value("scaled");
	@SerializedName("zoom_hide_hud")
	public final TrackedValue<String> zoomHideHud = this.value("false");
	@SerializedName("zoom_show_percentage")
	public final TrackedValue<Boolean> zoomShowPercentage = this.value(false);
	@SerializedName("zoom_type")
	public final TrackedValue<String> zoomType = this.value("perspective:logarithmic");
	@SerializedName("zoom_reset")
	public final TrackedValue<Boolean> zoomReset = this.value(false);
	@SerializedName("zoom_cinematic")
	public final TrackedValue<Boolean> zoomCinematic = this.value(false);
	@SerializedName("hold_perspective_back_multiplier")
	@FloatRange(min = 0.5, max = 4)
	public final TrackedValue<Float> holdPerspectiveBackMultiplier = this.value(1.0F);
	@SerializedName("hold_perspective_front_multiplier")
	@FloatRange(min = 0.5, max = 4)
	public final TrackedValue<Float> holdPerspectiveFrontMultiplier = this.value(1.0F);
	@SerializedName("hold_perspective_back_hide_hud")
	public final TrackedValue<Boolean> holdPerspectiveBackHideHud = this.value(false);
	@SerializedName("hold_perspective_front_hide_hud")
	public final TrackedValue<Boolean> holdPerspectiveFrontHideHud = this.value(true);
	@SerializedName("super_secret_settings_shader")
	public final TrackedValue<ConfigIdentifier> superSecretSettingsShader = this.value(ConfigIdentifier.of(Identifier.of("minecraft:blur")));
	@SerializedName("super_secret_settings_mode")
	public final TrackedValue<ShaderRenderType> superSecretSettingsMode = this.value(ShaderRenderType.game);
	@SerializedName("super_secret_settings_enabled")
	public final TrackedValue<Boolean> superSecretSettingsEnabled = this.value(false);
	@SerializedName("super_secret_settings_sound")
	public final TrackedValue<Boolean> superSecretSettingsSound = this.value(true);
	@SerializedName("super_secret_settings_show_name")
	public final TrackedValue<Boolean> superSecretSettingsShowName = this.value(true);
	@SerializedName("super_secret_settings_selection_blur")
	public final TrackedValue<Boolean> superSecretSettingsSelectionBlur = this.value(false);
	@SerializedName("textured_named_entity")
	public final TrackedValue<Boolean> texturedNamedEntity = this.value(true);
	@SerializedName("textured_random_entity")
	public final TrackedValue<Boolean> texturedRandomEntity = this.value(false);
	@SerializedName("allow_april_fools")
	public final TrackedValue<Boolean> allowAprilFools = this.value(true);
	@SerializedName("force_april_fools")
	public final TrackedValue<Boolean> forceAprilFools = this.value(false);
	@SerializedName("allow_halloween")
	public final TrackedValue<Boolean> allowHalloween = this.value(true);
	@SerializedName("force_halloween")
	public final TrackedValue<Boolean> forceHalloween = this.value(false);
	@SerializedName("version_overlay")
	public final TrackedValue<Boolean> versionOverlay = this.value(false);
	@SerializedName("position_overlay")
	public final TrackedValue<Boolean> positionOverlay = this.value(false);
	@SerializedName("time_overlay")
	public final TrackedValue<String> timeOverlay = this.value("false");
	@SerializedName("day_overlay")
	public final TrackedValue<Boolean> dayOverlay = this.value(false);
	@SerializedName("biome_overlay")
	public final TrackedValue<Boolean> biomeOverlay = this.value(false);
	@SerializedName("cps_overlay")
	public final TrackedValue<Boolean> cpsOverlay = this.value(false);
	@SerializedName("force_pride")
	public final TrackedValue<Boolean> forcePride = this.value(false);
	@SerializedName("force_pride_type")
	public final TrackedValue<String> forcePrideType = this.value("random");
	@SerializedName("show_death_coordinates")
	public final TrackedValue<Boolean> showDeathCoordinates = this.value(false);
	@SerializedName("ui_background")
	public final TrackedValue<String> uiBackground = this.value("default");
	@SerializedName("ui_background_texture")
	public final TrackedValue<String> uiBackgroundTexture = this.value("minecraft:block/dirt");
	@SerializedName("crosshair_type")
	public final TrackedValue<String> crosshairType = this.value("vanilla");
	@SerializedName("hide_block_outline")
	public final TrackedValue<Boolean> hideBlockOutline = this.value(false);
	@SerializedName("block_outline")
	public final TrackedValue<Integer> blockOutline = this.value(40);
	@SerializedName("rainbow_block_outline")
	public final TrackedValue<Boolean> rainbowBlockOutline = this.value(false);
	@SerializedName("hide_armor")
	public final TrackedValue<Boolean> hideArmor = this.value(false);
	@SerializedName("hide_nametags")
	public final TrackedValue<Boolean> hideNametags = this.value(false);
	@SerializedName("hide_players")
	public final TrackedValue<Boolean> hidePlayers = this.value(false);
	@SerializedName("hide_show_message")
	public final TrackedValue<Boolean> hideShowMessage = this.value(true);
	@SerializedName("detect_update_channel")
	public final TrackedValue<String> detectUpdateChannel = this.value("release");
	@SerializedName("tutorials")
	public final TrackedValue<Boolean> tutorials = this.value(true);
	@SerializedName("debug")
	public final TrackedValue<Boolean> debug = this.value(false);
	public static void setDefaults(boolean save) {
		PerspectiveConfig perspectiveConfig = PerspectiveConfig.config;
		config.zoomEnabled.setValue(perspectiveConfig.zoomEnabled.value(), false);
		config.zoomLevel.setValue(perspectiveConfig.zoomLevel.value(), false);
		config.zoomIncrementSize.setValue(perspectiveConfig.zoomIncrementSize.value(), false);
		config.zoomTransition.setValue(perspectiveConfig.zoomTransition.value(), false);
		config.zoomSmoothSpeedIn.setValue(perspectiveConfig.zoomSmoothSpeedIn.value(), false);
		config.zoomSmoothSpeedOut.setValue(perspectiveConfig.zoomSmoothSpeedOut.value(), false);
		config.zoomScaleMode.setValue(perspectiveConfig.zoomScaleMode.value(), false);
		config.zoomHideHud.setValue(perspectiveConfig.zoomHideHud.value(), false);
		config.zoomShowPercentage.setValue(perspectiveConfig.zoomShowPercentage.value(), false);
		config.zoomType.setValue(perspectiveConfig.zoomType.value(), false);
		config.zoomReset.setValue(perspectiveConfig.zoomReset.value(), false);
		config.zoomCinematic.setValue(perspectiveConfig.zoomCinematic.value(), false);
		config.holdPerspectiveBackMultiplier.setValue(perspectiveConfig.holdPerspectiveBackMultiplier.value(), false);
		config.holdPerspectiveFrontMultiplier.setValue(perspectiveConfig.holdPerspectiveFrontMultiplier.value(), false);
		config.holdPerspectiveBackHideHud.setValue(perspectiveConfig.holdPerspectiveBackHideHud.value(), false);
		config.holdPerspectiveFrontHideHud.setValue(perspectiveConfig.holdPerspectiveFrontHideHud.value(), false);
		config.superSecretSettingsShader.setValue(perspectiveConfig.superSecretSettingsShader.value(), false);
		config.superSecretSettingsMode.setValue(perspectiveConfig.superSecretSettingsMode.value(), false);
		config.superSecretSettingsEnabled.setValue(perspectiveConfig.superSecretSettingsEnabled.value(), false);
		config.superSecretSettingsSound.setValue(perspectiveConfig.superSecretSettingsSound.value(), false);
		config.superSecretSettingsShowName.setValue(perspectiveConfig.superSecretSettingsShowName.value(), false);
		config.superSecretSettingsSelectionBlur.setValue(perspectiveConfig.superSecretSettingsSelectionBlur.value(), false);
		config.texturedNamedEntity.setValue(perspectiveConfig.texturedNamedEntity.value(), false);
		config.texturedRandomEntity.setValue(perspectiveConfig.texturedRandomEntity.value(), false);
		config.allowAprilFools.setValue(perspectiveConfig.allowAprilFools.value(), false);
		config.forceAprilFools.setValue(perspectiveConfig.forceAprilFools.value(), false);
		config.allowHalloween.setValue(perspectiveConfig.allowHalloween.value(), false);
		config.forceHalloween.setValue(perspectiveConfig.forceHalloween.value(), false);
		config.versionOverlay.setValue(perspectiveConfig.versionOverlay.value(), false);
		config.positionOverlay.setValue(perspectiveConfig.positionOverlay.value(), false);
		config.timeOverlay.setValue(perspectiveConfig.timeOverlay.value(), false);
		config.dayOverlay.setValue(perspectiveConfig.dayOverlay.value(), false);
		config.biomeOverlay.setValue(perspectiveConfig.biomeOverlay.value(), false);
		config.cpsOverlay.setValue(perspectiveConfig.cpsOverlay.value(), false);
		config.forcePride.setValue(perspectiveConfig.forcePride.value(), false);
		config.forcePrideType.setValue(perspectiveConfig.forcePrideType.value(), false);
		config.showDeathCoordinates.setValue(perspectiveConfig.showDeathCoordinates.value(), false);
		config.uiBackground.setValue(perspectiveConfig.uiBackground.value(), false);
		config.uiBackgroundTexture.setValue(perspectiveConfig.uiBackgroundTexture.value(), false);
		config.crosshairType.setValue(perspectiveConfig.crosshairType.value(), false);
		config.hideBlockOutline.setValue(perspectiveConfig.hideBlockOutline.value(), false);
		config.blockOutline.setValue(perspectiveConfig.blockOutline.value(), false);
		config.rainbowBlockOutline.setValue(perspectiveConfig.rainbowBlockOutline.value(), false);
		config.hideArmor.setValue(perspectiveConfig.hideArmor.value(), false);
		config.hideNametags.setValue(perspectiveConfig.hideNametags.value(), false);
		config.hidePlayers.setValue(perspectiveConfig.hidePlayers.value(), false);
		config.hideShowMessage.setValue(perspectiveConfig.hideShowMessage.value(), false);
		config.detectUpdateChannel.setValue(perspectiveConfig.detectUpdateChannel.value(), false);
		config.tutorials.setValue(perspectiveConfig.tutorials.value(), false);
		config.debug.setValue(perspectiveConfig.debug.value(), false);
		if (save) config.save();
	}
}