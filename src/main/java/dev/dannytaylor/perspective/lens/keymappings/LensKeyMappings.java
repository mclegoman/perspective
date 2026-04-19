/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.keymappings;

import com.mclegoman.luminance.client.keybindings.KeybindingHelper;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.keymappings.CoreKeyMappings;
import dev.dannytaylor.perspective.lens.LensClient;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class LensKeyMappings extends CoreKeyMappings {
    private static final String category;

    public static final KeyMapping holdZoom;
    public static final KeyMapping toggleZoom;

    public static void onInitializeClient(PerspectiveMod mod) {
    }

    static {
        category = "zoom";
        holdZoom = KeybindingHelper.getKeybinding(LensClient.getMod().getId(true), category, getKey(category, "hold"), GLFW.GLFW_KEY_C);
        toggleZoom = KeybindingHelper.getKeybinding(LensClient.getMod().getId(true), category, getKey(category, "toggle"), GLFW.GLFW_KEY_M);
    }
}