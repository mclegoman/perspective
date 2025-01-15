/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config.value;

import fabric.com.mclegoman.luminance.client.shaders.Shader;
import org.quiltmc.config.api.values.ConfigSerializableObject;

@SuppressWarnings("unused")
public enum ShaderRenderType implements ConfigSerializableObject<String> {
	game(Shader.RenderType.WORLD),
	screen(Shader.RenderType.GAME);
	private final Shader.RenderType renderType;
	ShaderRenderType(Shader.RenderType renderType) {
		this.renderType = renderType;
	}
	public Shader.RenderType getRenderType() {
		return this.renderType;
	}
	public ShaderRenderType convertFrom(String representation) {
		return valueOf(representation);
	}
	public String getRepresentation() {
		return this.name();
	}
	public ShaderRenderType copy() {
		return this;
	}
}