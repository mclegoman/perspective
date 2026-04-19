/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.config.sections;


import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class NamedRandomEnabledSection extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> randomEnabled;
    public final TrackedValue<Boolean> namedEnabled;

    public NamedRandomEnabledSection(boolean randomEnabledValue, boolean namedEnabledValue) {
        this.randomEnabled = this.value(randomEnabledValue);
        this.namedEnabled = this.value(namedEnabledValue);
    }
}
