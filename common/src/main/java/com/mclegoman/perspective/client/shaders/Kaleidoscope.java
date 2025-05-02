/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;


import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.events.PerspectiveEvents;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class Kaleidoscope {
	private static Identifier shaderPack;
	public static void init() {
		PerspectiveEvents.AfterClientResourceReload.register(getId(), Kaleidoscope::apply);
		PerspectiveEvents.OnStartItemUse.register(getId(), (stack, world, player, hand) -> {
			if (stack.isOf(Items.SPYGLASS)) apply();
		});
	}
	protected static void apply() {
		PerspectiveEvents.ShaderRender.register(getId(), new ArrayList<>());
		PerspectiveEvents.ShaderRender.modify(getId(), ShaderPacks.getShaders(Kaleidoscope::getShaderPack, Kaleidoscope::getRenderType, Kaleidoscope::getEnabled));
	}
	public static Identifier getShaderPack() {
		shaderPack = ShaderPacks.randomize(shaderPack);
		return shaderPack;
	}
	public static Shader.RenderType getRenderType() {
		return Shader.RenderType.WORLD;
	}
	public static boolean getEnabled() {
		return PerspectiveConfig.config.kaleidoscope.value() && (ClientData.minecraft.player != null && ClientData.minecraft.player.isUsingSpyglass());
	}
	public static Identifier getId() {
		return Identifier.of(Data.getVersion().getID(), "kaleidoscope");
	}
}
