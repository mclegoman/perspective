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

public class TexturedEntityShader implements SpectatorHandler {
	public int getPriority(Entity entity) {
		TexturedEntityEntry.SpectatorShader spectatorShader = TexturedEntity.getShaderPack(entity);
		return !TexturedEntity.getShaders(entity, TexturedEntity.getShaderPack(entity)).isEmpty() && spectatorShader != null ? spectatorShader.priority() : -1;
	}
	public void apply(Entity entity) {
		TexturedEntity.applyShader(entity);
	}
	public void clear() {
		TexturedEntity.clearShader();
	}
}
