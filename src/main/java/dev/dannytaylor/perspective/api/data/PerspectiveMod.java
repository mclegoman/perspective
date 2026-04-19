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
    private final boolean hasFullId;
    private final String name;
    private final Log logger;

    public PerspectiveMod(String id, String name) {
        this(id, true, name);
    }

    public PerspectiveMod(String id, boolean hasFullId, String name) {
        this.id = id;
        this.hasFullId = hasFullId;
        this.name = name;
        this.logger = new Log(name);
    }

    public String getPerspectiveId() {
        return "perspective";
    }

    public String getId(boolean full) {
        return (this.hasFullId && full ? getPerspectiveId() + "_" : "") + this.id;
    }

    public String getName() {
        return this.name;
    }

    public Log getLogger() {
        return this.logger;
    }

    public Identifier idOf(String path) {
        return idOf(path, false);
    }

    public Identifier idOf(String path, boolean usePerspectiveId) {
        return Identifier.fromNamespaceAndPath(usePerspectiveId ? getPerspectiveId() : getId(true), path);
    }
}
