/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.data;

import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;

public class ClientData {
    public static final Minecraft minecraft = Minecraft.getInstance();

    public static boolean isDevelopment() {
        // TODO: config.
        return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment();// || PerspectiveConfig.config.debug.value();
    }
}