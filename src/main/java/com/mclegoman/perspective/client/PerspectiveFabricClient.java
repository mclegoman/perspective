/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client;

import net.fabricmc.api.ClientModInitializer;

public class PerspectiveFabricClient implements ClientModInitializer {
    public void onInitializeClient() {
        PerspectiveClient.init();
    }
}