/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.concurrent.Callable;

public class DefaultZoom extends AbstractZoom {
    private final LensRunnables.Zoomable isZooming;
    private final Callable<ZoomScale> scale;
    private final Callable<ZoomTransition> transition;
    private final Callable<ZoomEffect> effect;
    private final Callable<Float> amount;
    private final Callable<Float> transitionSpeedOut;
    private final Callable<Float> transitionSpeedIn;
    private final LensRunnables.ZoomOverlay guiOverlay;
    private final CoreRunnables.OnTickClient onTickClient;
    
    private final PerspectiveMod mod;

    private DefaultZoom(
            LensRunnables.Zoomable isZoomingValue,
            Callable<ZoomScale> scaleValue,
            Callable<ZoomTransition> transitionValue,
            Callable<ZoomEffect> effectValue,
            Callable<Float> amountValue,
            Callable<Float> transitionSpeedOutValue,
            Callable<Float> transitionSpeedInValue,
            CoreRunnables.OnTickClient onTickClientValue,
            LensRunnables.ZoomOverlay guiOverlay,
            PerspectiveMod mod
    ) {
        this.isZooming = isZoomingValue;
        this.scale = scaleValue;
        this.transition = transitionValue;
        this.effect = effectValue;
        this.amount = amountValue;
        this.transitionSpeedOut = transitionSpeedOutValue;
        this.transitionSpeedIn = transitionSpeedInValue;
        this.onTickClient = onTickClientValue;
        this.guiOverlay = guiOverlay;
        this.mod = mod;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isZooming() {
        try {
            if (this.isZooming != null) return this.isZooming.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to get zoom isZooming: {}", error);
        }
        return super.isZooming();
    }

    public ZoomScale getScale() {
        try {
            if (this.scale != null) return this.scale.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom scale: {}", error);
        }
        return super.getScale();
    }

    public ZoomTransition getTransition() {
        try {
            if (this.transition != null) return this.transition.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom transition: {}", error);
        }
        return super.getTransition();
    }

    public ZoomEffect getEffect() {
        try {
            if (this.effect != null) return this.effect.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom effect: {}", error);
        }
        return super.getEffect();
    }

    public float getZoomAmount() {
        try {
            if (this.amount != null) return this.amount.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom amount: {}", error);
        }
        return super.getZoomAmount();
    }

    public float getTransitionSpeedOut() {
        try {
            if (this.transitionSpeedOut != null) return this.transitionSpeedOut.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom transition speed out: {}", error);
        }
        return super.getTransitionSpeedOut();
    }

    public float getTransitionSpeedIn() {
        try {
            if (this.transitionSpeedIn != null) return this.transitionSpeedIn.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom transition speed in: {}", error);
        }
        return super.getTransitionSpeedIn();
    }

    public void onTickClient(Minecraft minecraft) {
        try {
            if (this.onTickClient != null) this.onTickClient.run(minecraft);
            else super.onTickClient(minecraft);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to tick zoom client: {}", error);
        }
    }

    public void draw(GuiGraphics graphics, DeltaTracker deltaTracker) {
        try {
            if (this.guiOverlay != null) this.guiOverlay.draw(graphics, deltaTracker, this);
            else super.draw(graphics, deltaTracker);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to draw zoom gui overlay: {}", error);
        }
    }

    public static class Builder {
        private LensRunnables.Zoomable isZooming;
        private Callable<ZoomScale> scale;
        private Callable<ZoomTransition> transition;
        private Callable<ZoomEffect> effect;
        private Callable<Float> amount;
        private Callable<Float> transitionSpeedOut;
        private Callable<Float> transitionSpeedIn;
        private CoreRunnables.OnTickClient onTickClient;
        private LensRunnables.ZoomOverlay guiOverlay;

        public Builder isZooming(LensRunnables.Zoomable isZooming) {
            this.isZooming = isZooming;
            return this;
        }

        public Builder scale(Callable<ZoomScale> scale) {
            this.scale = scale;
            return this;
        }

        public Builder transition(Callable<ZoomTransition> transition) {
            this.transition = transition;
            return this;
        }

        public Builder effect(Callable<ZoomEffect> effect) {
            this.effect = effect;
            return this;
        }

        public Builder amount(Callable<Float> amount) {
            this.amount = amount;
            return this;
        }

        public Builder transitionSpeedOut(Callable<Float> transitionSpeedOut) {
            this.transitionSpeedOut = transitionSpeedOut;
            return this;
        }

        public Builder transitionSpeedIn(Callable<Float> transitionSpeedIn) {
            this.transitionSpeedIn = transitionSpeedIn;
            return this;
        }

        public Builder onTickClient(CoreRunnables.OnTickClient onTickClient) {
            this.onTickClient = onTickClient;
            return this;
        }

        public Builder guiOverlay(LensRunnables.ZoomOverlay guiOverlay) {
            this.guiOverlay = guiOverlay;
            return this;
        }

        public DefaultZoom build(PerspectiveMod mod) {
            return new DefaultZoom(this.isZooming, this.scale, this.transition, this.effect, this.amount, this.transitionSpeedOut, this.transitionSpeedIn, this.onTickClient, this.guiOverlay, mod);
        }
    }
}
