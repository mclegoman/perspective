/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public abstract class DefaultZoomAV extends AbstractZoomAV {
    public final CoreRunnables.InputableCallable<Zoom, Float> speedOut;
    public final CoreRunnables.InputableCallable<Zoom, Float> speedIn;
    public final CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled;

    public final PerspectiveMod mod;

    public DefaultZoomAV(CoreRunnables.InputableCallable<Zoom, Float> speedOut, CoreRunnables.InputableCallable<Zoom, Float> speedIn, CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled, PerspectiveMod mod) {
        this.speedOut = speedOut;
        this.speedIn = speedIn;
        this.isSpeedConfigEnabled = isSpeedConfigEnabled;
        this.mod = mod;
    }

    public float getSpeedOut(Zoom zoom) {
        try {
            if (this.speedOut != null) return this.speedOut.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate zoom av speed out: {}", error);
        }
        return super.getSpeedOut(zoom);
    }

    public float getSpeedIn(Zoom zoom) {
        try {
            if (this.speedIn != null) return this.speedIn.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate zoom av speed in: {}", error);
        }
        return super.getSpeedIn(zoom);
    }

    public boolean isSpeedConfigEnabled(Zoom zoom) {
        try {
            if (this.isSpeedConfigEnabled != null) return this.isSpeedConfigEnabled.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate zoom av speed config enabled: {}", error);
        }
        return super.isSpeedConfigEnabled(zoom);
    }
}
