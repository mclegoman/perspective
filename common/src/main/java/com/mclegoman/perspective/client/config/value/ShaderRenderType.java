/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config.value;

import com.mclegoman.luminance.client.shaders.Shader;
import org.quiltmc.config.api.values.ConfigSerializableObject;

@SuppressWarnings("unused")
public enum ShaderRenderType implements ConfigSerializableObject<String> {
	game(Shader.RenderType.WORLD),
	ui(Shader.RenderType.UI);
	private final Shader.RenderType renderType;
	ShaderRenderType(Shader.RenderType renderType) {
		this.renderType = renderType;
	}
	public Shader.RenderType getRenderType() {
		return this.renderType;
	}
	public ShaderRenderType convertFrom(String representation) {
		// We check for true/false strings in case the user has updated from config version 6.
		// (We also check for "screen" for users that updated from config version 24)
		if (representation.equalsIgnoreCase("false")) return game;
		else if (representation.equalsIgnoreCase("true") || representation.equalsIgnoreCase("screen")) return ui;
		else return valueOf(representation);
	}
	public String getRepresentation() {
		return this.name();
	}
	public ShaderRenderType copy() {
		return this;
	}
}