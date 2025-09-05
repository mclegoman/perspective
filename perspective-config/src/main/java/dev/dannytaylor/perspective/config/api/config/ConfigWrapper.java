/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.config;

import folk.sisby.kaleido.api.ReflectiveConfig;

public record ConfigWrapper<T extends ReflectiveConfig>(T config, Class<T> type) {}
