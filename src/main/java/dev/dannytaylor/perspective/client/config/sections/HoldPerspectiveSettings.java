/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.FloatRange;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.values.TrackedValue;

public class HoldPerspectiveSettings extends ReflectiveConfig.Section {
    @IntegerRange(min = 1, max = 10)
    public final TrackedValue<Integer> multiplierIncrementSize;
    @FloatRange(min = 0.5, max = 16)
    public final TrackedValue<Float> backMultiplier;
    @FloatRange(min = 0.5, max = 16)
    public final TrackedValue<Float> frontMultiplier;
    public final TrackedValue<Boolean> backHideHud;
    public final TrackedValue<Boolean> frontHideHud;

    public HoldPerspectiveSettings(
            int multiplierIncrementSizeValue,
            float backMultiplierValue,
            float frontMultiplierValue,
            boolean backHideHudValue,
            boolean frontHideHudValue
    ) {
        multiplierIncrementSize = this.value(multiplierIncrementSizeValue);
        backMultiplier = this.value(backMultiplierValue);
        frontMultiplier = this.value(frontMultiplierValue);
        backHideHud = this.value(backHideHudValue);
        frontHideHud = this.value(frontHideHudValue);
    }
}
