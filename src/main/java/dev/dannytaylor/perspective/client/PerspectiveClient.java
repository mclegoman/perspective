/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client;

import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.registry.ClientRegistry;
import dev.dannytaylor.perspective.common.data.Data;
import dev.dannytaylor.perspective.common.data.Log;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class PerspectiveClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Log.info("Initializing {}...", Data.getVersion().getName());
		try {
			PerspectiveConfig.onInitializeClient();
			ClientRegistry.onInitializeClient();
			ClientTickEvents.START_CLIENT_TICK.register(this::onTickClient);
			Log.info("Finished initializing {}!", Data.getVersion().getName());
		} catch (Exception error) {
			Log.error("Failed to initialize {}: {}", Data.getVersion().getName(), error);
		}
	}

	public void onTickClient(Minecraft minecraft) {
		if (minecraft.isGameLoadFinished()) ClientRegistry.onTickClient(minecraft);
	}
}