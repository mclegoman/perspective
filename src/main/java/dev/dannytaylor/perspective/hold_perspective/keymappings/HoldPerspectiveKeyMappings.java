/*
    Hold Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.hold_perspective.keymappings;

import com.mclegoman.luminance.client.keybindings.KeybindingHelper;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.api.keymappings.CoreKeyMappings;
import dev.dannytaylor.perspective.hold_perspective.HoldPerspectiveClient;
import dev.dannytaylor.perspective.hold_perspective.events.HoldPerspectiveEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class HoldPerspectiveKeyMappings extends CoreKeyMappings {
    private static final String category;
    public static final KeyMapping adjustMultiplier;

    public static void onInitializeClient(PerspectiveMod mod) {
        HoldPerspectiveEvents.onInitialize(mod, "Key Mappings", () -> {
            HoldPerspective.onInitializeClient();
            SwapPerspective.onInitializeClient();
        });
    }

    static {
        category = "camera_type";
        adjustMultiplier = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "adjust_multiplier"), GLFW.GLFW_KEY_R);
    }

    public static class HoldPerspective {
        private static final String category;

        public static final KeyMapping thirdPersonBack;
        public static final KeyMapping thirdPersonFront;

        public static void onInitializeClient() {
        }

        static {
            category = getKey(HoldPerspectiveKeyMappings.category, "hold_perspective");
            thirdPersonBack = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "third_person_back"), GLFW.GLFW_KEY_Z);
            thirdPersonFront = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "third_person_front"), GLFW.GLFW_KEY_X);
        }
    }

    public static class SwapPerspective {
        private static final String category;

        public static final KeyMapping firstPerson;
        public static final KeyMapping thirdPersonBack;
        public static final KeyMapping thirdPersonFront;

        public static void onInitializeClient() {
        }

        static {
            category = getKey(HoldPerspectiveKeyMappings.category, "swap_perspective");
            firstPerson = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "first_person"), GLFW.GLFW_KEY_KP_1);
            thirdPersonBack = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "third_person_back"), GLFW.GLFW_KEY_KP_2);
            thirdPersonFront = KeybindingHelper.getKeybinding(HoldPerspectiveClient.getMod().getId(true), category, getKey(category, "third_person_front"), GLFW.GLFW_KEY_KP_3);
        }
    }
}