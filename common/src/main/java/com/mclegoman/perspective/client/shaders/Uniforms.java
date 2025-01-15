/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import fabric.com.mclegoman.luminance.common.util.LogType;

import static fabric.com.mclegoman.luminance.client.shaders.Uniforms.registerSingleTree;

public class Uniforms {
	public static void init() {
		try {
			String path = Data.version.getID();
			registerSingleTree(path, "zoomMultiplier", (tickDelta) -> Zoom.getMultiplier(), null, null);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to initialize uniforms: {}", error));
		}
	}
}
