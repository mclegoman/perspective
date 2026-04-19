/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.zoom;

import dev.dannytaylor.perspective_old.client.events.Runnables;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import net.minecraft.client.Minecraft;

import java.util.concurrent.Callable;

public class DefaultZoom extends AbstractZoom {
    private final Callable<Boolean> isZooming;
    private final Callable<ZoomScale> scale;
    private final Callable<ZoomTransition> transition;
    private final Callable<ZoomEffect> effect;
    private final Callable<Float> amount;
    private final Callable<Float> transitionSpeedOut;
    private final Callable<Float> transitionSpeedIn;
    private final Runnables.OnTickClient onTickClient;

    public DefaultZoom(
            Callable<Boolean> isZoomingValue,
            Callable<ZoomScale> scaleValue,
            Callable<ZoomTransition> transitionValue,
            Callable<ZoomEffect> effectValue,
            Callable<Float> amountValue,
            Callable<Float> transitionSpeedOutValue,
            Callable<Float> transitionSpeedInValue,
            Runnables.OnTickClient onTickClientValue
    ) {
        this.isZooming = isZoomingValue;
        this.scale = scaleValue;
        this.transition = transitionValue;
        this.effect = effectValue;
        this.amount = amountValue;
        this.transitionSpeedOut = transitionSpeedOutValue;
        this.transitionSpeedIn = transitionSpeedInValue;
        this.onTickClient = onTickClientValue;
    }

    public boolean isZooming() {
        try {
            return this.isZooming.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom isZooming: {}", error);
            return false;
        }
    }

    public ZoomScale getScale() {
        try {
            return this.scale.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom scale: {}", error);
            return null;
        }
    }

    public ZoomTransition getTransition() {
        try {
            return this.transition.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom transition: {}", error);
            return null;
        }
    }

    public ZoomEffect getEffect() {
        try {
            return this.effect.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom effect: {}", error);
            return null;
        }
    }

    public float getZoomAmount() {
        try {
            return this.amount.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom amount: {}", error);
            return 0.0F;
        }
    }

    public float getTransitionSpeedOut() {
        try {
            return this.transitionSpeedOut.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom transition speed out: {}", error);
            return 1.0F;
        }
    }

    public float getTransitionSpeedIn() {
        try {
            return this.transitionSpeedIn.call();
        } catch (Exception error) {
            //StaticLog.error("Failed to get zoom transition speed in: {}", error);
            return 1.0F;
        }
    }

    public void onTickClient(Minecraft minecraft) {
        try {
            this.onTickClient.run(minecraft);
        } catch (Exception error) {
            //StaticLog.error("Failed to tick zoom client: {}", error);
        }
    }
}
