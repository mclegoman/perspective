/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.config.api.config;

import dev.dannytaylor.perspective.base.Perspective;
import folk.sisby.kaleido.api.ReflectiveConfig;
import net.minecraft.util.Identifier;

/**
 * Return value for `ParameterInitializer`, `ClientParameterInitializer`, `DedicatedServerParameterInitializer` initializers, used to register `ConfigWrapper`s.
 * id: This must be a unique identifier as we use this as the filename and as the registry key.
 * config: This is your config class, See Quilt Config's docs for more information, you do not need to make an instance as we'll make one for you.
 */
public record ConfigEntry(Identifier id, Class<? extends ReflectiveConfig> config) {
    /**
     * Uses `Perspective.id` as the identifier namespace.
     */
    public ConfigEntry(String path, Class<? extends ReflectiveConfig> config) {
        this(Perspective.ofId(path), config);
    }
}
