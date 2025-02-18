/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record ShaderPack(Translation translation, List<Shader> shaders) {
	public ShaderPack(Translation translation) {
		this(translation, new ArrayList<>());
	}
	public record Translation(boolean isTranslatable, Identifier id, boolean isShaderPack) {
		public Translation(Identifier id) {
			this(false, id, true);
		}
		public Text getTranslation() {
			return isTranslatable ? (isShaderPack ? Text.translatable(Data.getVersion().getID() + ".shader_pack." + id.getNamespace() + "." + id.getPath()) : Text.translatable("shader." + id.getNamespace() + "." + id.getPath())) : Text.literal(id.getPath());
		}
	}
	public record Shader(Identifier registry, Identifier luminanceId) {
		public Shader(Identifier luminanceId) {
			this(Shaders.getMainRegistryId(), luminanceId);
		}
	}
}
