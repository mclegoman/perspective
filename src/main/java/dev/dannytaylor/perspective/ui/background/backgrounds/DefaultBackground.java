/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.events.UserInterfaceRunnables;
import net.minecraft.client.gui.GuiGraphics;

public class DefaultBackground extends AbstractBackground {
    private final UserInterfaceRunnables.WorldScreenDrawable renderWorld;
    private final CoreRunnables.InputableRunnable<GuiGraphics> renderMenu;
    private final CoreRunnables.InputableRunnable<GuiGraphics> renderTitle;
    private final BlurRenderer blurRenderer;
    private final CoreRunnables.InputableCallable<GuiGraphics, Boolean> transparentBackgroundRenderer;
    private final UserInterfaceRunnables.RenderPanorama shouldRenderPanorama;
    private final boolean shouldRenderMenuBackgroundTexture;

    public DefaultBackground() {
        this(
                (guiGraphics, isBlurred) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                new BlurRenderer((guiGraphics) -> {}, () -> true),
                (isTitleScreen) -> true,
                true
        );
    }

    public DefaultBackground(BlurRenderer blurRenderer) {
        this(
                (guiGraphics, isBlurred) -> {},
                (guiGraphics) -> {},
                (guiGraphics) -> {},
                blurRenderer,
                (isTitleScreen) -> true,
                true

        );
    }

    public DefaultBackground(UserInterfaceRunnables.WorldScreenDrawable renderWorld, CoreRunnables.InputableRunnable<GuiGraphics> renderMenu, CoreRunnables.InputableRunnable<GuiGraphics> renderTitle, BlurRenderer blurRenderer, UserInterfaceRunnables.RenderPanorama shouldRenderPanorama, boolean shouldRenderMenuBackgroundTexture) {
        this(renderWorld, renderMenu, renderTitle, blurRenderer, (guiGraphics) -> true, shouldRenderPanorama, shouldRenderMenuBackgroundTexture);
    }

    public DefaultBackground(UserInterfaceRunnables.WorldScreenDrawable renderWorld, CoreRunnables.InputableRunnable<GuiGraphics> renderMenu, CoreRunnables.InputableRunnable<GuiGraphics> renderTitle, BlurRenderer blurRenderer, CoreRunnables.InputableCallable<GuiGraphics, Boolean> transparentBackgroundRenderer, UserInterfaceRunnables.RenderPanorama shouldRenderPanorama, boolean shouldRenderMenuBackgroundTexture) {
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
                if (this.renderMenu != null) this.renderMenu.run(guiGraphics);
            }
            case TITLE -> {
                if (this.renderTitle != null) this.renderTitle.run(guiGraphics);
            }
        }
    }

    public BlurRenderer getBlurRenderer() {
        return this.blurRenderer;
    }

    public CoreRunnables.InputableCallable<GuiGraphics, Boolean> getTransparentBackgroundRenderer() {
        return this.transparentBackgroundRenderer;
    }

    public boolean shouldRenderMenuBackgroundTexture() {
        return this.shouldRenderMenuBackgroundTexture;
    }

    public boolean shouldRenderPanorama(boolean isTitleScreen) {
        return this.shouldRenderPanorama.call(isTitleScreen);
    }
}
