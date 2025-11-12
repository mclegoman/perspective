/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader;

import com.mclegoman.perspective.common.PerspectiveCommon;
import net.fabricmc.api.ModInitializer;

public class PerspectiveFabricLoader implements ModInitializer {
	public void onInitialize() {
		PerspectiveCommon.init();
	}
}
