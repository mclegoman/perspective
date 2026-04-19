/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance.keymappings;

import com.mclegoman.luminance.client.keybindings.KeybindingHelper;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.api.keymappings.CoreKeyMappings;
import dev.dannytaylor.perspective.radiance.RadianceClient;
import dev.dannytaylor.perspective.radiance.events.RadianceEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class RadianceKeyMappings extends CoreKeyMappings {
    private static final String category;

    public static void onInitializeClient(PerspectiveMod mod) {
        RadianceEvents.onInitialize(mod, "Key Mappings", () -> {
            SuperSecretSettingsKeyMappings.onInitializeClient(mod);
        });
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

        public static void onInitializeClient(PerspectiveMod mod) {
        }

        static {
            category = getKey(RadianceKeyMappings.category, "super_secret_settings");
            cycleShader = KeybindingHelper.getKeybinding(RadianceClient.getMod().getId(true), category, getKey(category, "cycle_shader"), GLFW.GLFW_KEY_UNKNOWN);
            cycleRenderLocation = KeybindingHelper.getKeybinding(RadianceClient.getMod().getId(true), category, getKey(category, "cycle_render_location"), GLFW.GLFW_KEY_UNKNOWN);
            holdShader = KeybindingHelper.getKeybinding(RadianceClient.getMod().getId(true), category, getKey(category, "hold_shader"), GLFW.GLFW_KEY_UNKNOWN);
            toggleShader = KeybindingHelper.getKeybinding(RadianceClient.getMod().getId(true), category, getKey(category, "toggle_shader"), GLFW.GLFW_KEY_UNKNOWN);
        }
    }
}