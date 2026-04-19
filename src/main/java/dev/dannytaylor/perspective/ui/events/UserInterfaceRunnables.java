/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.events;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class UserInterfaceRunnables extends CoreRunnables {
    public interface ScreenDrawable {
        void draw(GuiGraphics guiGraphics);
    }

    public interface RenderPanorama {
        boolean call(boolean isTitleScreen);
    }

    public interface BlurDrawable {
        boolean draw(GuiGraphics guiGraphics, GraphicsResourceAllocator allocator);
    }
}
