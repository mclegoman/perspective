/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.PerspectiveConfig;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.gui.CoreConfigWidgets;
import dev.dannytaylor.perspective.api.gui.screen.config.ConfigGroup;
import dev.dannytaylor.perspective.ui.UserInterfaceClient;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import java.nio.file.Paths;
import java.util.List;

public class UserInterfaceConfig extends PerspectiveConfig {
    public static final UserInterfaceConfig instance = UserInterfaceConfig.createToml(Paths.get("config"), "perspective", UserInterfaceClient.getMod().getId(false), UserInterfaceConfig.class);

    public final TrackedValue<ConfigIdentifier> background = this.value(ConfigIdentifier.of(UserInterfaceClient.idOf("gaussian")));
    public final TrackedValue<ConfigIdentifier> backgroundTexture = this.value(ConfigIdentifier.of(Identifier.withDefaultNamespace("textures/block/dirt.png")));

    public static void onInitializeClient(PerspectiveMod mod) {
        UserInterfaceEvents.onInitialize(mod, "Config", () -> {
            UserInterfaceEvents.ConfigGroups.register(mod.idOf("config"), new ConfigGroup() {
                @Override
                public List<ListWidget.ListEntry> getWidgets() {
                    return List.of(
                            new ListWidget.ListEntry(
                                    CoreConfigWidgets.priorityEventButton(mod, "background", Components::guiTranslatable, instance.background, UserInterfaceEvents.Backgrounds, () -> !Minecraft.getInstance().hasShiftDown()).build()
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
            });
        });
    }
}
