/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.modloader;

import com.mclegoman.perspective.common.Perspective;
import quilt.com.mclegoman.luminance.entrypoint.LuminanceInit;

public class PerspectiveQuiltLoader implements LuminanceInit {
	public void init(String modId) {
		Perspective.init();
	}
}
