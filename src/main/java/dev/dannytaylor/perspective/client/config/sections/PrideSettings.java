/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;

public class PrideSettings extends ReflectiveConfig.Section {
    @SerializedName("force_pride_type")
    public final TrackedValue<String> type;
    @SerializedName("force_pride")
    public final TrackedValue<Boolean> force;

    public PrideSettings(String typeValue, boolean forceValue) {
        this.type = this.value(typeValue);
        this.force = this.value(forceValue);
    }
}
