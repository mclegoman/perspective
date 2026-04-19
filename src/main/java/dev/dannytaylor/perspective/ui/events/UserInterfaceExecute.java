/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.events;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Runnables;
import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderTime;
import com.mclegoman.luminance.mixin.client.shaders.GameRendererAccessor;
import dev.dannytaylor.perspective.api.events.CoreExecute;
import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.background.CurrentBackground;
import dev.dannytaylor.perspective.ui.shaders.UserInterfaceShaders;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.TitleScreen;

public class UserInterfaceExecute extends CoreExecute {
    public static void renderUserInterfaceBackground(Minecraft minecraft, GuiGraphics guiGraphics) {
        if (BackgroundRegistry.getBackground() != null) {
            CurrentBackground currentBackground;
            if (minecraft.level != null && !(minecraft.screen instanceof LevelLoadingScreen) && !(minecraft.screen instanceof GenericMessageScreen))
                currentBackground = CurrentBackground.WORLD;
            else if (minecraft.screen instanceof TitleScreen) currentBackground = CurrentBackground.TITLE;
            else currentBackground = CurrentBackground.MENU;
            renderUserInterfaceBackground(guiGraphics, currentBackground);
        }
    }

    public static void renderUserInterfaceBackground(GuiGraphics guiGraphics, CurrentBackground currentBackground) {
        if (BackgroundRegistry.getBackground() != null) {
            BackgroundRegistry.getBackground().render(guiGraphics, currentBackground);
        }
    }

    public static void onBlur(Minecraft minecraft) {
        RenderLocations.RenderLocation<?> previous = ShaderTime.currentRenderLocation;
        ShaderTime.currentRenderLocation = UserInterfaceShaders.BLUR;
        UserInterfaceShaders.render(UserInterfaceShaders.BLUR, new Runnables.GameRender.Data(ClientData.minecraft.getMainRenderTarget(), ((GameRendererAccessor)minecraft.gameRenderer).getResourcePool()));
        ShaderTime.currentRenderLocation = previous;
    }
}
