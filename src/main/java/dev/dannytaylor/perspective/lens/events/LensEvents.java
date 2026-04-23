/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.events;

import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.audiovisuals.audiovisual.ZoomAV;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public class LensEvents extends CoreEvents {
    public static final PriorityRegistry<ZoomScale> ZoomScales = new PriorityRegistry<>();
    public static final PriorityRegistry<ZoomTransition> ZoomTransitions = new PriorityRegistry<>();
    public static final PriorityRegistry<ZoomEffect> ZoomEffects = new PriorityRegistry<>();
    public static final PriorityRegistry<ZoomAV> ZoomAVs = new PriorityRegistry<>();
    public static final Registry<Zoom> Zooms = new Registry<>();
}
