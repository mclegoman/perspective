/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import dev.dannytaylor.perspective.client.registry.zooms.scales.scale.ZoomScale;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.transition.ZoomTransition;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;

public class Events extends com.mclegoman.luminance.client.events.Events {
    public static final Registry<Runnables.UseItem> OnClientStartItemUse = new Registry<>();
    public static final Registry<Runnables.FinishUsingItem> OnClientFinishItemUse = new Registry<>();

    public static final Registry<ZoomScale> ZoomScales = new Registry<>();
    public static final Registry<ZoomTransition> ZoomTransitions = new Registry<>();
    public static final Registry<Zoom> Zooms = new Registry<>();
}
