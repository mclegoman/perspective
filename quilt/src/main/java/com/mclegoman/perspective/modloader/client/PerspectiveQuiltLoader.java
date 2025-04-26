/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader.client;

import com.mclegoman.perspective.client.PerspectiveClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.client.ClientModInitializer;

public class PerspectiveQuiltLoader implements ClientModInitializer {
    public void onInitializeClient(ModContainer mod) {
        PerspectiveClient.init();
    }
}