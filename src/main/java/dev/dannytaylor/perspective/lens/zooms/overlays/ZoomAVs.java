/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.overlays;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.overlays.overlays.AbstractZoomAV;
import dev.dannytaylor.perspective.lens.zooms.overlays.overlays.SpyglassZoomAV;
import dev.dannytaylor.perspective.lens.zooms.overlays.overlays.ZoomAV;
import net.minecraft.resources.Identifier;

public class ZoomAVs {
    public static ZoomAV NONE = register(LensClient.idOf("none"), new AbstractZoomAV() {});
    public static ZoomAV SPYGLASS = register(LensClient.idOf("spyglass"), new SpyglassZoomAV(() -> true, () -> true));
    public static ZoomAV SPYGLASS_AUDIO_ONLY = register(LensClient.idOf("spyglass/audio_only"), new SpyglassZoomAV(() -> true, () -> false));
    public static ZoomAV SPYGLASS_VISUAL_ONLY = register(LensClient.idOf("spyglass/visual_only"), new SpyglassZoomAV(() -> false, () -> true));

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom AVs", () -> {});
    }

    public static ZoomAV register(Identifier identifier, ZoomAV zoomAV) {
        LensEvents.ZoomAVs.register(identifier, zoomAV);
        return zoomAV;
    }
}
