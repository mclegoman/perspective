/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.mixin.zooms;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(priority = 100, value = Gui.class)
public interface GuiAccessor {
    @Invoker("renderSpyglassOverlay")
    void perspective$renderSpyglassOverlay(GuiGraphics guiGraphics, float scale);
}
