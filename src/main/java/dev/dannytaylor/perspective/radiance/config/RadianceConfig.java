/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance.config;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.radiance.RadianceClient;
import dev.dannytaylor.perspective.api.config.sections.NamedRandomEnabledSection;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.resources.Identifier;

import java.nio.file.Paths;

public class RadianceConfig extends ReflectiveConfig {
    public static final RadianceConfig instance = RadianceConfig.createToml(Paths.get("config"), "perspective", RadianceClient.getMod().getId(false), RadianceConfig.class);

    public final RadianceConfig.SuperSecretSettings superSecretSettings = new RadianceConfig.SuperSecretSettings(
            Identifier.parse("box_blur"),
            RenderLocations.GAME.identifier(),
            true,
            false,
            false
    );

    public final NamedRandomEnabledSection kaleidoscope = new NamedRandomEnabledSection(
            false,
            true
    );

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Config", () -> {});
    }

    public static class SuperSecretSettings extends ReflectiveConfig.Section {
        public final TrackedValue<ConfigIdentifier> shaderStack;
        public final TrackedValue<ConfigIdentifier> renderLocation;
        public final TrackedValue<Boolean> showNameOnChange;
        public final TrackedValue<Boolean> blurConfigBackground;
        public final TrackedValue<Boolean> enabled;

        public SuperSecretSettings(Identifier shaderStackValue, Identifier renderLocationValue, boolean showNameOnChangeValue, boolean blurConfigBackgroundValue, boolean enabledValue) {
            this.shaderStack = this.value(ConfigIdentifier.of(shaderStackValue));
            this.renderLocation = this.value(ConfigIdentifier.of(renderLocationValue));
            this.showNameOnChange = this.value(showNameOnChangeValue);
            this.blurConfigBackground = this.value(blurConfigBackgroundValue);
            this.enabled = this.value(enabledValue);
        }
    }
}
