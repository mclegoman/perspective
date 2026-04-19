/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;
import dev.dannytaylor.perspective.ui.shaders.UserInterfaceShaders;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class UserInterfaceClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("ui", "User Interface");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        UserInterfaceEvents.onInitialize(getMod(), () -> {
            UserInterfaceShaders.onInitializeClient(getMod());
            BackgroundRegistry.onInitializeClient(getMod());
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        });
    }

    private void onTickClient(Minecraft minecraft) {
    }
}