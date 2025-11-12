/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common.data;

import com.mclegoman.luminance.common.util.ModContainer;
import com.mclegoman.luminance.common.util.ModHelper;
import com.mclegoman.luminance.common.util.Version;

import java.util.Collections;
import java.util.Optional;

public class Data extends com.mclegoman.luminance.common.data.Data {
	private static final Version version;
	public static Version getVersion() {
		return version;
	}
	static {
		Optional<ModContainer> modContainer = ModHelper.getModContainer("perspective");
		version = Version.parse(modContainer.isPresent() ? modContainer.get().metadata() : new ModContainer.ModMetadata("perspective", "0.0.0-release.0", "Perspective", "metadata could not be found!", Collections.emptyList(), Collections.emptyList()), "6CTGnrNg");
	}
}