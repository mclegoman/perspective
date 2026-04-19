/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.blurs;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import net.minecraft.client.gui.GuiGraphics;

public interface Blur {
    boolean render(GuiGraphics guiGraphics, GraphicsResourceAllocator allocator);
}
