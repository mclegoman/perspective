/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader;

import com.mclegoman.perspective.common.PerspectiveCommon;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.client.ClientModInitializer;

public class PerspectiveQuiltLoader implements ClientModInitializer {
	public void onInitializeClient(ModContainer mod) {
		PerspectiveCommon.init();
	}
}
