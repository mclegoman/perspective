/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import dev.dannytaylor.perspective.client.config.value.ConfigIdentifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.FloatRange;
import org.quiltmc.config.api.annotations.IntegerRange;
import org.quiltmc.config.api.values.TrackedValue;

public class ZoomSettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> enabled;
    @IntegerRange(min = 0, max = 100)
    public final TrackedValue<Integer> amount;
    @IntegerRange(min = 1, max = 10)
    public final TrackedValue<Integer> incrementSize;
    public final TrackedValue<String> transition;
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedIn;
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedOut;
    public final TrackedValue<String> scaleMode;
    public final TrackedValue<String> hideHud;
    public final TrackedValue<Boolean> showPercentage;
    public final TrackedValue<ConfigIdentifier> type;
    public final TrackedValue<Boolean> reset;
    public final TrackedValue<Boolean> cinematic;

    // TODO: Update options that could be turned into ConfigIdentifier's or Config Values.
    public ZoomSettings(
            boolean enabledValue,
            int amountValue,
            int incrementSizeValue,
            String transitionValue,
            float smoothSpeedInValue,
            float smoothSpeedOutValue,
            String scaleModeValue,
            String hideHudValue,
            Boolean showPercentageValue,
            ConfigIdentifier typeValue,
            Boolean resetValue,
            Boolean cinematicValue
    ) {
        enabled = this.value(enabledValue);
        amount = this.value(amountValue);
        incrementSize = this.value(incrementSizeValue);
        transition = this.value(transitionValue);
        smoothSpeedIn = this.value(smoothSpeedInValue);
        smoothSpeedOut = this.value(smoothSpeedOutValue);
        scaleMode = this.value(scaleModeValue);
        hideHud = this.value(hideHudValue);
        showPercentage = this.value(showPercentageValue);
        type = this.value(typeValue);
        reset = this.value(resetValue);
        cinematic = this.value(cinematicValue);
    }
}
