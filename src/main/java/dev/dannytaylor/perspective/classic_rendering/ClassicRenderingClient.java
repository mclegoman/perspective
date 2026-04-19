/*
    Classic Rendering
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.classic_rendering;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.classic_rendering.config.ClassicRenderingConfig;
import dev.dannytaylor.perspective.classic_rendering.events.ClassicRenderingEvents;
import dev.dannytaylor.perspective.classic_rendering.render.HudRendering;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class ClassicRenderingClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("classic_rendering", "Classic Rendering");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        ClassicRenderingEvents.onInitialize(getMod(), () -> {
            ClassicRenderingConfig.onInitializeClient(getMod());
            HudRendering.onInitializeClient(getMod());
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        }, true);
    }

    public void onTickClient(Minecraft minecraft) {
    }
}