/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.PerspectiveConfig;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.gui.CoreConfigWidgets;
import dev.dannytaylor.perspective.api.gui.SliderWidget;
import dev.dannytaylor.perspective.api.gui.screen.config.ConfigGroup;
import dev.dannytaylor.perspective.api.util.NumberHelper;
import dev.dannytaylor.perspective.hold_perspective.HoldPerspectiveClient;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

import java.nio.file.Paths;
import java.util.List;

public class HoldPerspectiveConfig extends PerspectiveConfig {
    public static final HoldPerspectiveConfig instance = HoldPerspectiveConfig.createToml(Paths.get("config"), "perspective", HoldPerspectiveClient.getMod().getId(false), HoldPerspectiveConfig.class);

    @FloatRange(min = 0.01F, max = 10.0F)
    public final TrackedValue<Float> multiplierIncrementSize = this.value(5.0F);
    @FloatRange(min = 0.5F, max = 16.0F)
    public final TrackedValue<Float> backMultiplier = this.value(1.0F);
    @FloatRange(min = 0.5F, max = 16.0F)
    public final TrackedValue<Float> frontMultiplier = this.value(1.0F);
    public final HoldPerspectiveSettings holdPerspective = new HoldPerspectiveSettings(
            1.0F,
            1.0F,
            HideUi.nothing,
            HideUi.handsHud
    );

    public static void onInitializeClient(PerspectiveMod mod) {
        HoldPerspectiveEvents.onInitialize(mod, "Config", () -> {
            HoldPerspectiveEvents.ConfigGroups.register(mod.idOf("config"), new ConfigGroup() {
                @Override
                public List<ListWidget.ListEntry> getWidgets() {
                    return List.of(
                            new ListWidget.ListEntry(
                                    new SliderWidget(0, 0, 150, 20, (instance.holdPerspective.backMultiplier.value() - 0.5F) / (16.0F - 0.5F), (value) -> instance.holdPerspective.backMultiplier.setValue(NumberHelper.formatFloat((float)(0.5F + value * (16.0F - 0.5F))), false), () -> Components.configTranslatable(mod.idOf("hold.back_multiplier"), instance.holdPerspective.backMultiplier.value())),
                                    CoreConfigWidgets.hideUiButton(mod, "hold.back_hide_ui", instance.holdPerspective.backHideHud).build()
                            ),
                            new ListWidget.ListEntry(
                                    new SliderWidget(0, 0, 150, 20, (instance.holdPerspective.frontMultiplier.value() - 0.5F) / (16.0F - 0.5F), (value) -> instance.holdPerspective.frontMultiplier.setValue(NumberHelper.formatFloat((float)(0.5F + value * (16.0F - 0.5F))), false), () -> Components.configTranslatable(mod.idOf("hold.front_multiplier"), instance.holdPerspective.frontMultiplier.value())),
                                    CoreConfigWidgets.hideUiButton(mod, "hold.front_hide_ui", instance.holdPerspective.frontHideHud).build()
                            ),
                            new ListWidget.ListEntry(
                                    new SliderWidget(0, 0, 150, 20, (instance.multiplierIncrementSize.value() - 0.1F) / (10.0F - 0.1F), (value) -> instance.multiplierIncrementSize.setValue(NumberHelper.formatFloat((float)(0.1F + value * (10.0F - 0.1F))), false), () -> Components.configTranslatable(mod.idOf("multiplier_increment_size"), instance.multiplierIncrementSize.value()))
                            )
                    );
                }

                @Override
                public void save() {
                    instance.save();
                }

                @Override
                public void reset() {
                    instance.reset(false);
                }
            }, 10.0F);

            HoldPerspectiveEvents.ConfigGroups.register(mod.idOf("config.swap"), new ConfigGroup() {
                @Override
                public List<ListWidget.ListEntry> getWidgets() {
                    return List.of(
                            new ListWidget.ListEntry(
                                    new SliderWidget(0, 0, 150, 20, (instance.backMultiplier.value() - 0.5F) / (16.0F - 0.5F), (value) -> instance.backMultiplier.setValue(NumberHelper.formatFloat((float)(0.5F + value * (16.0F - 0.5F))), false), () -> Components.configTranslatable(mod.idOf("back_multiplier"), instance.backMultiplier.value())),
                                    new SliderWidget(0, 0, 150, 20, (instance.frontMultiplier.value() - 0.5F) / (16.0F - 0.5F), (value) -> instance.frontMultiplier.setValue(NumberHelper.formatFloat((float)(0.5F + value * (16.0F - 0.5F))), false), () -> Components.configTranslatable(mod.idOf("front_multiplier"), instance.frontMultiplier.value()))
                            )
                    );
                }

                @Override
                public void save() {
                    instance.save();
                }

                @Override
                public void reset() {
                    instance.reset(false);
                }

                @Override
                public boolean resetOnBulkReset() {
                    return false;
                }
            }, 10.1F);
        });
    }

    public static class HoldPerspectiveSettings extends ReflectiveConfig.Section {
        @FloatRange(min = 0.5, max = 16)
        public final TrackedValue<Float> backMultiplier;
        @FloatRange(min = 0.5, max = 16)
        public final TrackedValue<Float> frontMultiplier;
        public final TrackedValue<HideUi> backHideHud;
        public final TrackedValue<HideUi> frontHideHud;

        public HoldPerspectiveSettings(
                float backMultiplierValue,
                float frontMultiplierValue,
                HideUi backHideUiValue,
                HideUi frontHideUiValue
        ) {
            backMultiplier = this.value(backMultiplierValue);
            frontMultiplier = this.value(frontMultiplierValue);
            backHideHud = this.value(backHideUiValue);
            frontHideHud = this.value(frontHideUiValue);
        }
    }
}
