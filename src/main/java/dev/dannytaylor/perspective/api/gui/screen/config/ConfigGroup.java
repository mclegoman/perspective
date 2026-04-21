package dev.dannytaylor.perspective.api.gui.screen.config;

import com.mclegoman.luminance.client.gui.widget.ListWidget;
import java.util.List;

public interface ConfigGroup {
    List<ListWidget.ListEntry> getWidgets();

    void onSave();

    default float getPriority() {
        return Float.MAX_VALUE;
    }
}
