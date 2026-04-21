package dev.dannytaylor.perspective.lens.zooms.overlays.overlays;

import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public interface ZoomAV {
    void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    void onStart(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    void onFinish(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
}