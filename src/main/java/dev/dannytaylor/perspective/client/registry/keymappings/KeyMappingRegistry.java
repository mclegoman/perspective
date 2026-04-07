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
        Shaders.onInitializeClient();
    }

    public static class Shaders {
        private static final String category;

        public static void onInitializeClient() {
            SuperSecretSettings.onInitializeClient();
        }

        static {
            category = "shaders";
        }

        public static class SuperSecretSettings {
            private static final String category;

            public static final KeyMapping cycleShader;
            public static final KeyMapping cycleRenderLocation;
            public static final KeyMapping holdShader;
            public static final KeyMapping toggleShader;

            public static void onInitializeClient() {
            }

            static {
                category = getKey(Shaders.category, "super_secret_settings");
                cycleShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "cycle_shader"), GLFW.GLFW_KEY_UNKNOWN);
                cycleRenderLocation = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "cycle_render_location"), GLFW.GLFW_KEY_UNKNOWN);
                holdShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "hold_shader"), GLFW.GLFW_KEY_UNKNOWN);
                toggleShader = KeybindingHelper.getKeybinding(Data.getVersion().getID(), category, getKey(category, "toggle_shader"), GLFW.GLFW_KEY_UNKNOWN);
            }
        }
    }

    public static String getKey(String category, String key) {
        return Translation.getString("{}.{}", category, key);
    }
}
