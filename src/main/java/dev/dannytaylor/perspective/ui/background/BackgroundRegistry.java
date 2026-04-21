/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.ui.UserInterfaceClient;
import dev.dannytaylor.perspective.ui.background.backgrounds.Background;
import dev.dannytaylor.perspective.ui.background.backgrounds.DefaultBackground;
import dev.dannytaylor.perspective.ui.config.UserInterfaceConfig;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;
import dev.dannytaylor.perspective.ui.shaders.UserInterfaceShaders;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class BackgroundRegistry {
    public static Background VANILLA = register(UserInterfaceClient.idOf("vanilla"), new DefaultBackground());
    public static Background GAUSSIAN = register(UserInterfaceClient.idOf("gaussian"), new DefaultBackground(BackgroundRegistry::noBlur));
    public static Background LEGACY = register(UserInterfaceClient.idOf("legacy"), new DefaultBackground(
            BackgroundRegistry::renderGradiantBackground,
            BackgroundRegistry::renderTexturedBackground,
            (guiGraphics) -> {},
            BackgroundRegistry::noBlur, (isTitleScreen) -> isTitleScreen, true));
    public static Background CLASSIC = register(UserInterfaceClient.idOf("classic"), new DefaultBackground(
            BackgroundRegistry::renderGradiantBackground,
            BackgroundRegistry::renderTexturedBackground,
            BackgroundRegistry::renderTexturedBackground,
            BackgroundRegistry::noBlur, (isTitleScreen) -> false, true));
    public static Background NONE = register(UserInterfaceClient.idOf("none"), new DefaultBackground(
            BackgroundRegistry::renderNone,
            BackgroundRegistry::renderNone,
            BackgroundRegistry::renderNone,
            BackgroundRegistry::noBlur,
            BackgroundRegistry::noBlur, (isTitleScreen) -> true, false));

    public static void onInitializeClient(PerspectiveMod mod) {
        UserInterfaceEvents.onInitialize(mod, "Background Registry", () -> {
            Events.ShaderRender.register(UserInterfaceClient.idOf("gaussian"));
            Events.AfterClientResourceReload.register(UserInterfaceClient.idOf("gaussian"), BackgroundRegistry::applyGaussian);
        });
    }

    public static void applyGaussian() {
        Events.ShaderRender.modify(UserInterfaceClient.idOf("gaussian"), ShaderStacks.getShaders(UserInterfaceClient.idOf("gaussian"), ShaderStacks.getStack(UserInterfaceClient.idOf("background"), UserInterfaceClient.idOf("gaussian")), () -> UserInterfaceShaders.BLUR, () -> getBackground().equals(GAUSSIAN), (entry) -> false));
    }

    public static Background register(Identifier identifier, Background background) {
        UserInterfaceEvents.Backgrounds.register(identifier, background);
        return background;
    }

    public static void renderGradiantBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), -1072689136, -804253680);
    }

    public static void renderGradiantBackground(GuiGraphics guiGraphics, boolean isBlurred) {
        if (isBlurred) guiGraphics.fillGradient(0, 0, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), -1072689136, -804253680);
    }

    public static void renderTexturedBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), 32, 32);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UserInterfaceClient.idOf("textures/gui/legacy_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), 32, 32);
    }

    public static void renderNone(GuiGraphics guiGraphics) {
    }

    public static void renderNone(GuiGraphics guiGraphics, boolean isBlurred) {
    }

    public static boolean noBlur(GuiGraphics guiGraphics) {
        return false;
    }

    public static Identifier getBackgroundTexture() {
        return UserInterfaceConfig.instance.backgroundTexture.value().getIdentifier();
    }

    public static Background getBackground() {
        return UserInterfaceEvents.Backgrounds.get(UserInterfaceConfig.instance.background.value().getIdentifier());
    }
}
