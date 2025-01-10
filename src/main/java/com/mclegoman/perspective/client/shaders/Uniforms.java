/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.uniforms.LuminanceUniform;
import com.mclegoman.luminance.client.util.LuminanceIdentifier;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;

public class Uniforms {
	public static void init() {
		SmoothUniforms.init();
		Events.ShaderUniform.register(new LuminanceIdentifier(Data.version.getID(), "zoomMultiplier"), new LuminanceUniform((tickDelta) -> (float) Zoom.getMultiplier()));
	}
	public static void tick() {
		SmoothUniforms.tick();
	}
}
