/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.google.gson.JsonObject;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public record ShaderPack(Translation translation, List<Shader> shaders, JsonObject customData) {
	public record Translation(boolean isTranslatable, Identifier id, boolean isShaderPack, boolean description) {
		public Translation(Identifier id) {
			this(false, id, true, false);
		}
		public Text getTranslation(boolean showNamespace, boolean description) {
			return (isShaderPack ? Text.translatable(Data.getVersion().getID() + ".shader_pack." + id.getNamespace() + "." + id.getPath() + (description ? ".description" : "")) : com.mclegoman.perspective.client.translation.Translation.getShaderText(id, description, isTranslatable, showNamespace));
		}
		public Text getTranslation(boolean showNamespace) {
			return getTranslation(showNamespace, false);
		}
		public Text getDescription(boolean showNamespace) {
			return getTranslation(showNamespace, true);
		}
	}
	public record Shader(Identifier registry, Identifier luminance, List<Uniform> uniforms) {
	}
	public record Uniform(Identifier postEffect, String id, List<Float> values, List<String> overrides) {
	}
}
