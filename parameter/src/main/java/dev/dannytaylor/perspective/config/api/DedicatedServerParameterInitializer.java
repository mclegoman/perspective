package dev.dannytaylor.perspective.config.api;

@FunctionalInterface
public interface DedicatedServerParameterInitializer {
    /**
     * Runs on perspective dedicated server config initialization.
     */
    String key = "parameter_dedicated_server_config";
    ConfigEntry register();
}