/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.common.data.Data;
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
        return Data.idOf("textures/gui/title/update.png");
    }
}
