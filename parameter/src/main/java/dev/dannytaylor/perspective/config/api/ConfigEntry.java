package dev.dannytaylor.perspective.config.api;

import folk.sisby.kaleido.api.WrappedConfig;

public record ConfigEntry(ConfigType type, boolean isAddon, WrappedConfig.Section config) {
}
