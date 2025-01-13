/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.shaders.uniforms.LuminanceUniform;
import com.mclegoman.luminance.client.util.LuminanceIdentifier;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;

public class SmoothUniforms extends Uniforms {
	public static float prevZoom = Zoom.getMultiplier();
	public static float zoom = Zoom.getMultiplier();
	public static void init() {
		Events.ShaderUniform.register(new LuminanceIdentifier(Data.version.getID(), "zoomMultiplierSmooth"), new LuminanceUniform((tickDelta) -> Shaders.getSmooth(tickDelta, prevZoom, zoom)));
	}
	public static void tick() {
		prevZoom = zoom;
		zoom = (prevZoom + Zoom.getMultiplier()) * 0.5F;
	}
}
