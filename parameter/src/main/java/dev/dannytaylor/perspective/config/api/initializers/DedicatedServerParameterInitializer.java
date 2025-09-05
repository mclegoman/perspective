package dev.dannytaylor.perspective.config.api.initializers;

import dev.dannytaylor.perspective.config.api.config.ConfigEntry;

@FunctionalInterface
public interface DedicatedServerParameterInitializer {
    /**
     * Runs on perspective dedicated server config initialization.
     */
    String key = "parameter_dedicated_server_config";
    ConfigEntry register();
}