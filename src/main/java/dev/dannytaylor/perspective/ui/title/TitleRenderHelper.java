/*
    UI Background
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.title;

import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.CoreData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

public class TitleRenderHelper {
    public static void updateTitleY(boolean isStart, GuiGraphics guiGraphics) {
        if (textureExists(getUpdateTexture())) {
            Matrix3x2fStack pose = guiGraphics.pose();
            if (isStart) {
                pose.pushMatrix();
                pose.translate(0.0F, -16.0F);
            } else pose.popMatrix();
        }
    }

    public static boolean textureExists(Identifier textureId) {
        return ClientData.minecraft.getResourceManager().getResource(textureId).isPresent();
    }

    public static Identifier getUpdateTexture() {
        return CoreData.idOf("textures/gui/title/update.png");
    }
}
