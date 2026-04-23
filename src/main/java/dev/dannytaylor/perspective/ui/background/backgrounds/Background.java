/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import net.minecraft.client.gui.GuiGraphics;

public interface Background {
    boolean shouldRenderPanorama(boolean isTitleScreen);
    boolean shouldRenderMenuBackgroundTexture();
    void render(GuiGraphics guiGraphics, CurrentBackground currentBackground);
    BlurRenderer getBlurRenderer();
    CoreRunnables.InputableCallable<GuiGraphics, Boolean> getTransparentBackgroundRenderer();
}
