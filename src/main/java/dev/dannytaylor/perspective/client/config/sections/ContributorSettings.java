/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.values.TrackedValue;

public class ContributorSettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> preventFlipOnHoldPerspectiveFront;

    public ContributorSettings(boolean preventFlipOnHoldPerspectiveFrontValue) {
        this.preventFlipOnHoldPerspectiveFront = this.value(preventFlipOnHoldPerspectiveFrontValue);
    }
}
