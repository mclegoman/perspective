/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.config.sections;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class ContributorSettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> preventFlipOnHoldPerspectiveFront;

    public ContributorSettings(boolean preventFlipOnHoldPerspectiveFrontValue) {
        this.preventFlipOnHoldPerspectiveFront = this.value(preventFlipOnHoldPerspectiveFrontValue);
    }
}
