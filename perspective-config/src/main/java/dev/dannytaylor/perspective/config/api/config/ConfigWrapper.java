/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.config;

import folk.sisby.kaleido.api.ReflectiveConfig;

/**
 * Made from registered `ConfigEntry`s and stored in the corresponding registry.
 * This is used internally to store parameter configs, and is made from `ConfigEntry`.
 */
public record ConfigWrapper<T extends ReflectiveConfig>(T config, Class<T> type) {}
