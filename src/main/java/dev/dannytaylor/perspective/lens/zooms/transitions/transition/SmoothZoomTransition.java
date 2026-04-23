/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.transitions.transition;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.util.Mth;

public class SmoothZoomTransition extends AbstractZoomTransition {
    public final CoreRunnables.InputableCallable<Zoom, Float> speedOut;
    public final CoreRunnables.InputableCallable<Zoom, Float> speedIn;
    public final CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled;

    public final PerspectiveMod mod;

    private SmoothZoomTransition(CoreRunnables.InputableCallable<Zoom, Float> speedOut, CoreRunnables.InputableCallable<Zoom, Float> speedIn, CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled, PerspectiveMod mod) {
        this.speedOut = speedOut;
        this.speedIn = speedIn;
        this.isSpeedConfigEnabled = isSpeedConfigEnabled;
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
        return Mth.lerp(zoom.getPreviousMultiplier() < speedMultiplier ? getSpeedOut(zoom) : getSpeedIn(zoom), zoom.getPreviousMultiplier(), speedMultiplier);
    }

    public float getSpeedOut(Zoom zoom) {
        try {
            if (this.speedOut != null) return this.speedOut.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate smooth zoom transition speed out: {}", error);
        }
        return super.getSpeedOut(zoom);
    }

    public float getSpeedIn(Zoom zoom) {
        try {
            if (this.speedIn != null) return this.speedIn.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate smooth zoom transition speed in: {}", error);
        }
        return super.getSpeedIn(zoom);
    }

    public boolean isSpeedConfigEnabled(Zoom zoom) {
        try {
            if (this.isSpeedConfigEnabled != null) return this.isSpeedConfigEnabled.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to calculate smooth zoom transition config enabled: {}", error);
        }
        return super.isSpeedConfigEnabled(zoom);
    }

    public boolean isInstant(Zoom zoom) {
        return false;
    }

    public static class Builder {
        private CoreRunnables.InputableCallable<Zoom, Float> speedOut;
        private CoreRunnables.InputableCallable<Zoom, Float> speedIn;
        private CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled;

        public Builder speedOut(CoreRunnables.InputableCallable<Zoom, Float> speedOut) {
            this.speedOut = speedOut;
            return this;
        }

        public Builder speedIn(CoreRunnables.InputableCallable<Zoom, Float> speedIn) {
            this.speedIn = speedIn;
            return this;
        }

        public Builder isSpeedConfigEnabled(CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled) {
            this.isSpeedConfigEnabled = isSpeedConfigEnabled;
            return this;
        }

        public SmoothZoomTransition build(PerspectiveMod mod) {
            return new SmoothZoomTransition(this.speedOut, this.speedIn, this.isSpeedConfigEnabled, mod);
        }
    }
}
