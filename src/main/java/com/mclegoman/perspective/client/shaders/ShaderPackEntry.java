/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public record ShaderPackEntry(Identifier registry, Translation translation, List<Shader> shaders, JsonObject customData) {
	public ShaderPackEntry(Translation translation, List<Shader> shaders, JsonObject customData) {
		this(ShaderPacks.getShadersId(), translation, shaders, customData);
	}
	public record Translation(Identifier id, boolean isShaderPack) {
		public Translation(Identifier id) {
			this(id, true);
		}
		public Text getTranslation(boolean description, boolean shouldShowNamespace) {
			return (isShaderPack ? Text.translatableWithFallback(Data.getVersion().getID() + ".shader_pack." + id.getNamespace() + "." + id.getPath() + (description ? ".description" : ""), description ? "" : com.mclegoman.perspective.client.translation.Translation.getString((shouldShowNamespace ? id().getNamespace() + ":" : "") + id().getPath())) : com.mclegoman.perspective.client.translation.Translation.getShaderText(id(), shouldShowNamespace, description));
		}
		public Text getTranslation(boolean shouldShowNamespace) {
			return getTranslation(false, shouldShowNamespace);
		}
		public Text getDescription(boolean shouldShowNamespace) {
			return getTranslation(true, shouldShowNamespace);
		}
	}
	public record Shader(Identifier registry, Identifier luminance, List<Uniform> uniforms) {
	}
	public record Uniform(Identifier postEffect, String id, List<Float> values, List<String> overrides) {
	}
}
