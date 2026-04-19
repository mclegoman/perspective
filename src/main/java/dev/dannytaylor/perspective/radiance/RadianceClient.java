/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.radiance.config.RadianceConfig;
import dev.dannytaylor.perspective.radiance.keymappings.RadianceKeyMappings;
import dev.dannytaylor.perspective.radiance.shaders.ShaderRenderers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class RadianceClient implements ClientModInitializer {
    private static final PerspectiveMod mod = new PerspectiveMod("radiance", "Radiance");

    public static PerspectiveMod getMod() {
        return mod;
    }

    public static Identifier idOf(String path) {
        return getMod().idOf(path);
    }

    @Override
    public void onInitializeClient() {
        CoreEvents.onInitialize(getMod(), () -> {
            RadianceConfig.onInitializeClient(getMod());
            ShaderRenderers.onInitializeClient(getMod());
            RadianceKeyMappings.onInitializeClient(getMod());
            ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
        });
    }

    public void onTickClient(Minecraft minecraft) {
        ShaderRenderers.onTickClient(minecraft);
    }
}