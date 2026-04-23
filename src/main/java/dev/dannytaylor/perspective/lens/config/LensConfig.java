/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.PerspectiveConfig;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.gui.CoreConfigWidgets;
import dev.dannytaylor.perspective.api.gui.SliderWidget;
import dev.dannytaylor.perspective.api.gui.screen.config.ConfigGroup;
import dev.dannytaylor.perspective.api.util.NumberHelper;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

import java.nio.file.Paths;
import java.util.List;

public class LensConfig extends PerspectiveConfig {
    public static final LensConfig instance = LensConfig.createToml(Paths.get("config"), "perspective", LensClient.getMod().getId(false), LensConfig.class);

    public final TrackedValue<Boolean> enabled = this.value(true);
    @FloatRange(min = 0.0F, max = 100.0F)
    public final TrackedValue<Float> amount = this.value(40.0F);
    @FloatRange(min = 0.01F, max = 10.0F)
    public final TrackedValue<Float> incrementSize = this.value(2.0F);
    public final TrackedValue<ConfigIdentifier> transition = this.value(ConfigIdentifier.of(LensClient.idOf("smooth")));
    @FloatRange(min = 0.0F, max = 2.0F)
    public final TrackedValue<Float> transitionSpeedIn = this.value(1.0F);
    @FloatRange(min = 0.0F, max = 2.0F)
    public final TrackedValue<Float> transitionSpeedOut = this.value(1.25F);
    public final TrackedValue<ConfigIdentifier> effects = this.value(ConfigIdentifier.of(LensClient.idOf("scaled")));
    public final TrackedValue<Boolean> effectsWhenNotZooming = this.value(true);
    public final TrackedValue<Float> effectsThreshold = this.value(0.85F);
    public final TrackedValue<HideUi> hideUi = this.value(HideUi.hands);
    public final TrackedValue<Boolean> showPercentage = this.value(false);
    public final TrackedValue<ConfigIdentifier> scaleType = this.value(ConfigIdentifier.of(LensClient.idOf("logarithmic")));
    public final TrackedValue<ConfigIdentifier> audioVisual = this.value(ConfigIdentifier.of(LensClient.idOf("none")));
    public final TrackedValue<Boolean> reset = this.value(false);
    public final TrackedValue<Boolean> cinematic = this.value(false);
    public final TrackedValue<Boolean> checkOnTick = this.value(true);
    public final TrackedValue<Float> scopeScale = this.value(1.125F);
    public final TrackedValue<Boolean> requireSpyglass = this.value(false);

    // Used to enable and disable depending on Transition type.
    private static SliderWidget speedSliderIn = null;
    private static SliderWidget speedSliderOut = null;

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Config", () -> {
            HoldPerspectiveEvents.ConfigGroups.register(mod.idOf("config"), new ConfigGroup() {
                @Override
                public List<ListWidget.ListEntry> getWidgets() {
                    List<ListWidget.ListEntry> entries = List.of(
                            new ListWidget.ListEntry(
                                    new SliderWidget(0, 0, 150, 20, instance.amount.value() / 100.0F, (value) -> instance.amount.setValue(NumberHelper.formatFloat(Mth.ceil(value.floatValue() * 100.0F)), false), () -> Components.configTranslatable(mod.idOf("amount"), NumberHelper.floatToString(instance.amount.value()) + "%")),
                                    new SliderWidget(0, 0, 150, 20, (instance.incrementSize.value() - 0.1F) / (10.0F - 0.1F), (value) -> instance.incrementSize.setValue(NumberHelper.formatFloat((float)(0.1F + value * (10.0F - 0.1F))), false), () -> Components.configTranslatable(mod.idOf("increment_size"), instance.incrementSize.value())),
                                    CoreConfigWidgets.toggleButton(mod, "show_percentage", instance.showPercentage).build()
                            ),
                            new ListWidget.ListEntry(
                                    CoreConfigWidgets.priorityEventButton(mod, "scale", Components::guiTranslatable, instance.scaleType, LensEvents.ZoomScales, () -> !Minecraft.getInstance().hasShiftDown()).build(),
                                    CoreConfigWidgets.priorityEventButton(mod, "transition", Components::guiTranslatable, instance.transition, LensEvents.ZoomTransitions, () -> !Minecraft.getInstance().hasShiftDown(), LensConfig::setSpeedSlidersActive).build(),
                                    CoreConfigWidgets.priorityEventButton(mod, "effect", Components::guiTranslatable, instance.effects, LensEvents.ZoomEffects, () -> !Minecraft.getInstance().hasShiftDown()).build()
                            ),
                            new ListWidget.ListEntry(
                                    speedSliderIn = new SliderWidget(0, 0, 150, 20, (instance.transitionSpeedIn.value() - 0.01F) / (2.0F - 0.01F), (value) -> instance.transitionSpeedIn.setValue(NumberHelper.formatFloat(0.01F + value.floatValue() * (2.0F - 0.01F)), false), () -> Components.configTranslatable(mod.idOf("transition.speed_in"), NumberHelper.floatToString(instance.transitionSpeedIn.value()))),
                                    speedSliderOut = new SliderWidget(0, 0, 150, 20, (instance.transitionSpeedOut.value() - 0.01F) / (2.0F - 0.01F), (value) -> instance.transitionSpeedOut.setValue(NumberHelper.formatFloat(0.01F + value.floatValue() * (2.0F - 0.01F)), false), () -> Components.configTranslatable(mod.idOf("transition.speed_out"), NumberHelper.floatToString(instance.transitionSpeedOut.value())))
                            ),
                            new ListWidget.ListEntry(
                                    CoreConfigWidgets.priorityEventButton(mod, "av", Components::guiTranslatable, instance.audioVisual, LensEvents.ZoomAVs, () -> !Minecraft.getInstance().hasShiftDown(), LensConfig::setSpeedSlidersActive).build(),
                                    CoreConfigWidgets.toggleButton(mod, "cinematic", instance.cinematic).build(),
                                    CoreConfigWidgets.hideUiButton(mod, "hide_ui", instance.hideUi).build()
                            ),
                            new ListWidget.ListEntry(
                                    CoreConfigWidgets.toggleButton(mod, "reset", instance.reset).build(),
                                    CoreConfigWidgets.toggleButton(mod, "require_spyglass", instance.requireSpyglass).build(),
                                    CoreConfigWidgets.toggleButton(mod, "enabled", instance.enabled).build()
                            )
                    );
                    setSpeedSlidersActive();
                    return entries;
                }

                @Override
                public void save() {
                    LensConfig.instance.save();
                }

                @Override
                public void reset() {
                    instance.reset(false);
                }
            }, 0.0F);
        });
    }

    private static void setSpeedSlidersActive() {
        boolean isSpeedConfigEnabled = ZoomRegistry.isSpeedConfigEnabled();
        if (speedSliderIn != null) speedSliderIn.active = isSpeedConfigEnabled;
        if (speedSliderOut != null) speedSliderOut.active = isSpeedConfigEnabled;
    }
}
