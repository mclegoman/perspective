/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions.transition;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.util.Mth;

import java.util.concurrent.Callable;

public class SmoothZoomTransition extends AbstractZoomTransition {
    public final Callable<Float> speedOut;
    public final Callable<Float> speedIn;

    public final PerspectiveMod mod;

    private SmoothZoomTransition(Callable<Float> speedOut, Callable<Float> speedIn, PerspectiveMod mod) {
        this.speedOut = speedOut;
        this.speedIn = speedIn;
        this.mod = mod;
    }

    public static Builder builder() {
        return new Builder();
    }

    public float updateFov(float fov, Zoom zoom, float tickDelta) {
        return fov * Mth.lerp(tickDelta, zoom.getPreviousMultiplier(), zoom.getMultiplier());
    }

    public float updateMultiplier(Zoom zoom) {
        float speedMultiplier = ((zoom.getPreviousMultiplier() + zoom.getMultiplier()) * 0.5F);
        return Mth.lerp(zoom.getPreviousMultiplier() < speedMultiplier ? getSpeedOut() : getSpeedIn(), zoom.getPreviousMultiplier(), speedMultiplier);
    }

    public float getSpeedOut() {
        try {
            if (this.speedOut != null) this.speedOut.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate zoom transition speed out: {}", error);
        }
        return super.getSpeedOut();
    }

    public float getSpeedIn() {
        try {
            if (this.speedIn != null) this.speedIn.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate zoom transition speed in: {}", error);
        }
        return super.getSpeedIn();
    }

    public static class Builder {
        private Callable<Float> speedOut;
        private Callable<Float> speedIn;

        public Builder speedOut(Callable<Float> speedOut) {
            this.speedOut = speedOut;
            return this;
        }

        public Builder speedIn(Callable<Float> speedIn) {
            this.speedIn = speedIn;
            return this;
        }

        public SmoothZoomTransition build(PerspectiveMod mod) {
            return new SmoothZoomTransition(this.speedOut, this.speedIn, mod);
        }
    }
}
