package dev.dannytaylor.perspective.lens.zooms.overlays.overlays;

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
}
