/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.events;

import dev.dannytaylor.perspective.api.events.CoreExecute;
import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.background.CurrentBackground;
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
            BackgroundRegistry.getBackground().render(guiGraphics, currentBackground);
        }
    }
}
