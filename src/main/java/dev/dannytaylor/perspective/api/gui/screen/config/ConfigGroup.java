/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.gui.screen.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import java.util.List;

public interface ConfigGroup {
    List<ListWidget.ListEntry> getWidgets();

    void save();

    void reset();

    default boolean resetOnBulkReset() {
        return true;
    }

    default float getPriority() {
        return Float.MAX_VALUE;
    }
}
