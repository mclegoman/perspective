/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public abstract class AbstractZoomAV implements ZoomAV {
    public void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
    }

    public void onStart(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
    }

    public void onFinish(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
    }

    public void onZoomDisabled(Zoom zoom) {
    }

    public float getSpeedIn(Zoom zoom) {
        return 1.0F;
    }

    public float getSpeedOut(Zoom zoom) {
        return 1.0F;
    }

    public boolean isSpeedConfigEnabled(Zoom zoom) {
        return false;
    }
}
