/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

public class Events extends com.mclegoman.luminance.client.events.Events {
    public static final Registry<Runnables.UseItem> OnClientStartItemUse = new Registry<>();
    public static final Registry<Runnables.FinishUsingItem> OnClientFinishItemUse = new Registry<>();
}
