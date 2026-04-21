/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.overlays.overlays.ZoomAV;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;

import java.util.concurrent.Callable;

public class DefaultZoom extends AbstractZoom {
    private final LensRunnables.ZoomableBoolean isZooming;
    private final LensRunnables.Zoomable onStartZooming;
    private final LensRunnables.Zoomable onFinishZooming;
    private final Callable<ZoomScale> scale;
    private final Callable<ZoomTransition> transition;
    private final Callable<ZoomEffect> effect;
    private final LensRunnables.ZoomableBoolean shouldEffect;
    private final Callable<Float> amount;
    private final Callable<ZoomAV> audioVisual;
    private final LensRunnables.Zoomable onTickClient;
    private final LensRunnables.ZoomableBoolean isEnabled;
    
    private final PerspectiveMod mod;

    private DefaultZoom(
            LensRunnables.ZoomableBoolean isZoomingValue,
            LensRunnables.Zoomable onStartZoomingValue,
            LensRunnables.Zoomable onFinishZoomingValue,
            Callable<ZoomScale> scaleValue,
            Callable<ZoomTransition> transitionValue,
            Callable<ZoomEffect> effectValue,
            LensRunnables.ZoomableBoolean shouldEffectValue,
            Callable<Float> amountValue,
            LensRunnables.Zoomable onTickClientValue,
            Callable<ZoomAV> audioVisualValue,
            LensRunnables.ZoomableBoolean isEnabled,
            PerspectiveMod mod
    ) {
        this.isZooming = isZoomingValue;
        this.onStartZooming = onStartZoomingValue;
        this.onFinishZooming = onFinishZoomingValue;
        this.scale = scaleValue;
        this.transition = transitionValue;
        this.effect = effectValue;
        this.shouldEffect = shouldEffectValue;
        this.amount = amountValue;
        this.onTickClient = onTickClientValue;
        this.audioVisual = audioVisualValue;
        this.isEnabled = isEnabled;
        this.mod = mod;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isZooming() {
        try {
            if (this.isZooming != null) return this.isZooming.call(this);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to get zoom isZooming: {}", error);
        }
        return super.isZooming();
    }

    public void onStartZooming(Zoom zoom) {
        try {
            if (this.onStartZooming != null) this.onStartZooming.run(zoom);
            else super.isZooming();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to run zoom start: {}", error);
        }
    }

    public void onFinishZooming(Zoom zoom) {
        try {
            if (this.onFinishZooming != null) this.onFinishZooming.run(zoom);
            else super.isZooming();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod, "Failed to run zoom start: {}", error);
        }
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

    public boolean shouldEffect() {
        try {
            if (this.shouldEffect != null) {
                return this.shouldEffect.call(this);
            }
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get should zoom effect: {}", error);
        }
        return super.shouldEffect();
    }

    public float getZoomAmount() {
        try {
            if (this.amount != null) return this.amount.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom amount: {}", error);
        }
        return super.getZoomAmount();
    }

    public void onTickClient() {
        try {
            if (this.onTickClient != null) this.onTickClient.run(this);
            else super.onTickClient();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to tick zoom client: {}", error);
        }
    }

    public ZoomAV getAudioVisual() {
        try {
            if (this.audioVisual != null) return this.audioVisual.call();
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom overlay: {}", error);
        }
        return super.getAudioVisual();
    }

    public boolean isEnabled() {
        try {
            if (this.isEnabled != null) return this.isEnabled.call(this);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to check if zoom enabled: {}", error);
        }
        return super.isEnabled(); // should we default to false instead?
    }

    public static class Builder {
        private LensRunnables.ZoomableBoolean isZooming;
        private LensRunnables.Zoomable onStartZooming;
        private LensRunnables.Zoomable onFinishZooming;
        private Callable<ZoomScale> scale;
        private Callable<ZoomTransition> transition;
        private Callable<ZoomEffect> effect;
        private LensRunnables.ZoomableBoolean shouldEffect;
        private Callable<Float> amount;
        private LensRunnables.Zoomable onTickClient;
        private Callable<ZoomAV> audioVisual;
        private LensRunnables.ZoomableBoolean isEnabled;

        public Builder isZooming(LensRunnables.ZoomableBoolean isZooming) {
            this.isZooming = isZooming;
            return this;
        }

        public Builder onStartZooming(LensRunnables.Zoomable onStartZooming) {
            this.onStartZooming = onStartZooming;
            return this;
        }

        public Builder onFinishZooming(LensRunnables.Zoomable onFinishZooming) {
            this.onFinishZooming = onFinishZooming;
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

        public Builder shouldEffect(LensRunnables.ZoomableBoolean shouldEffect) {
            this.shouldEffect = shouldEffect;
            return this;
        }

        public Builder amount(Callable<Float> amount) {
            this.amount = amount;
            return this;
        }

        public Builder onTickClient(LensRunnables.Zoomable onTickClient) {
            this.onTickClient = onTickClient;
            return this;
        }

        public Builder audioVisual(Callable<ZoomAV> audioVisual) {
            this.audioVisual = audioVisual;
            return this;
        }

        public Builder isEnabled(LensRunnables.ZoomableBoolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public DefaultZoom build(PerspectiveMod mod) {
            return new DefaultZoom(this.isZooming, this.onStartZooming, this.onFinishZooming, this.scale, this.transition, this.effect, this.shouldEffect, this.amount, this.onTickClient, this.audioVisual, this.isEnabled, mod);
        }
    }
}
