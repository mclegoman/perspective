/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.config;

import dev.dannytaylor.perspective.api.config.PerspectiveConfig;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.ui.UserInterfaceClient;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.resources.Identifier;

import java.nio.file.Paths;

public class UserInterfaceConfig extends PerspectiveConfig {
    public static final UserInterfaceConfig instance = UserInterfaceConfig.createToml(Paths.get("config"), "perspective", UserInterfaceClient.getMod().getId(false), UserInterfaceConfig.class);

    public final TrackedValue<ConfigIdentifier> background = this.value(ConfigIdentifier.of(UserInterfaceClient.idOf("gaussian")));
    public final TrackedValue<ConfigIdentifier> backgroundTexture = this.value(ConfigIdentifier.of(Identifier.withDefaultNamespace("textures/block/dirt.png")));

    public static void onInitializeClient(PerspectiveMod mod) {
        UserInterfaceEvents.onInitialize(mod, "Config", () -> {});
    }
}
