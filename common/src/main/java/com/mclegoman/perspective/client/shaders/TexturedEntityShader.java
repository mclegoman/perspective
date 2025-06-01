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
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class TexturedEntityShader implements SpectatorHandler {
	public static final Identifier random;
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
	static {
		random = Identifier.of(Data.getVersion().getID(), "random");
	}
}
