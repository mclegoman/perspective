/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.background.blurs.BackgroundRenderer;
import net.minecraft.client.gui.GuiGraphics;

public interface Background {
    boolean shouldRenderPanorama(boolean isTitleScreen);
    boolean shouldRenderMenuBackgroundTexture();
    void render(GuiGraphics guiGraphics, CurrentBackground currentBackground);
    BackgroundRenderer getBlurRenderer();
    BackgroundRenderer getTransparentBackgroundRenderer();
}
