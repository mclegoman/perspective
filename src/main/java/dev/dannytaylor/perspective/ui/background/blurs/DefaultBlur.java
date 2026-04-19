/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.blurs;

import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import dev.dannytaylor.perspective.ui.events.UserInterfaceRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class DefaultBlur extends AbstractBlur {
    private final UserInterfaceRunnables.BlurDrawable renderBlur;

    public DefaultBlur(UserInterfaceRunnables.BlurDrawable renderBlur) {
        this.renderBlur = renderBlur;
    }

    public boolean render(GuiGraphics guiGraphics, GraphicsResourceAllocator allocator) {
        return this.renderBlur.draw(guiGraphics, allocator);
    }
}
