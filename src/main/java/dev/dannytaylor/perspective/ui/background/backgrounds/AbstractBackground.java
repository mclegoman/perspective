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

public abstract class AbstractBackground implements Background {
    public boolean shouldRenderPanorama(boolean isTitleScreen) {
        return true;
    }

    public boolean shouldRenderMenuBackgroundTexture() {
        return true;
    }

    public void render(GuiGraphics guiGraphics, CurrentBackground currentBackground) {
    }

    public BlurRenderer getBlurRenderer() {
        return new BlurRenderer((guiGraphics) -> {}, () -> true);
    }

    public CoreRunnables.InputableCallable<GuiGraphics, Boolean> getTransparentBackgroundRenderer() {
        return (guiGraphics) -> true;
    }
}
