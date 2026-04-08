/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.zoom;

import dev.dannytaylor.perspective.client.events.Runnables;
import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.Minecraft;

import java.util.concurrent.Callable;

public class DefaultZoom extends AbstractZoom {
    private final Callable<Boolean> isZooming;
    private final Callable<ZoomScale> scale;
    private final Callable<ZoomTransition> transition;
    private final Callable<Float> amount;
    private final Callable<Float> transitionSpeedOut;
    private final Callable<Float> transitionSpeedIn;
    private final Runnables.OnTickClient onTickClient;

    public DefaultZoom(
            Callable<Boolean> isZoomingValue,
            Callable<ZoomScale> scaleValue,
            Callable<ZoomTransition> transitionValue,
            Callable<Float> amountValue,
            Callable<Float> transitionSpeedOutValue,
            Callable<Float> transitionSpeedInValue,
            Runnables.OnTickClient onTickClientValue
    ) {
        this.isZooming = isZoomingValue;
        this.scale = scaleValue;
        this.transition = transitionValue;
        this.amount = amountValue;
        this.transitionSpeedOut = transitionSpeedOutValue;
        this.transitionSpeedIn = transitionSpeedInValue;
        this.onTickClient = onTickClientValue;
    }

    public boolean isZooming() {
        try {
            return isZooming.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom isZooming: {}", error);
            return false;
        }
    }

    public ZoomScale getScale() {
        try {
            return scale.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom scale: {}", error);
            return null;
        }
    }

    public ZoomTransition getTransition() {
        try {
            return transition.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom transition: {}", error);
            return null;
        }
    }

    public float getZoomAmount() {
        try {
            return amount.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom amount: {}", error);
            return 0.0F;
        }
    }

    public float getTransitionSpeedOut() {
        try {
            return transitionSpeedOut.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom transition speed out: {}", error);
            return 1.0F;
        }
    }

    public float getTransitionSpeedIn() {
        try {
            return transitionSpeedIn.call();
        } catch (Exception error) {
            Log.error("Failed to get zoom transition speed in: {}", error);
            return 1.0F;
        }
    }

    public void onTickClient(Minecraft minecraft) {
        try {
            this.onTickClient.run(minecraft);
        } catch (Exception error) {
            Log.error("Failed to tick zoom client: {}", error);
        }
    }
}
