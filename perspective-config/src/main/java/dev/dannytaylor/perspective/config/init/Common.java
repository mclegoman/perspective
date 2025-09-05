package dev.dannytaylor.perspective.config.init;

import dev.dannytaylor.perspective.config.api.config.Config;
import dev.dannytaylor.perspective.config.api.initializers.AfterParameterInitializer;
import dev.dannytaylor.perspective.config.api.initializers.ParameterInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;

public class Common implements ModInitializer {
	@Override
	public void onInitialize() {
		FabricLoader.getInstance().invokeEntrypoints(ParameterInitializer.key, ParameterInitializer.class, (initializer) -> Config.Common.instance.register(initializer.register()));
		FabricLoader.getInstance().invokeEntrypoints(AfterParameterInitializer.key, AfterParameterInitializer.class, AfterParameterInitializer::init);
	}
}