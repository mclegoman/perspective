package dev.dannytaylor.perspective.config.initializer;

import dev.dannytaylor.perspective.config.api.ClientParameterInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client implements ClientModInitializer {
	public static final String MOD_ID = "perspective_config";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		for (ClientParameterInitializer clientConfigEntry : FabricLoader.getInstance().getEntrypoints(ClientParameterInitializer.key, ClientParameterInitializer.class)) {
			clientConfigEntry.register();
		}
	}
}