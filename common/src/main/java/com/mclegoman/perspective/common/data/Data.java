/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common.data;

import fabric.com.mclegoman.luminance.common.util.Version;
import net.fabricmc.loader.api.FabricLoader;

public class Data extends fabric.com.mclegoman.luminance.common.data.Data {
	public static final Version version = Version.parse(FabricLoader.getInstance().getModContainer("perspective").get().getMetadata(), "6CTGnrNg");
}