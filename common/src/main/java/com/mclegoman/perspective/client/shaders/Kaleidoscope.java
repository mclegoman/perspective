/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.events.PerspectiveEvents;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;

public class Kaleidoscope {
	private static Identifier shaderPack;
	public static void init() {
		PerspectiveEvents.AfterClientResourceReload.register(getId(), Kaleidoscope::apply);
		PerspectiveEvents.OnStartItemUse.register(getId(), (stack, world, player, hand) -> {
			if (stack.isOf(Items.SPYGLASS)) apply(stack);
		});
	}
	protected static void apply() {
		apply(ItemStack.EMPTY);
	}
	protected static void apply(ItemStack stack) {
		PerspectiveEvents.ShaderRender.register(getId(), new ArrayList<>());
		PerspectiveEvents.ShaderRender.modify(getId(), ShaderPacks.getShaders(() -> {
			try {
				Callable<Identifier> id = getShader(stack);
				return id != null ? ShaderPacks.getShaderPack(id.call()) : null;
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, "Error getting kaleidoscope shader pack!");
			}
			return null;
		}, Kaleidoscope::getRenderType, Kaleidoscope::getEnabled));
	}
	public static Identifier getShaderPack(ItemStack stack) {
		shaderPack = stack.getCustomName() != null ? set(guessPackId(stack.getCustomName().getString()).orElse(set(shaderPack, true)), false) : set(shaderPack, true);
		return shaderPack;
	}
	public static Shader.RenderType getRenderType() {
		return Shader.RenderType.WORLD;
	}
	public static boolean getEnabled() {
		return shouldBeEnabled() && shaderPack != null;
	}
	public static boolean shouldBeEnabled() {
		return (getEnabledNamed() || getEnabledRandom()) && (ClientData.minecraft.player != null && isUsingSpyglass(ClientData.minecraft.player));
	}
	public static boolean getEnabledRandom() {
		return PerspectiveConfig.config.randomKaleidoscope.value();
	}
	public static boolean getEnabledNamed() {
		return PerspectiveConfig.config.namedKaleidoscope.value();
	}
	public static boolean isUsingSpyglass(PlayerEntity player) {
		return player.isUsingSpyglass() && ClientData.minecraft.options.getPerspective().isFirstPerson();
	}
	public static Identifier getId() {
		return Identifier.of(Data.getVersion().getID(), "kaleidoscope");
	}
	public static Optional<Identifier> guessPackId(@NotNull String id) {
		return ShaderPacks.guessPackId(id.toLowerCase().replace(" ", "_"));
	}
	private static Identifier set(Identifier shaderPack, boolean randomize) {
		return (!randomize && getEnabledNamed()) ? shaderPack : (getEnabledRandom() ? ShaderPacks.randomize(shaderPack) : null);
	}
	private static Callable<Identifier> getShader(ItemStack stack) {
		return shouldBeEnabled() ? () -> getShaderPack(stack) : null;
	}
}
