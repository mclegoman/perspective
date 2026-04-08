/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.keymappings;

import com.mclegoman.luminance.client.keybindings.KeybindingHelper;
import com.mclegoman.luminance.client.translation.Translation;
import dev.dannytaylor.perspective.common.data.Data;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyMappingRegistry {
    public static void onInitializeClient() {
        Log.info("Initializing key mappings...");
        CameraTypeKeyMappings.onInitializeClient();
        ShaderKeyMappings.onInitializeClient();
        ZoomKeyMappings.onInitializeClient();
    }

    public static class CameraTypeKeyMappings {
        private static final String category;
        public static final KeyMapping adjustMultiplier;

        public static void onInitializeClient() {
            HoldPerspective.onInitializeClient();
            SwapPerspective.onInitializeClient();
        }

        static {
            category = "camera_type";
            adjustMultiplier = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "adjust_multiplier"), GLFW.GLFW_KEY_R);
        }

        public static class HoldPerspective {
            private static final String category;

            public static final KeyMapping thirdPersonBack;
            public static final KeyMapping thirdPersonFront;

            public static void onInitializeClient() {
            }

            static {
                category = getKey(CameraTypeKeyMappings.category, "hold_perspective");
                thirdPersonBack = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "third_person_back"), GLFW.GLFW_KEY_Z);
                thirdPersonFront = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "third_person_front"), GLFW.GLFW_KEY_X);
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
                category = getKey(CameraTypeKeyMappings.category, "swap_perspective");
                firstPerson = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "first_person"), GLFW.GLFW_KEY_KP_1);
                thirdPersonBack = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "third_person_back"), GLFW.GLFW_KEY_KP_2);
                thirdPersonFront = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "third_person_front"), GLFW.GLFW_KEY_KP_3);
            }
        }
    }

    public static class ShaderKeyMappings {
        private static final String category;

        public static void onInitializeClient() {
            SuperSecretSettingsKeyMappings.onInitializeClient();
        }

        static {
            category = "shaders";
        }

        public static class SuperSecretSettingsKeyMappings {
            private static final String category;

            public static final KeyMapping cycleShader;
            public static final KeyMapping cycleRenderLocation;
            public static final KeyMapping holdShader;
            public static final KeyMapping toggleShader;

            public static void onInitializeClient() {
            }

            static {
                category = getKey(ShaderKeyMappings.category, "super_secret_settings");
                cycleShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "cycle_shader"), GLFW.GLFW_KEY_UNKNOWN);
                cycleRenderLocation = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "cycle_render_location"), GLFW.GLFW_KEY_UNKNOWN);
                holdShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "hold_shader"), GLFW.GLFW_KEY_UNKNOWN);
                toggleShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "toggle_shader"), GLFW.GLFW_KEY_UNKNOWN);
            }
        }
    }

    public static class ZoomKeyMappings {
        private static final String category;

        public static final KeyMapping holdZoom;
        public static final KeyMapping toggleZoom;

        public static void onInitializeClient() {
        }

        static {
            category = "zoom";
            holdZoom = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "hold"), GLFW.GLFW_KEY_C);
            toggleZoom = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "toggle"), GLFW.GLFW_KEY_M);
        }
    }

    public static String getKey(String category, String key) {
        return Translation.getString("{}.{}", category, key);
    }
}
