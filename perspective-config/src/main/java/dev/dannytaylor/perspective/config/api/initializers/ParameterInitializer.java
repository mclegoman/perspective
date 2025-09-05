/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.initializers;

import dev.dannytaylor.perspective.config.api.config.ConfigEntry;

@FunctionalInterface
public interface ParameterInitializer {
    /**
     * Runs on parameter config initialization.
     */
    String key = "parameter_config";
    ConfigEntry register();
}