/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.shaders.SpectatorHandler;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class TexturedEntityShader implements SpectatorHandler {
	public static final Identifier random;
	public int getPriority(Entity entity) {
		Optional<TexturedEntityEntry> texturedEntity = TexturedEntity.getEntity(entity);
		if (texturedEntity.isPresent()) {
			TexturedEntityEntry.SpectatorShader shaderPack = texturedEntity.get().getShaderPack();
			if (shaderPack != null) {
				if (ShaderPacks.exists(shaderPack.registry(), shaderPack.shaderPack()) || shaderPack.shaderPack().equals(random)) {
					return shaderPack.priority();
				} else Data.getVersion().sendToLog(LogType.WARN, "Could not locate the current shader pack!: " + shaderPack.registry() + ":" + shaderPack.shaderPack());
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
	static {
		random = Identifier.of(Data.getVersion().getID(), "random");
	}
}
