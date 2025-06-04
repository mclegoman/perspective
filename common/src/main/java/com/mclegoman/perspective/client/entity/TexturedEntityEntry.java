/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TexturedEntityEntry {
	private final String namespace;
	private final String type;
	private final String name;
	private final JsonObject entity_specific;
	private final JsonArray overrides;
	private final boolean flip;
	private final boolean item_group;
	private final Identifier item_model;
	private final boolean canBeRandom;
	private final boolean overrideLookingAtVariant;
	private final SpectatorShader shaderPack;
	private final boolean enabled;
	public TexturedEntityEntry(String namespace, String type, String name, JsonObject entity_specific, JsonArray overrides, boolean flip, boolean item_group, Identifier item_model, boolean canBeRandom, boolean overrideLookingAtVariant, @Nullable SpectatorShader shaderPack, boolean enabled) {
		this.namespace = namespace;
		this.type = type;
		this.name = name;
		this.entity_specific = entity_specific;
		this.overrides = overrides;
		this.flip = flip;
		this.item_group = item_group;
		this.item_model = item_model;
		this.canBeRandom = canBeRandom;
		this.overrideLookingAtVariant = overrideLookingAtVariant;
		this.shaderPack = shaderPack;
		this.enabled = enabled;
	}
	public String getNamespace() {
		return this.namespace;
	}
	public String getType() {
		return this.type;
	}
	public String getName() {
		return this.name;
	}
	public JsonObject getEntitySpecific() {
		return this.entity_specific;
	}
	public JsonArray getOverrides() {
		return this.overrides;
	}
	public boolean getFlip() {
		return this.flip;
	}
	public boolean getItemGroup() {
		return this.item_group;
	}
	public Identifier getItemModel() {
		return this.item_model;
	}
	public boolean getEnabled() {
		return this.enabled;
	}
	public boolean getCanBeRandom() {
		return this.canBeRandom;
	}
	public boolean getOverrideLookingAtVariant() {
		return this.overrideLookingAtVariant;
	}
	public SpectatorShader getShaderPack() {
		return this.shaderPack;
	}
	public record SpectatorShader(Identifier registry, Identifier shaderPack, int priority) {
		public SpectatorShader(Identifier shaderPack, int priority) {
			this(ShaderPacks.getShadersId(), shaderPack, priority);
		}
	}
}
