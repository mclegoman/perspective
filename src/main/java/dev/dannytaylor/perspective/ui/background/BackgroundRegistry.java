/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.background;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.events.Runnables;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.ui.UserInterfaceClient;
import dev.dannytaylor.perspective.ui.background.backgrounds.Background;
import dev.dannytaylor.perspective.ui.background.backgrounds.DefaultBackground;
import dev.dannytaylor.perspective.ui.background.blurs.DefaultBlur;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;
import dev.dannytaylor.perspective.ui.shaders.UserInterfaceShaders;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class BackgroundRegistry {
    public static Background DEFAULT = register(UserInterfaceClient.idOf("default"), new DefaultBackground());
    public static Background GAUSSIAN = register(UserInterfaceClient.idOf("gaussian"), new DefaultBackground(
            new DefaultBlur((guiGraphics, allocator) -> {
                UserInterfaceShaders.render(UserInterfaceShaders.GAUSSIAN_BACKGROUND_BLUR, new Runnables.GameRender.Data(ClientData.minecraft.getMainRenderTarget(), allocator));
                return false;
            })));
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
            BackgroundRegistry::noBlur, (isTitleScreen) -> false, false));

    public static void onInitializeClient(PerspectiveMod mod) {
        UserInterfaceEvents.onInitialize(mod, "Background Registry", () -> {
            Events.ShaderRender.register(UserInterfaceClient.idOf("gaussian"));
            Events.AfterClientResourceReload.register(UserInterfaceClient.idOf("gaussian"), BackgroundRegistry::applyGaussian);
        });
    }

    public static void applyGaussian() {
        Events.ShaderRender.modify(UserInterfaceClient.idOf("gaussian"), ShaderStacks.getShaders(UserInterfaceClient.idOf("gaussian"), ShaderStacks.getStack(UserInterfaceClient.idOf("background"), UserInterfaceClient.idOf("gaussian")), () -> UserInterfaceShaders.GAUSSIAN_BACKGROUND_BLUR, () -> true, (entry) -> false));
        for (Shader.Data data : Events.ShaderRender.get(UserInterfaceClient.idOf("gaussian")).shaders()) {
            System.out.println(data.id() + ":" + data.shader().getShaderId());
        }
    }

    public static Background register(Identifier identifier, Background background) {
        UserInterfaceEvents.Backgrounds.register(identifier, background);
        return background;
    }

    public static void renderGradiantBackground(GuiGraphics guiGraphics) {
        guiGraphics.fillGradient(0, 0, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), -1072689136, -804253680);
    }

    public static void renderTexturedBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, getBackgroundTexture(), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), 32, 32);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, UserInterfaceClient.idOf("textures/gui/legacy_menu_background.png"), 0, 0, 0, 0.0F, ClientData.minecraft.getWindow().getGuiScaledWidth(), ClientData.minecraft.getWindow().getGuiScaledHeight(), 32, 32);
    }

    public static void renderNone(GuiGraphics guiGraphics) {
    }

    public static boolean noBlur(GuiGraphics guiGraphics, GraphicsResourceAllocator allocator) {
        return false;
    }

    public static Identifier getBackgroundTexture() {
        return Identifier.withDefaultNamespace("textures/block/dirt.png");
    }

    public static Background getBackground() {
        return CLASSIC;
    }
}
