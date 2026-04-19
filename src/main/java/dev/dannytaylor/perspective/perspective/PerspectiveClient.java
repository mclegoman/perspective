/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

// v2 will move all other packages into sub-mods, this package will stay.
// This package will contain contributor events, and holiday events (such as april fools).

package dev.dannytaylor.perspective.perspective;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.CameraTypeRegistry;
import dev.dannytaylor.perspective.hold_perspective.config.HoldPerspectiveConfig;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import dev.dannytaylor.perspective.hold_perspective.keymappings.HoldPerspectiveKeyMappings;
import dev.dannytaylor.perspective.perspective.events.PerspectiveEvents;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class PerspectiveClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("perspective", false, "Perspective");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        PerspectiveEvents.onInitialize(getMod(), () -> {
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        });
    }

    private void onTickClient(Minecraft minecraft) {
    }
}