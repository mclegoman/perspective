/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader.client;

import com.mclegoman.luminance.api.entrypoint.LuminanceInit;
import com.mclegoman.perspective.client.PerspectiveClient;

public class PerspectiveFabricLoader implements LuminanceInit {
    public void init(String modId) {
        PerspectiveClient.init();
    }
}