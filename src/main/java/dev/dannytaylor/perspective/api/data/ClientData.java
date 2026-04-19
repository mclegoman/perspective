/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.data;

import dev.dannytaylor.perspective.api.config.CoreConfig;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import net.minecraft.client.Minecraft;

public class ClientData {
    public static final Minecraft minecraft = Minecraft.getInstance();

    public static boolean isDevelopment() {
        return FabricLoaderImpl.INSTANCE.isDevelopmentEnvironment() || CoreConfig.instance.debug.value();
    }
}