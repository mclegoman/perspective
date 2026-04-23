/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms.audiovisuals;

import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.AbstractZoomAV;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.CinematicZoomAV;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.SpyglassZoomAV;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.ZoomAV;
import net.minecraft.resources.Identifier;

public class ZoomAVs {
    public static ZoomAV NONE = register(LensClient.idOf("none"), new AbstractZoomAV() {}, Float.MIN_VALUE);
    public static ZoomAV SPYGLASS = register(LensClient.idOf("spyglass"), SpyglassZoomAV.builder()
            .audio(() -> true)
            .visual(() -> true)
            .speedOut((zoom) -> zoom.getTransition().getSpeedOut(zoom))
            .speedIn((zoom) -> zoom.getTransition().getSpeedIn(zoom))
            .isSpeedConfigEnabled((zoom) -> zoom.getTransition().isSpeedConfigEnabled(zoom))
            .build(LensClient.getMod()), 10.0F);
    public static ZoomAV SPYGLASS_VISUAL_ONLY = register(LensClient.idOf("spyglass/visual_only"), SpyglassZoomAV.builder()
            .visual(() -> true)
            .speedOut((zoom) -> zoom.getTransition().getSpeedOut(zoom))
            .speedIn((zoom) -> zoom.getTransition().getSpeedIn(zoom))
            .isSpeedConfigEnabled((zoom) -> zoom.getTransition().isSpeedConfigEnabled(zoom))
            .build(LensClient.getMod()), 10.1F);
    public static ZoomAV SPYGLASS_AUDIO_ONLY = register(LensClient.idOf("spyglass/audio_only"), SpyglassZoomAV.builder()
            .audio(() -> true)
            .speedOut((zoom) -> zoom.getTransition().getSpeedOut(zoom))
            .speedIn((zoom) -> zoom.getTransition().getSpeedIn(zoom))
            .isSpeedConfigEnabled((zoom) -> zoom.getTransition().isSpeedConfigEnabled(zoom))
            .build(LensClient.getMod()), 10.2F);
    public static ZoomAV CINEMATIC = register(LensClient.idOf("cinematic"), CinematicZoomAV.builder()
            .aspectRatio(() -> new CinematicZoomAV.AspectRatio(21, 9))
            .speedOut((zoom) -> zoom.getTransition().getSpeedOut(zoom))
            .speedIn((zoom) -> zoom.getTransition().getSpeedIn(zoom))
            .isSpeedConfigEnabled((zoom) -> zoom.getTransition().isSpeedConfigEnabled(zoom))
            .build(LensClient.getMod()), 20.0F);

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom AVs", () -> {});
    }

    public static ZoomAV register(Identifier identifier, ZoomAV zoomAV) {
        LensEvents.ZoomAVs.register(identifier, zoomAV);
        return zoomAV;
    }

    public static ZoomAV register(Identifier identifier, ZoomAV zoomAV, float priority) {
        LensEvents.ZoomAVs.register(identifier, zoomAV, priority);
        return zoomAV;
    }
}
