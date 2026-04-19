/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.background.blurs.BackgroundRenderer;
import dev.dannytaylor.perspective.ui.background.blurs.DefaultBackgroundRenderer;
import dev.dannytaylor.perspective.ui.events.UserInterfaceRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class DefaultBackground extends AbstractBackground {
    private final UserInterfaceRunnables.WorldScreenDrawable renderWorld;
    private final UserInterfaceRunnables.Drawable renderMenu;
    private final UserInterfaceRunnables.Drawable renderTitle;
    private final BackgroundRenderer blurRenderer;
    private final BackgroundRenderer transparentBackgroundRenderer;
    private final UserInterfaceRunnables.RenderPanorama shouldRenderPanorama;
    private final boolean shouldRenderMenuBackgroundTexture;

    public DefaultBackground() {
        this(
                (guiGraphics, isBlurred) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                new DefaultBackgroundRenderer((guiGraphics) -> true),
                (isTitleScreen) -> true,
                true
        );
    }

    public DefaultBackground(BackgroundRenderer blurRenderer) {
        this(
                (guiGraphics, isBlurred) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                blurRenderer,
                (isTitleScreen) -> true,
                true

        );
    }

    public DefaultBackground(UserInterfaceRunnables.WorldScreenDrawable renderWorld, UserInterfaceRunnables.Drawable renderMenu, UserInterfaceRunnables.Drawable renderTitle, BackgroundRenderer blurRenderer, UserInterfaceRunnables.RenderPanorama shouldRenderPanorama, boolean shouldRenderMenuBackgroundTexture) {
        this(renderWorld, renderMenu, renderTitle, blurRenderer, new DefaultBackgroundRenderer((guiGraphics) -> true), shouldRenderPanorama, shouldRenderMenuBackgroundTexture);
    }

    public DefaultBackground(UserInterfaceRunnables.WorldScreenDrawable renderWorld, UserInterfaceRunnables.Drawable renderMenu, UserInterfaceRunnables.Drawable renderTitle, BackgroundRenderer blurRenderer, BackgroundRenderer transparentBackgroundRenderer, UserInterfaceRunnables.RenderPanorama shouldRenderPanorama, boolean shouldRenderMenuBackgroundTexture) {
        this.renderWorld = renderWorld;
        this.renderMenu = renderMenu;
        this.renderTitle = renderTitle;
        this.blurRenderer = blurRenderer;
        this.transparentBackgroundRenderer = transparentBackgroundRenderer;
        this.shouldRenderPanorama = shouldRenderPanorama;
        this.shouldRenderMenuBackgroundTexture = shouldRenderMenuBackgroundTexture;
    }

    public void render(GuiGraphics guiGraphics, CurrentBackground currentBackground) {
        switch (currentBackground) {
            case WORLD -> {
                if (this.renderWorld != null) this.renderWorld.draw(guiGraphics, true);
            }
            case TRANSPARENT_BACKGROUND -> {
                if (this.renderWorld != null) this.renderWorld.draw(guiGraphics, false);
            }
            case MENU -> {
                if (this.renderMenu != null) this.renderMenu.draw(guiGraphics);
            }
            case TITLE -> {
                if (this.renderTitle != null) this.renderTitle.draw(guiGraphics);
            }
        }
    }

    public BackgroundRenderer getBlurRenderer() {
        return this.blurRenderer;
    }

    public BackgroundRenderer getTransparentBackgroundRenderer() {
        return this.transparentBackgroundRenderer;
    }

    public boolean shouldRenderMenuBackgroundTexture() {
        return this.shouldRenderMenuBackgroundTexture;
    }

    public boolean shouldRenderPanorama(boolean isTitleScreen) {
        return this.shouldRenderPanorama.call(isTitleScreen);
    }
}
