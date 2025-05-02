/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.mclegoman.perspective.client.events.runnables.PerspectiveRunnables;

public class PerspectiveEvents extends com.mclegoman.luminance.client.events.Events {
	public static final Registry<PerspectiveRunnables.UseItem> OnStartItemUse = new Registry<>();
	public static final Registry<PerspectiveRunnables.FinishUsingItem> OnFinishItemUse = new Registry<>();
}
