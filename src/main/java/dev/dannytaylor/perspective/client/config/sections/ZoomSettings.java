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
import org.quiltmc.config.api.annotations.FloatRange;
import org.quiltmc.config.api.values.TrackedValue;

public class ZoomSettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> enabled;
    @FloatRange(min = 0, max = 100)
    public final TrackedValue<Float> amount;
    @FloatRange(min = 1, max = 10)
    public final TrackedValue<Float> incrementSize;
    public final TrackedValue<ConfigIdentifier> transition;
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedIn;
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedOut;
    public final TrackedValue<ConfigIdentifier> scaledEffects;
    public final TrackedValue<String> hideHud;
    public final TrackedValue<Boolean> showPercentage;
    public final TrackedValue<ConfigIdentifier> scaleType;
    public final TrackedValue<Boolean> reset;
    public final TrackedValue<Boolean> cinematic;

    // TODO: Update options that could be turned into ConfigIdentifier's or Config Values.
    public ZoomSettings(
            boolean enabledValue,
            float amountValue,
            float incrementSizeValue,
            Identifier transitionValue,
            float smoothSpeedInValue,
            float smoothSpeedOutValue,
            Identifier scaledEffectsValue,
            String hideHudValue,
            Boolean showPercentageValue,
            Identifier scaleTypeValue,
            Boolean resetValue,
            Boolean cinematicValue
    ) {
        enabled = this.value(enabledValue);
        amount = this.value(amountValue);
        incrementSize = this.value(incrementSizeValue);
        transition = this.value(ConfigIdentifier.of(transitionValue));
        smoothSpeedIn = this.value(smoothSpeedInValue);
        smoothSpeedOut = this.value(smoothSpeedOutValue);
        scaledEffects = this.value(ConfigIdentifier.of(scaledEffectsValue));
        hideHud = this.value(hideHudValue);
        showPercentage = this.value(showPercentageValue);
        scaleType = this.value(ConfigIdentifier.of(scaleTypeValue));
        reset = this.value(resetValue);
        cinematic = this.value(cinematicValue);
    }
}
