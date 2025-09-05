/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterClientParameterInitializer {
    /**
     * Runs after parameter client config initialization.
     */
    String key = "parameter_after_client_config";
    void init();
}