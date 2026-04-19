/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.shaders;

import com.mclegoman.luminance.client.events.Runnables;
import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderTime;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.shaders.Uniforms;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.ui.UserInterfaceClient;
import dev.dannytaylor.perspective.ui.events.UserInterfaceEvents;

public class UserInterfaceShaders extends RenderLocations {
    public static RenderLocation<Runnables.GameRender.Data> BLUR = register(UserInterfaceClient.idOf("blur"), Shaders::renderFromGameData, RenderLocations.DepthType.MAIN, UIType.UNDER, false);

    public static void onInitializeClient(PerspectiveMod mod) {
        UserInterfaceEvents.onInitialize(mod, "Shaders", () -> {
            Uniforms.registerSingleValueTree(UserInterfaceClient.getMod().getId(true), "blur_radius", UserInterfaceShaders::getMenuBackgroundBlurriness, null, null);
        });
    }

    public static float getMenuBackgroundBlurriness(ShaderTime shaderTime) {
        return ClientData.minecraft.options.getMenuBackgroundBlurriness() * 2.0F;
    }
}
