package dev.dannytaylor.perspective.config.initializer;

import dev.dannytaylor.perspective.config.api.DedicatedServerParameterInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements DedicatedServerModInitializer {
	public static final String MOD_ID = "perspective_config";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeServer() {
		FabricLoader.getInstance().getEntrypoints(DedicatedServerParameterInitializer.key, DedicatedServerParameterInitializer.class);
	}
}