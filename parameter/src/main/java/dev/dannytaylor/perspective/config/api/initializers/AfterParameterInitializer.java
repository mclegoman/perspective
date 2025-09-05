package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterParameterInitializer {
    /**
     * Runs after perspective config initialization.
     */
    String key = "parameter_after_config";
    void init();
}