package dev.dannytaylor.perspective.config.initializer;

import dev.dannytaylor.perspective.config.api.ParameterInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Common implements ModInitializer {
	public static final String MOD_ID = "perspective_config";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		FabricLoader.getInstance().getEntrypoints(ParameterInitializer.key, ParameterInitializer.class);
	}
}