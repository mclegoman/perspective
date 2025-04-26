/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader.client;

import com.mclegoman.perspective.client.PerspectiveClient;
import net.fabricmc.api.ClientModInitializer;

public class PerspectiveFabricLoader implements ClientModInitializer {
    public void onInitializeClient() {
        PerspectiveClient.init();
    }
}