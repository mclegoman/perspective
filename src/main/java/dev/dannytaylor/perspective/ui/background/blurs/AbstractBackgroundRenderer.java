/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.blurs;

import net.minecraft.client.gui.GuiGraphics;

public abstract class AbstractBackgroundRenderer implements BackgroundRenderer {
    public boolean render(GuiGraphics guiGraphics) {
        return true;
    }
}
