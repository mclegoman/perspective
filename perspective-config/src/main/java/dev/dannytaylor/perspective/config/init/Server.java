/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.init;

import dev.dannytaylor.perspective.config.api.config.Config;
import dev.dannytaylor.perspective.config.api.initializers.AfterDedicatedServerParameterInitializer;
import dev.dannytaylor.perspective.config.api.initializers.DedicatedServerParameterInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.SERVER)
public class Server implements DedicatedServerModInitializer {
	/**
	 * Runs `DedicatedServerParameterInitializer`, then runs parameter `AfterDedicatedServerParameterInitializer`s.
	 */
	@Override
	public void onInitializeServer() {
		FabricLoader.getInstance().invokeEntrypoints(DedicatedServerParameterInitializer.key, DedicatedServerParameterInitializer.class, (initializer) -> Config.DedicatedServer.instance.register(initializer.register()));
		FabricLoader.getInstance().invokeEntrypoints(AfterDedicatedServerParameterInitializer.key, AfterDedicatedServerParameterInitializer.class, AfterDedicatedServerParameterInitializer::init);
	}
}