package dev.dannytaylor.perspective.config.api.config;

import folk.sisby.kaleido.api.ReflectiveConfig;

public record ConfigEntry(String id, boolean isAddon, Class<? extends ReflectiveConfig> config) {
}
