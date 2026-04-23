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
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.ZoomAV;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;

import java.util.concurrent.Callable;

public class DefaultZoom extends AbstractZoom {
    private final CoreRunnables.InputableCallable<Zoom, Boolean> isZooming;
    private final CoreRunnables.InputableRunnable<Zoom> onStartZooming;
    private final CoreRunnables.InputableRunnable<Zoom> onFinishZooming;
    private final Callable<ZoomScale> scale;
    private final Callable<ZoomTransition> transition;
    private final Callable<ZoomEffect> effect;
    private final CoreRunnables.InputableCallable<Zoom, Boolean> shouldEffect;
    private final Callable<Float> amount;
    private final Callable<ZoomAV> audioVisual;
    private final CoreRunnables.InputableRunnable<Zoom> onTickClient;
    private final CoreRunnables.InputableCallable<Zoom, Boolean> isCinematic;
    private final CoreRunnables.InputableCallable<Zoom, Boolean> isEnabled;
    
    private final PerspectiveMod mod;

    private DefaultZoom(
            CoreRunnables.InputableCallable<Zoom, Boolean> isZoomingValue,
            CoreRunnables.InputableRunnable<Zoom> onStartZoomingValue,
            CoreRunnables.InputableRunnable<Zoom> onFinishZoomingValue,
            Callable<ZoomScale> scaleValue,
            Callable<ZoomTransition> transitionValue,
            Callable<ZoomEffect> effectValue,
            CoreRunnables.InputableCallable<Zoom, Boolean> shouldEffectValue,
            Callable<Float> amountValue,
            CoreRunnables.InputableRunnable<Zoom> onTickClientValue,
            CoreRunnables.InputableCallable<Zoom, Boolean> isCinematicValue,
            Callable<ZoomAV> audioVisualValue,
            CoreRunnables.InputableCallable<Zoom, Boolean> isEnabled,
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
        this.isCinematic = isCinematicValue;
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

    public boolean isCinematic(Zoom zoom) {
        try {
            if (this.isCinematic != null) return this.isCinematic.call(zoom);
        } catch (Exception error) {
            PerspectiveLog.error(this.mod,"Failed to get zoom cinematic: {}", error);
        }
        return super.isCinematic(zoom);
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
        private CoreRunnables.InputableCallable<Zoom, Boolean> isZooming;
        private CoreRunnables.InputableRunnable<Zoom> onStartZooming;
        private CoreRunnables.InputableRunnable<Zoom> onFinishZooming;
        private Callable<ZoomScale> scale;
        private Callable<ZoomTransition> transition;
        private Callable<ZoomEffect> effect;
        private CoreRunnables.InputableCallable<Zoom, Boolean> shouldEffect;
        private Callable<Float> amount;
        private CoreRunnables.InputableRunnable<Zoom> onTickClient;
        private CoreRunnables.InputableCallable<Zoom, Boolean> isCinematic;
        private Callable<ZoomAV> audioVisual;
        private CoreRunnables.InputableCallable<Zoom, Boolean> isEnabled;

        public Builder isZooming(CoreRunnables.InputableCallable<Zoom, Boolean> isZooming) {
            this.isZooming = isZooming;
            return this;
        }

        public Builder onStartZooming(CoreRunnables.InputableRunnable<Zoom> onStartZooming) {
            this.onStartZooming = onStartZooming;
            return this;
        }

        public Builder onFinishZooming(CoreRunnables.InputableRunnable<Zoom> onFinishZooming) {
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

        public Builder shouldEffect(CoreRunnables.InputableCallable<Zoom, Boolean> shouldEffect) {
            this.shouldEffect = shouldEffect;
            return this;
        }

        public Builder amount(Callable<Float> amount) {
            this.amount = amount;
            return this;
        }

        public Builder onTickClient(CoreRunnables.InputableRunnable<Zoom> onTickClient) {
            this.onTickClient = onTickClient;
            return this;
        }

        public Builder isCinematic(CoreRunnables.InputableCallable<Zoom, Boolean> isCinematic) {
            this.isCinematic = isCinematic;
            return this;
        }

        public Builder audioVisual(Callable<ZoomAV> audioVisual) {
            this.audioVisual = audioVisual;
            return this;
        }

        public Builder isEnabled(CoreRunnables.InputableCallable<Zoom, Boolean> isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public DefaultZoom build(PerspectiveMod mod) {
            return new DefaultZoom(this.isZooming, this.onStartZooming, this.onFinishZooming, this.scale, this.transition, this.effect, this.shouldEffect, this.amount, this.onTickClient, this.isCinematic, this.audioVisual, this.isEnabled, mod);
        }
    }
}
