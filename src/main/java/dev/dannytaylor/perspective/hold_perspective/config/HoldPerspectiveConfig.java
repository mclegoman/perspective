/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.config;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.hold_perspective.HoldPerspectiveClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class HoldPerspectiveConfig extends ReflectiveConfig {
    public static final HoldPerspectiveConfig instance = HoldPerspectiveConfig.createToml(Paths.get("config"), "perspective", HoldPerspectiveClient.getMod().getId(false), HoldPerspectiveConfig.class);

    @FloatRange(min = 1, max = 10)
    public final TrackedValue<Float> multiplierIncrementSize = this.value(5.0F);
    @FloatRange(min = 0.5, max = 16)
    public final TrackedValue<Float> backMultiplier = this.value(1.0F);
    @FloatRange(min = 0.5, max = 16)
    public final TrackedValue<Float> frontMultiplier = this.value(1.0F);
    public final HoldPerspectiveSettings holdPerspective = new HoldPerspectiveSettings(
            1.0F,
            1.0F,
            false,
            true
    );

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Config", () -> {});
    }

    public static class HoldPerspectiveSettings extends ReflectiveConfig.Section {
        @FloatRange(min = 0.5, max = 16)
        public final TrackedValue<Float> backMultiplier;
        @FloatRange(min = 0.5, max = 16)
        public final TrackedValue<Float> frontMultiplier;
        public final TrackedValue<Boolean> backHideHud;
        public final TrackedValue<Boolean> frontHideHud;

        public HoldPerspectiveSettings(
                float backMultiplierValue,
                float frontMultiplierValue,
                boolean backHideHudValue,
                boolean frontHideHudValue
        ) {
            backMultiplier = this.value(backMultiplierValue);
            frontMultiplier = this.value(frontMultiplierValue);
            backHideHud = this.value(backHideHudValue);
            frontHideHud = this.value(frontHideHudValue);
        }
    }
}
