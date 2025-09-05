package dev.dannytaylor.perspective.config.api;

@FunctionalInterface
public interface ClientParameterInitializer {
    /**
     * Runs on perspective client config initialization.
     */
    String key = "parameter_client_config";
    ConfigEntry register();
}