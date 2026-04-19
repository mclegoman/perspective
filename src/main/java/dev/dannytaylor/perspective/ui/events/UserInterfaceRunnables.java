/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.events;

import dev.dannytaylor.perspective.api.events.CoreRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class UserInterfaceRunnables extends CoreRunnables {
    public interface WorldScreenDrawable {
        void draw(GuiGraphics guiGraphics, boolean isBlurred);
    }

    public interface RenderPanorama {
        boolean call(boolean isTitleScreen);
    }

    public interface Drawable {
        void draw(GuiGraphics guiGraphics);
    }

    public interface CancellableDrawable {
        boolean draw(GuiGraphics guiGraphics);
    }
}
