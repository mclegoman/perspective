/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.hold_perspective.cameratypes.CameraTypeRegistry;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import dev.dannytaylor.perspective.hold_perspective.keymappings.HoldPerspectiveKeyMappings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class HoldPerspectiveClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("hold_perspective", "Hold Perspective");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        HoldPerspectiveEvents.onInitialize(getMod(), () -> {
            HoldPerspectiveKeyMappings.onInitializeClient(mod);
            CameraTypeRegistry.onInitializeClient(mod);
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        });
    }

    public void onTickClient(Minecraft minecraft) {
        CameraTypeRegistry.onTickClient(minecraft);
    }
}