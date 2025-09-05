package dev.dannytaylor.perspective.config.api;

@FunctionalInterface
public interface ParameterInitializer {
    /**
     * Runs on perspective config initialization.
     */
    String key = "parameter_config";
    ConfigEntry register();
}