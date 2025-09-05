/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterDedicatedServerParameterInitializer {
    /**
     * Runs after parameter dedicated server config initialization.
     */
    String key = "parameter_after_dedicated_server_config";
    void init();
}