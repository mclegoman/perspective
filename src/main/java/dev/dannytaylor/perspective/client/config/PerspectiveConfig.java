/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.config.LuminanceConfigHelper;
import dev.dannytaylor.perspective.client.config.sections.*;
import dev.dannytaylor.perspective.client.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.client.config.value.QualityToggle;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.common.data.Data;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.values.TrackedValue;

public class PerspectiveConfig extends ReflectiveConfig {
	public static final PerspectiveConfig config = LuminanceConfigHelper.register(LuminanceConfigHelper.SerializerType.PROPERTIES, Data.getVersion().getID(), "config", PerspectiveConfig.class);

	// TODO: Adjust values that could be Config Values instead of strings.
	public final ZoomSettings zoom = new ZoomSettings(
			true,
			40,
			2,
			"smooth",
			1.0F,
			1.0F,
			"scaled",
			"false",
			false,
			ConfigIdentifier.of(Identifiers.LOGARITHMIC),
			false,
			false
	);

	public final CameraTypeSettings cameraType = new CameraTypeSettings(
			5,
			1.0F,
			1.0F,
			new CameraTypeSettings.HoldPerspectiveSettings(
					1.0F,
					1.0F,
					false,
					true
			)
	);

	public final ShaderSettings shaders = new ShaderSettings(
			new ShaderSettings.SuperSecretSettings(
					Identifiers.FALLBACK_SHADER,
					RenderLocations.WORLD.identifier(),
					true,
					false,
					false
			),
			new ShaderSettings.Kaleidoscope(
					false,
					true
			),
			new ShaderSettings.UiBackground(
					Identifiers.DEFAULT,
					Identifier.parse("minecraft:block/dirt")
			)
	);

	public final EventSettings events = new EventSettings(
			new EventSettings.AprilFools(true, false),
			new EventSettings.Halloween(true, false)
	);

	public final TexturedEntitySettings texturedEntity = new TexturedEntitySettings(
			false,
			true
	);

	public final OverlaySettings overlay = new OverlaySettings(
			false,
			false,
			"false",
			false,
			new OverlaySettings.Date(
					Identifiers.GREGORIAN,
					false
			),
			false,
			QualityToggle.off,
			false,
			false,
			false,
			false
	);

	public final PrideSettings pride = new PrideSettings(
			"random",
			false
	);

	public final HideSettings hide = new HideSettings(
			false,
			"vanilla",
			new HideSettings.BlockOutline(
					40,
					false,
					false
			),
			false,
			false,
			false,
			1.0F,
			true
	);

	public final ContributorSettings contributor = new ContributorSettings(
			true
	);

	public final TrackedValue<Boolean> showTutorialToasts = this.value(true);

	public final TrackedValue<String> detectUpdateChannel = this.value("release");

	public final TrackedValue<Boolean> debug = this.value(false);

	//@Comment("Do not edit this! This is used for updating the config.")
	//public final TrackedValue<Float> config_version = this.value(ClientData.configVersion);

	public static void onInitializeClient() {
		Log.info("Initializing client config...");
	}

	public static void tick() {
		try {
			//if (Keybindings.openConfig.wasPressed()) ClientData.minecraft.setScreen(new ConfigScreen(ClientData.minecraft.currentScreen, 1));
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.WARN, "Failed to tick config!");
		}
	}
}