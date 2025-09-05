package dev.dannytaylor.perspective.config.init;

import dev.dannytaylor.perspective.config.api.config.Config;
import dev.dannytaylor.perspective.config.api.initializers.AfterDedicatedServerParameterInitializer;
import dev.dannytaylor.perspective.config.api.initializers.DedicatedServerParameterInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class Server implements DedicatedServerModInitializer {
	@Override
	public void onInitializeServer() {
		FabricLoader.getInstance().invokeEntrypoints(DedicatedServerParameterInitializer.key, DedicatedServerParameterInitializer.class, (initializer) -> Config.DedicatedServer.instance.register(initializer.register()));
		FabricLoader.getInstance().invokeEntrypoints(AfterDedicatedServerParameterInitializer.key, AfterDedicatedServerParameterInitializer.class, AfterDedicatedServerParameterInitializer::init);
	}
}