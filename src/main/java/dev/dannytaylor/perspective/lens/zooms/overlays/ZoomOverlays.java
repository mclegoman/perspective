/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.overlays;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.mixin.zooms.GuiAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class ZoomOverlays {
    public static LensRunnables.ZoomOverlay NONE = register(LensClient.idOf("none"), (guiGraphics, deltaTracker, zoom) -> {});
    public static LensRunnables.ZoomOverlay SPYGLASS = register(LensClient.idOf("spyglass"), (guiGraphics, deltaTracker, zoom) -> {
        ZoomOverlays.scopeScale = Mth.lerp((0.5F * zoom.getTransitionSpeedIn()) * deltaTracker.getGameTimeDeltaTicks(), ZoomOverlays.scopeScale, 1.125F);
        if (zoom.isZooming()) {
            if (ClientData.minecraft.options.getCameraType().isFirstPerson()) ((GuiAccessor)ClientData.minecraft.gui).perspective$renderSpyglassOverlay(guiGraphics, ZoomOverlays.scopeScale);
        } else ZoomOverlays.scopeScale = 0.5F;
    });

    private static float scopeScale;

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom Overlays", () -> {});
    }

    public static LensRunnables.ZoomOverlay register(Identifier identifier, LensRunnables.ZoomOverlay guiOverlay) {
        LensEvents.ZoomOverlays.register(identifier, guiOverlay);
        return guiOverlay;
    }
}
