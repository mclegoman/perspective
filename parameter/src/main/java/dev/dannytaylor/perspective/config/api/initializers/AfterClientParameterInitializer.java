package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterClientParameterInitializer {
    /**
     * Runs after perspective client config initialization.
     */
    String key = "parameter_after_client_config";
    void init();
}