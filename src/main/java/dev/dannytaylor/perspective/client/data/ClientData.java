/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.data;

import com.mclegoman.luminance.client.config.LuminanceConfig;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;

public class ClientData {
    public static final Minecraft minecraft = Minecraft.getInstance();

    public static boolean isDevelopment() {
        return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment() || LuminanceConfig.config.debug.value();
    }
}