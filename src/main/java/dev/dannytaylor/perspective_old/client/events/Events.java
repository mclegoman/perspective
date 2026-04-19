/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.events;

import dev.dannytaylor.perspective.lens.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.lens.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.lens.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;

public class Events extends com.mclegoman.luminance.client.events.Events {
    public static final Registry<Runnables.UseItem> OnClientStartItemUse = new Registry<>();
    public static final Registry<Runnables.FinishUsingItem> OnClientFinishItemUse = new Registry<>();

    public static final Registry<ZoomScale> ZoomScales = new Registry<>();
    public static final Registry<ZoomTransition> ZoomTransitions = new Registry<>();
    public static final Registry<ZoomEffect> ZoomEffects = new Registry<>();
    public static final Registry<Zoom> Zooms = new Registry<>();
}
