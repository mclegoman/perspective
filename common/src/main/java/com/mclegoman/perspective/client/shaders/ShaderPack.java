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

import java.util.ArrayList;
import java.util.List;

public record ShaderPack(Translation translation, List<Shader> shaders, JsonObject customData) {
	public ShaderPack(Translation translation) {
		this(translation, new ArrayList<>(), new JsonObject());
	}
	public ShaderPack(Translation translation, JsonObject customData) {
		this(translation, new ArrayList<>(), customData);
	}
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
	public record Shader(Identifier registry, Identifier luminanceId, List<Uniform> uniforms) {
		public Shader(Identifier registry, Identifier luminance) {
			this(registry, luminance, new ArrayList<>());
		}
		public Shader(Identifier luminanceId) {
			this(luminanceId, new ArrayList<>());
		}
		public Shader(Identifier luminanceId, List<Uniform> uniforms) {
			this(Shaders.getMainRegistryId(), luminanceId, uniforms);
		}
	}
	public record Uniform(String id, String value) {
	}
}
