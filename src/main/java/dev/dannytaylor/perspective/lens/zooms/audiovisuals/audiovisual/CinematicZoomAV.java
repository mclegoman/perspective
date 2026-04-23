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
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;

import java.util.concurrent.Callable;

public class CinematicZoomAV extends DefaultZoomAV {
    private final Callable<AspectRatio> aspectRatio;
    private float cinematicScale;

    public CinematicZoomAV(Callable<AspectRatio> aspectRatio, CoreRunnables.InputableCallable<Zoom, Float> speedOut, CoreRunnables.InputableCallable<Zoom, Float> speedIn, CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled, PerspectiveMod mod) {
        super(speedOut, speedIn, isSpeedConfigEnabled, mod);
        this.aspectRatio = aspectRatio;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.aspectRatio != null) {
                AspectRatio aspectRatio = this.aspectRatio.call();
                boolean isInstant = zoom.getTransition() != null && zoom.getTransition().isInstant(zoom);
                this.cinematicScale = isInstant ? (zoom.isZooming() ? 1.0F : 0.0F) : Mth.lerp((0.5F * (zoom.isZooming() ? this.getSpeedIn(zoom) : this.getSpeedOut(zoom))) * deltaTracker.getGameTimeDeltaTicks(), this.cinematicScale, zoom.isZooming() ? 1.0F : 0.0F);
                this.render(guiGraphics, aspectRatio, this.cinematicScale, -16777216);
            }
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to draw cinematic zoom av: {}", error);
        }
    }

    private void render(GuiGraphics guiGraphics, AspectRatio aspectRatio, float scale, int color) {
        int guiWidth = guiGraphics.guiWidth();
        int guiHeight = guiGraphics.guiHeight();

        float guiRatio = (float) guiWidth / guiHeight;
        float avRatio = (float) aspectRatio.width() / aspectRatio.height();
        int size;

        if (guiRatio < avRatio) {
            int contentHeight = Math.round(guiWidth / avRatio);
            size = Math.round((guiHeight - contentHeight) / 2.0F * scale);
            guiGraphics.fill(RenderPipelines.GUI, 0, 0, guiWidth, size, color);
            guiGraphics.fill(RenderPipelines.GUI, 0, guiHeight - size, guiWidth, guiHeight, color);
        } else {
            int contentWidth = Math.round(guiHeight * avRatio);
            size = Math.round((guiWidth - contentWidth) / 2.0F * scale);
            guiGraphics.fill(RenderPipelines.GUI, 0, 0, size, guiHeight, color);
            guiGraphics.fill(RenderPipelines.GUI, guiWidth - size, 0, guiWidth, guiHeight, color);
        }
    }

    public void onZoomDisabled(Zoom zoom) {
        this.cinematicScale = 0.0F;
    }

    public record AspectRatio(int width, int height) {}

    public static class Builder {
        private Callable<AspectRatio> aspectRatio;
        private CoreRunnables.InputableCallable<Zoom, Float> speedOut;
        private CoreRunnables.InputableCallable<Zoom, Float> speedIn;
        private CoreRunnables.InputableCallable<Zoom, Boolean> isSpeedConfigEnabled;

        public Builder aspectRatio(Callable<AspectRatio> aspectRatio) {
            this.aspectRatio = aspectRatio;
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

        public CinematicZoomAV build(PerspectiveMod mod) {
            return new CinematicZoomAV(this.aspectRatio, this.speedOut, this.speedIn, this.isSpeedConfigEnabled, mod);
        }
    }
}
