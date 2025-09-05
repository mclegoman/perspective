package dev.dannytaylor.perspective.config.api.config;

import folk.sisby.kaleido.api.ReflectiveConfig;

public record ConfigWrapper<T extends ReflectiveConfig>(T config, Class<T> type) {}
