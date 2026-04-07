/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import dev.dannytaylor.perspective.client.config.value.ConfigIdentifier;
import net.minecraft.resources.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.values.TrackedValue;

public class ShaderSettings extends ReflectiveConfig.Section {
    public final SuperSecretSettings superSecretSettings;
    public final Kaleidoscope kaleidoscope;
    public final UiBackground uiBackground;

    public ShaderSettings(SuperSecretSettings superSecretSettingsValue, Kaleidoscope kaleidoscopeValue, UiBackground uiBackgroundValue) {
        this.superSecretSettings = superSecretSettingsValue;
        this.kaleidoscope = kaleidoscopeValue;
        this.uiBackground = uiBackgroundValue;
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

    public static class Kaleidoscope extends NamedRandomEnabledSection {
        public Kaleidoscope(boolean randomEnabledValue, boolean namedEnabledValue) {
            super(randomEnabledValue, namedEnabledValue);
        }
    }

    public static class UiBackground extends ReflectiveConfig.Section {
        public final TrackedValue<ConfigIdentifier> type;
        public final TrackedValue<ConfigIdentifier> texture;

        public UiBackground(Identifier typeValue, Identifier textureValue) {
            this.type = this.value(ConfigIdentifier.of(typeValue));
            this.texture = this.value(ConfigIdentifier.of(textureValue));
        }
    }
}
