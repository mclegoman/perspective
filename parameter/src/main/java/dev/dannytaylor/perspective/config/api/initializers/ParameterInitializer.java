package dev.dannytaylor.perspective.config.api.initializers;

import dev.dannytaylor.perspective.config.api.config.ConfigEntry;

@FunctionalInterface
public interface ParameterInitializer {
    /**
     * Runs on perspective config initialization.
     */
    String key = "parameter_config";
    ConfigEntry register();
}