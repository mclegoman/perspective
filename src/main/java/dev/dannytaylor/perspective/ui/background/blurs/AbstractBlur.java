/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.blurs;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.gui.GuiGraphics;

public abstract class AbstractBlur implements Blur {
    public boolean render(GuiGraphics guiGraphics, GraphicsResourceAllocator allocator) {
        return true;
    }
}
