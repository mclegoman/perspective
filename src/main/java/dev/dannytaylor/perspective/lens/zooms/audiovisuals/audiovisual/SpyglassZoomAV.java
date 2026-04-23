/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.mixin.zooms.GuiAccessor;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

import java.util.concurrent.Callable;

public class SpyglassZoomAV extends DefaultZoomAV {
    private final Callable<Boolean> audio;
    private final Callable<Boolean> visual;
    private float scopeScale;

    private SpyglassZoomAV(Callable<Boolean> audio, Callable<Boolean> visual, CoreRunnables.InputableCallable<Zoom, Float> speedOut, CoreRunnables.InputableCallable<Zoom, Float> speedIn, CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled, PerspectiveMod mod) {
        super(speedOut, speedIn, isSpeedConfigEnabled, mod);
        this.audio = audio;
        this.visual = visual;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.visual != null && this.visual.call()) {
                boolean isInstant = zoom.getTransition() != null && zoom.getTransition().isInstant(zoom);
                scopeScale = isInstant ? (zoom.isZooming() ? LensConfig.instance.scopeScale.value() : 0.5F) : Mth.lerp((0.5F * (zoom.isZooming() ? this.getSpeedIn(zoom) : this.getSpeedOut(zoom))) * deltaTracker.getGameTimeDeltaTicks(), scopeScale, zoom.isZooming() ? LensConfig.instance.scopeScale.value() : 0.5F);
                if (zoom.isZooming() && !(ClientData.minecraft.player != null && ClientData.minecraft.player.isScoping())) {
                    if (ClientData.minecraft.options.getCameraType().isFirstPerson()) ((GuiAccessor)ClientData.minecraft.gui).perspective$renderSpyglassOverlay(guiGraphics, scopeScale);
                }
            } else scopeScale = 0.5F;
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to draw spyglass zoom av: {}", error);
        }
    }

    public void onStart(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.audio != null && this.audio.call() && ClientData.minecraft.player != null) ClientData.minecraft.player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to onStart spyglass zoom av: {}", error);
        }
    }

    public void onFinish(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.audio != null && this.audio.call() && ClientData.minecraft.player != null)
                ClientData.minecraft.player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to onFinish spyglass zoom av: {}", error);
        }
    }

    public void onZoomDisabled(Zoom zoom) {
        scopeScale = 0.5F;
    }

    public static class Builder {
        private Callable<Boolean> audio;
        private Callable<Boolean> visual;
        private CoreRunnables.InputableCallable<Zoom, Float> speedOut;
        private CoreRunnables.InputableCallable<Zoom, Float> speedIn;
        private CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled;

        public Builder audio(Callable<Boolean> audio) {
            this.audio = audio;
            return this;
        }

        public Builder visual(Callable<Boolean> visual) {
            this.visual = visual;
            return this;
        }

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

        public SpyglassZoomAV build(PerspectiveMod mod) {
            return new SpyglassZoomAV(this.audio, this.visual, this.speedOut, this.speedIn, this.isSpeedConfigEnabled, mod);
        }
    }
}
