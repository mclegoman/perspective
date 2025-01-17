/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common.data;

import com.mclegoman.luminance.api.mod.ModHelper;
import com.mclegoman.luminance.common.util.Version;

public class Data extends com.mclegoman.luminance.common.data.Data {
	private static final Version version = Version.parse(ModHelper.getModContainer("perspective").get().metadata(), "6CTGnrNg");
	public static Version getVersion() {
		return version;
	}
}