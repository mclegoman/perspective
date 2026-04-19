/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.blurs;

import dev.dannytaylor.perspective.ui.events.UserInterfaceRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class DefaultBackgroundRenderer extends AbstractBackgroundRenderer {
    private final UserInterfaceRunnables.CancellableDrawable render;

    public DefaultBackgroundRenderer(UserInterfaceRunnables.CancellableDrawable render) {
        this.render = render;
    }

    public boolean render(GuiGraphics guiGraphics) {
        return this.render.draw(guiGraphics);
    }
}
