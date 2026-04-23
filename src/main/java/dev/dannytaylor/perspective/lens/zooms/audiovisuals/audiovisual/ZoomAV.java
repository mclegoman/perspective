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

public interface ZoomAV {
    void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    void onStart(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    void onFinish(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom);
    void onZoomDisabled(Zoom zoom);
    float getSpeedOut(Zoom zoom);
    float getSpeedIn(Zoom zoom);
    boolean isSpeedConfigEnabled(Zoom zoom);
}