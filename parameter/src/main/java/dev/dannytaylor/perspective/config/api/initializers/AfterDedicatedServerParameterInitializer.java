package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterDedicatedServerParameterInitializer {
    /**
     * Runs after perspective dedicated server config initialization.
     */
    String key = "parameter_after_dedicated_server_config";
    void init();
}