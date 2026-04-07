/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.values.TrackedValue;

public class NamedRandomEnabledSection extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> randomEnabled;
    public final TrackedValue<Boolean> namedEnabled;

    public NamedRandomEnabledSection(boolean randomEnabledValue, boolean namedEnabledValue) {
        this.randomEnabled = this.value(randomEnabledValue);
        this.namedEnabled = this.value(namedEnabledValue);
    }
}
