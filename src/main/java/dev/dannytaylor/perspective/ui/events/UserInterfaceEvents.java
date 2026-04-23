/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.events;

import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.ui.background.backgrounds.Background;

public class UserInterfaceEvents extends CoreEvents {
    public static final PriorityRegistry<Background> Backgrounds = new PriorityRegistry<>();
}
