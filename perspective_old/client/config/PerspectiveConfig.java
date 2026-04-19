/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.config;

import com.mclegoman.luminance.common.util.LogType;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.api.data.log.Log;
import dev.dannytaylor.perspective.api.config.value.QualityToggle;
import dev.dannytaylor.perspective.api.data.CoreData;
import dev.dannytaylor.perspective_old.client.config.sections.*;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.resources.Identifier;

import java.nio.file.Paths;

public class PerspectiveConfig extends ReflectiveConfig {
	public static final PerspectiveConfig config = PerspectiveConfig.createToml(Paths.get("config"), "perspective", "config", PerspectiveConfig.class);

	// TODO: Adjust values that could be Config Values instead of strings.

	public static class UiBackground extends ReflectiveConfig.Section {
		public final TrackedValue<ConfigIdentifier> type;
		public final TrackedValue<ConfigIdentifier> texture;

		public UiBackground(Identifier typeValue, Identifier textureValue) {
			this.type = this.value(ConfigIdentifier.of(typeValue));
			this.texture = this.value(ConfigIdentifier.of(textureValue));
		}
	}

	public final UiBackground shaders = new UiBackground(
			Identifier.parse("default"), // TODO: config.
			Identifier.parse("minecraft:block/dirt")
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
					Identifier.parse("gregorian"), // TODO: config.
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

	public static void onInitializeClient(Log logger) {
		logger.info("Initializing client config...");
	}

	public static void tick() {
		try {
			//if (Keybindings.openConfig.wasPressed()) ClientData.minecraft.setScreen(new ConfigScreen(ClientData.minecraft.currentScreen, 1));
		} catch (Exception error) {
			CoreData.getVersion().sendToLog(LogType.WARN, "Failed to tick config!");
		}
	}
}