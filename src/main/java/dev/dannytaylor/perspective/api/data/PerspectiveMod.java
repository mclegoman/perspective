/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.data;

import dev.dannytaylor.perspective.api.data.log.Log;
import net.minecraft.resources.Identifier;

public class PerspectiveMod {
    private final String id;
    private final String name;
    private final Log logger;

    public PerspectiveMod(String id, String name) {
        this.id = id;
        this.name = name;
        this.logger = new Log(name);
    }

    public String getId(boolean full) {
        return (full ? "perspective_" : "") + this.id;
    }

    public String getName() {
        return this.name;
    }

    public Log getLogger() {
        return this.logger;
    }

    public Identifier idOf(String path) {
        return Identifier.fromNamespaceAndPath(getId(true), path);
    }
}
