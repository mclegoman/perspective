/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.keymappings.LensKeyMappings;
import dev.dannytaylor.perspective.lens.zooms.ZoomRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class LensClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("lens", "Lens");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        LensEvents.onInitialize(getMod(), () -> {
            LensConfig.onInitializeClient(mod);
            LensKeyMappings.onInitializeClient(mod);
            ZoomRegistry.onInitializeClient(mod);
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        });
    }

    public void onTickClient(Minecraft minecraft) {
    }
}