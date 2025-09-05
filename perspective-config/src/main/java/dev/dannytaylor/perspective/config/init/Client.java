package dev.dannytaylor.perspective.config.init;

import dev.dannytaylor.perspective.config.api.config.Config;
import dev.dannytaylor.perspective.config.api.initializers.AfterClientParameterInitializer;
import dev.dannytaylor.perspective.config.api.initializers.ClientParameterInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FabricLoader.getInstance().invokeEntrypoints(ClientParameterInitializer.key, ClientParameterInitializer.class, (initializer) -> Config.Client.instance.register(initializer.register()));
		FabricLoader.getInstance().invokeEntrypoints(AfterClientParameterInitializer.key, AfterClientParameterInitializer.class, AfterClientParameterInitializer::init);
	}
}