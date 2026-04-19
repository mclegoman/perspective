/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.background.blurs.Blur;
import dev.dannytaylor.perspective.ui.background.blurs.DefaultBlur;
import dev.dannytaylor.perspective.ui.events.UserInterfaceRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class DefaultBackground extends AbstractBackground {
    private final UserInterfaceRunnables.ScreenDrawable renderWorld;
    private final UserInterfaceRunnables.ScreenDrawable renderMenu;
    private final UserInterfaceRunnables.ScreenDrawable renderTitle;
    private final Blur blur;
    private final UserInterfaceRunnables.RenderPanorama shouldRenderPanorama;
    private final boolean shouldRenderMenuBackgroundTexture;

    public DefaultBackground() {
        this(
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                new DefaultBlur((guiGraphics, allocator) -> true),
                (isTitleScreen) -> true,
                true
        );
    }

    public DefaultBackground(Blur blur) {
        this(
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                blur,
                (isTitleScreen) -> true,
                true

        );
    }

    public DefaultBackground(UserInterfaceRunnables.ScreenDrawable renderWorld, UserInterfaceRunnables.ScreenDrawable renderMenu, UserInterfaceRunnables.ScreenDrawable renderTitle, Blur blur, UserInterfaceRunnables.RenderPanorama shouldRenderPanorama, boolean shouldRenderMenuBackgroundTexture) {
        this.renderWorld = renderWorld;
        this.renderMenu = renderMenu;
        this.renderTitle = renderTitle;
        this.blur = blur;
        this.shouldRenderPanorama = shouldRenderPanorama;
        this.shouldRenderMenuBackgroundTexture = shouldRenderMenuBackgroundTexture;
    }

    public void render(GuiGraphics guiGraphics, CurrentBackground currentBackground) {
        switch (currentBackground) {
            case WORLD -> {
                if (this.renderWorld != null) this.renderWorld.draw(guiGraphics);
            }
            case MENU -> {
                if (this.renderMenu != null) this.renderMenu.draw(guiGraphics);
            }
            case TITLE -> {
                if (this.renderTitle != null) this.renderTitle.draw(guiGraphics);
            }
        }
    }

    public Blur getBlurRenderer() {
        return this.blur;
    }

    public boolean shouldRenderMenuBackgroundTexture() {
        return this.shouldRenderMenuBackgroundTexture;
    }

    public boolean shouldRenderPanorama(boolean isTitleScreen) {
        return this.shouldRenderPanorama.call(isTitleScreen);
    }
}
