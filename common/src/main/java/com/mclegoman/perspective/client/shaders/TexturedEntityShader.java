/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.shaders.SpectatorHandler;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import net.minecraft.entity.Entity;

import java.util.Optional;

public class TexturedEntityShader implements SpectatorHandler {
	public int getPriority(Entity entity) {
		Optional<TexturedEntityEntry> texturedEntity = TexturedEntity.getEntity(entity);
		if (texturedEntity.isPresent()) {
			Optional<TexturedEntityEntry.SpectatorShader> shaderPack = texturedEntity.get().getShaderPack();
			if (shaderPack.isPresent()) {
				if (ShaderPacks.exists(shaderPack.get().registry(), shaderPack.get().shaderPack())) {
					return shaderPack.get().priority();
				}
			}
		}
		return -1;
	}
	public void apply(Entity entity) {
		TexturedEntity.applyShader(entity);
	}
	public void clear() {
		TexturedEntity.clearShader();
	}
}
