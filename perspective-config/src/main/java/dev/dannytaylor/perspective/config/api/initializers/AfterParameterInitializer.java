/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.initializers;

@FunctionalInterface
public interface AfterParameterInitializer {
    /**
     * Runs after perspective config initialization.
     */
    String key = "parameter_after_config";
    void init();
}