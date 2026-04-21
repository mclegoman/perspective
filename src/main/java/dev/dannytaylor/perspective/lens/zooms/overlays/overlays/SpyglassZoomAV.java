package dev.dannytaylor.perspective.lens.zooms.overlays.overlays;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.mixin.zooms.GuiAccessor;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;

import java.util.concurrent.Callable;

public class SpyglassZoomAV extends AbstractZoomAV {
    private final Callable<Boolean> audio;
    private final Callable<Boolean> visual;
    private float scopeScale;

    public SpyglassZoomAV(Callable<Boolean> audio, Callable<Boolean> visual) {
        this.audio = audio;
        this.visual = visual;
    }

    public void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.visual.call()) {
                scopeScale = Mth.lerp((0.5F * zoom.getTransition().getSpeedIn()) * deltaTracker.getGameTimeDeltaTicks(), scopeScale, LensConfig.instance.scopeScale.value());
                if (zoom.isEnabled() && zoom.isZooming() && !(ClientData.minecraft.player != null && ClientData.minecraft.player.isScoping())) {
                    if (ClientData.minecraft.options.getCameraType().isFirstPerson()) ((GuiAccessor)ClientData.minecraft.gui).perspective$renderSpyglassOverlay(guiGraphics, scopeScale);
                } else scopeScale = 0.5F;
            }
        } catch (Exception error) {
            PerspectiveLog.error(LensClient.getMod(), "Failed to draw spyglass zoom av: {}", error);
        }
    }

    public void onStart(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.audio.call() && ClientData.minecraft.player != null) ClientData.minecraft.player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        } catch (Exception error) {
            PerspectiveLog.error(LensClient.getMod(), "Failed to onStart spyglass zoom av: {}", error);
        }
    }

    public void onFinish(GuiGraphics guiGraphics, DeltaTracker deltaTracker, Zoom zoom) {
        try {
            if (this.audio.call() && ClientData.minecraft.player != null) ClientData.minecraft.player.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
        } catch (Exception error) {
            PerspectiveLog.error(LensClient.getMod(), "Failed to onFinish spyglass zoom av: {}", error);
        }
    }
}
