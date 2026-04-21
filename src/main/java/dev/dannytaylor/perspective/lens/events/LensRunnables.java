/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class LensRunnables extends CoreRunnables {
    public interface Zoomable {
        boolean call();
    }

    public interface ZoomOverlay {
        void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    }
}
