/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.config;

import dev.dannytaylor.perspective.base.Perspective;
import folk.sisby.kaleido.api.ReflectiveConfig;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Config {
	private abstract static class Generic {
		private final String type;
		private final String modId;

		public Generic(String type, String modId) {
			this.type = type;
			this.modId = modId;
		}

		private final Map<String, ConfigWrapper<? extends ReflectiveConfig>> configs = new HashMap<>();

		@SuppressWarnings({"unchecked", "rawtypes"})
		public <T extends ReflectiveConfig> ConfigWrapper<? extends ReflectiveConfig> register(ConfigEntry configEntry) {
			return configs.put(configEntry.id(), new ConfigWrapper((T) ReflectiveConfig.createToml(Paths.get("config"), this.modId, (!this.type.isEmpty() ? this.type + "/" : "") + (configEntry.isAddon() ? "addons/" : "") + configEntry.id(), configEntry.config()), configEntry.config()));
		}

		@SuppressWarnings("unchecked")
		public <T extends ReflectiveConfig> T get(String id, Class<T> type) {
			ConfigWrapper<?> wrapper = get(id);
			if (wrapper == null) throw new IllegalArgumentException("No config with id " + id);
			if (!type.isAssignableFrom(wrapper.type())) throw new IllegalArgumentException("Config is not of type " + type.getName());
			return (T) wrapper.config();
		}

		public ConfigWrapper<?> get(String id) {
			return configs.get(id);
		}
	}

	public abstract static class PerspectiveGeneric extends Generic {
		public PerspectiveGeneric(String type) {
			super(type, Perspective.id);
		}
	}

	public static class Client extends PerspectiveGeneric {
		public static Client instance = new Client();

		public Client() {
			super("client");
		}
	}

	public static class Common extends PerspectiveGeneric {
		public static Common instance = new Common();

		public Common() {
			super("");
		}
	}

	public static class DedicatedServer extends PerspectiveGeneric {
		public static DedicatedServer instance = new DedicatedServer();

		public DedicatedServer() {
			super("dedicated_server");
		}
	}
}