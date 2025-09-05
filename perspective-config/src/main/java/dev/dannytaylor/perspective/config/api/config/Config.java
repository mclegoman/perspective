/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.config;

import dev.dannytaylor.perspective.base.api.GenericRegistry;
import folk.sisby.kaleido.api.ReflectiveConfig;
import net.minecraft.util.Identifier;

import java.nio.file.Paths;

/**
 * Registers configs in registries.
 */
public class Config {
	private abstract static class Generic {
		private final String type;

		/**
		 * Generic Config Registry
		 * type: This is the filename of the directory in `configs/<modId>/` that the configs are stored in.
		 */
		public Generic(String type) {
			this.type = type;
		}

		/**
		 * This is where the magic - of storing configs - happens.
		 */
		private final GenericRegistry<Identifier, ConfigWrapper<? extends ReflectiveConfig>> registry = new GenericRegistry<>();

		/**
		 * Please use `ParameterInitializer`, `ClientParameterInitializer`, and/or `DedicatedServerParameterInitializer` instead.
		 * We use this internally to register the above-mentioned configs.
		 */
		@SuppressWarnings({"unchecked", "rawtypes"})
		public void register(ConfigEntry configEntry) {
			this.registry.register(configEntry.id(), new ConfigWrapper(ReflectiveConfig.createToml(Paths.get("config"), configEntry.id().getNamespace(), (!this.type.isEmpty() ? this.type + "/" : "") + configEntry.id().getPath(), configEntry.config()), configEntry.config()));
		}

		/**
		 * Returns the config with the corresponding id if it exists and is of matching type.
		 */
		@SuppressWarnings("unchecked")
		public <T extends ReflectiveConfig> T get(Identifier id, Class<T> type) {
			ConfigWrapper<?> wrapper = get(id);
			if (wrapper == null) throw new IllegalArgumentException("No config with id " + id);
			if (!type.isAssignableFrom(wrapper.type())) throw new IllegalArgumentException("Config is not of type " + type.getName());
			return (T) wrapper.config();
		}

		/**
		 * Returns the config with the corresponding id if it exists.
		 * We recommend using `get(String id, Class<T> type)` instead.
		 */
		public ConfigWrapper<?> get(Identifier id) {
			return registry.get(id);
		}
	}

	/**
	 * Stores client configs.
	 */
	public static class Client extends Generic {
		public static Client instance = new Client();

		public Client() {
			super("client");
		}
	}

	/**
	 * Stores common configs.
	 */
	public static class Common extends Generic {
		public static Common instance = new Common();

		public Common() {
			super("");
		}
	}

	/**
	 * Stores dedicated server configs.
	 */
	public static class DedicatedServer extends Generic {
		public static DedicatedServer instance = new DedicatedServer();

		public DedicatedServer() {
			super("dedicated_server");
		}
	}
}