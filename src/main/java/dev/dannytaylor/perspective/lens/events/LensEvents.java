/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public class LensEvents extends CoreEvents {
    public static final Registry<ZoomScale> ZoomScales = new Registry<>();
    public static final Registry<ZoomTransition> ZoomTransitions = new Registry<>();
    public static final Registry<ZoomEffect> ZoomEffects = new Registry<>();
    public static final Registry<LensRunnables.ZoomOverlay> ZoomOverlays = new Registry<>();
    public static final Registry<Zoom> Zooms = new Registry<>();
}
