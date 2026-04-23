/*
    Classic Rendering
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.classic_rendering.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.PerspectiveConfig;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.gui.CoreConfigWidgets;
import dev.dannytaylor.perspective.api.gui.SliderWidget;
import dev.dannytaylor.perspective.api.gui.screen.config.ConfigGroup;
import dev.dannytaylor.perspective.api.util.NumberHelper;
import dev.dannytaylor.perspective.classic_rendering.ClassicRenderingClient;
import dev.dannytaylor.perspective.classic_rendering.events.ClassicRenderingEvents;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.util.Mth;

import java.nio.file.Paths;
import java.util.List;

public class ClassicRenderingConfig extends PerspectiveConfig {
    public static final ClassicRenderingConfig instance = ClassicRenderingConfig.createToml(Paths.get("config"), "perspective", ClassicRenderingClient.getMod().getId(false), ClassicRenderingConfig.class);

    public final TrackedValue<Boolean> versionOverlay = this.value(false);
    public final TrackedValue<Float> starBrightnessMultiplier = this.value(1.0F);

    public static void onInitializeClient(PerspectiveMod mod) {
        ClassicRenderingEvents.onInitialize(mod, "Config", () -> {
            ClassicRenderingEvents.ConfigGroups.register(mod.idOf("config"), new ConfigGroup() {
                @Override
                public List<ListWidget.ListEntry> getWidgets() {
                    return List.of(
                            new ListWidget.ListEntry(
                                    CoreConfigWidgets.toggleButton(mod, "version_overlay", instance.versionOverlay).build(),
                                    new SliderWidget(0, 0, 150, 20, instance.starBrightnessMultiplier.value() / 2.0F, (value) -> instance.starBrightnessMultiplier.setValue(NumberHelper.formatFloat((float) (value * 2.0F)), false), () -> Components.configTranslatable(mod.idOf("star_brightness"), Mth.ceil(instance.starBrightnessMultiplier.value() * 100.0F) + "%"))
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

                // TODO:
                //@Override
                //public int getPriority() {
                //    return 0;
                //}
            });
        });
    }
}
