/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.config;

import dev.dannytaylor.perspective.api.config.value.HideHud;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;

public class LensConfig extends ReflectiveConfig {
    public static final LensConfig instance = LensConfig.createToml(Paths.get("config"), "perspective", LensClient.getMod().getId(false), LensConfig.class);

    public final TrackedValue<Boolean> enabled = this.value(true);
    @FloatRange(min = 0, max = 100)
    public final TrackedValue<Float> amount = this.value(40.0F);
    @FloatRange(min = 1, max = 10)
    public final TrackedValue<Float> incrementSize = this.value(2.0F);
    public final TrackedValue<ConfigIdentifier> transition = this.value(ConfigIdentifier.of(LensClient.idOf("smooth")));
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedIn = this.value(1.0F);
    @FloatRange(min = 0, max = 2)
    public final TrackedValue<Float> smoothSpeedOut = this.value(1.0F);
    public final TrackedValue<ConfigIdentifier> effects = this.value(ConfigIdentifier.of(LensClient.idOf("scaled")));
    public final TrackedValue<HideHud> hideHud = this.value(HideHud.nothing);
    public final TrackedValue<Boolean> showPercentage = this.value(false);
    public final TrackedValue<ConfigIdentifier> scaleType = this.value(ConfigIdentifier.of(LensClient.idOf("logarithmic")));
    public final TrackedValue<ConfigIdentifier> overlay = this.value(ConfigIdentifier.of(LensClient.idOf("none")));
    public final TrackedValue<Boolean> reset = this.value(false);
    public final TrackedValue<Boolean> cinematic = this.value(false);
    public final TrackedValue<Boolean> checkOnTick = this.value(true);
    public final TrackedValue<Float> scopeScale = this.value(1.125F);

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Config", () -> {});
    }
}
