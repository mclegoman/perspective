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

public abstract class AbstractBackground implements Background {
    public boolean shouldRenderPanorama(boolean isTitleScreen) {
        return true;
    }

    public boolean shouldRenderMenuBackgroundTexture() {
        return true;
    }

    public void render(GuiGraphics guiGraphics, CurrentBackground currentBackground) {
    }

    public BackgroundRenderer getBlurRenderer() {
        return (guiGraphics) -> true;
    }

    public BackgroundRenderer getTransparentBackgroundRenderer() {
        return (guiGraphics) -> true;
    }
}
