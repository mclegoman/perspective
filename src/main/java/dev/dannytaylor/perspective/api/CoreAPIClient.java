/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class CoreAPIClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("api", "CoreAPI");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        CoreEvents.onInitialize(getMod(), () -> {
            // todo: config
            //PerspectiveConfig.onInitializeClient(getMod());
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        }, true);
    }

    public void onTickClient(Minecraft minecraft) {
    }
}