/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.util.JsonResourceReloader;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TexturedEntityDataReloader extends JsonResourceReloader {
	private static final Map<Identifier, TexturedEntityEntry> registry = new HashMap<>();
	public static List<TexturedEntityEntry> getRegistry() {
		return registry.values().stream().toList();
	}
	public static List<TexturedEntityEntry> getSortedRegistry() {
		// This should only be used if the list needs to be sorted alphabetically.
		Map<String, TexturedEntityEntry> unprocessedRegistry = new HashMap<>();
		List<TexturedEntityEntry> processedRegistry = new ArrayList<>();
		for (TexturedEntityEntry data : getRegistry()) unprocessedRegistry.put(data.getType() + data.getNamespace() + data.getName().toLowerCase(), data);
		for (String name : new TreeSet<>(unprocessedRegistry.keySet())) processedRegistry.add(unprocessedRegistry.get(name));
		return processedRegistry;
	}
	public static Map<Identifier, TexturedEntityEntry> getRegistryMap() {
		return registry;
	}
	public static final String identifier = "textured_entity";
	public static boolean isReady;
	public TexturedEntityDataReloader() {
		super(new Gson(), identifier);
	}
	private TexturedEntityEntry data(String namespace, String type, String name, JsonObject entity_specific, JsonArray overrides, boolean flip, boolean item_group, Identifier itemModel, boolean canBeRandom, @Nullable TexturedEntityEntry.SpectatorShader shaderPack, boolean enabled) {
		return new TexturedEntityEntry(namespace, type, name, entity_specific, overrides, flip, item_group, itemModel, canBeRandom, shaderPack, enabled);
	}
	private void add(Identifier id, String namespace, String type, String name, JsonObject entity_specific, JsonArray overrides, boolean flip, boolean item_group, Identifier itemModel, boolean canBeRandom, @Nullable TexturedEntityEntry.SpectatorShader shaderPack, boolean enabled) {
		try {
			registry.put(id, data(namespace, type, name, entity_specific, overrides, flip, item_group, itemModel, canBeRandom, shaderPack, enabled));
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to add textured entity to registry: {}", error));
		}
	}
	private void reset() {
		try {
			registry.clear();
			// We add defaults so the random textured entity can use the default texture.
			addDefaultTexturedEntities("minecraft", new String[]{
					"allay",
					"armor_stand",
					"arrow",
					"axolotl",
					"bat",
					"bee",
					"blaze",
					"chest_boat",
					"boat",
					"breeze",
					"breeze_wind_charge",
					"camel",
					"cat",
					"cave_spider",
					"chicken",
					"cod",
					"cow",
					"creeper",
					"dolphin",
					"mule",
					"donkey",
					"dragon_fireball",
					"drowned",
					"elder_guardian",
					"end_crystal",
					"enderman",
					"endermite",
					"evoker",
					"evoker_fangs",
					"experience_orb",
					"firework_rocket",
					"fox",
					"frog",
					"ghast",
					"giant",
					"glow_squid",
					"goat",
					"guardian",
					"hoglin",
					"horse",
					"husk",
					"illusioner",
					"iron_golem",
					"leash_knot",
					"trader_llama",
					"llama",
					"llama_spit",
					"magma_cube",
					"chest_minecart",
					"command_block_minecart",
					"furnace_minecart",
					"hopper_minecart",
					"spawner_minecart",
					"tnt_minecart",
					"minecart",
					"mooshroom",
					"ocelot",
					"panda",
					"parrot",
					"phantom",
					"pig",
					"piglin",
					"zombified_piglin",
					"piglin_brute",
					"pillager",
					"polar_bear",
					"pufferfish",
					"rabbit",
					"ravager",
					"salmon",
					"sheep",
					"shulker_bullet",
					"shulker",
					"silverfish",
					"skeleton",
					"slime",
					"sniffer",
					"snow_golem",
					"spectral_arrow",
					"spider",
					"squid",
					"stray",
					"strider",
					"tadpole",
					"tnt",
					"trident",
					"tropical_fish",
					"turtle",
					"vex",
					"villager",
					"vindicator",
					"wandering_trader",
					"warden",
					"wind_charge",
					"witch",
					"wither",
					"wither_skeleton",
					"wither_skull",
					"wolf",
					"zoglin",
					"zombie",
					"skeleton_horse",
					"zombie_horse",
					"zombie_villager"
			});
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.WARN, Translation.getString("Failed to reset Textured Entity registry: {}", error));
		}
	}
	public void addDefaultTexturedEntities(String namespace, String[] entityTypes) {
		for (String entity : entityTypes) {
			add(Identifier.of(namespace, entity), namespace, entity, "default", new JsonObject(), new JsonArray(), false, false,null, true, null, false);
		}
	}
	@Override
	public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		try {
			isReady = false;
			reset();
			prepared.forEach(this::layout$perspective);
			sendPlayerMessage();
			isReady = true;
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to apply textured entity dataloader: {}", error));
		}
	}

	private void sendPlayerMessage() {
		// If the player doesn't exist, we don't worry as the problem doesn't occur.
		// This issue relates to the creative inventory.
		if (ClientData.minecraft.player != null && (ClientData.minecraft.player.hasPermissionLevel(2) || ClientData.minecraft.player.isInCreativeMode())) ClientData.minecraft.player.sendMessage(Translation.getTranslation(Data.getVersion().getID(), "textured_entity.creative_tab_issue"), false);
	}

	private void layout$perspective(Identifier identifier, JsonElement jsonElement) {
		try {
			JsonObject reader = jsonElement.getAsJsonObject();
			String entity = JsonHelper.getString(reader, "entity");
			String namespace = entity.contains(":") ? entity.substring(0, entity.lastIndexOf(":")) : "minecraft";
			String type = entity.contains(":") ? entity.substring(entity.lastIndexOf(":") + 1) : entity;
			String name = JsonHelper.getString(reader, "name", "default");
			JsonObject entity_specific = JsonHelper.getObject(reader, "entity_specific", new JsonObject());
			JsonArray overrides = JsonHelper.getArray(reader, "overrides", new JsonArray());
			boolean flip = JsonHelper.getBoolean(reader, "flip", false);
			boolean item_group = JsonHelper.getBoolean(reader, "item_group", true);
			Identifier item_model = Identifier.of(JsonHelper.getString(reader, "item_model", namespace.toLowerCase() + ":" + name.toLowerCase() + "_" + type.toLowerCase() + "_spawn_egg"));
			boolean can_be_random = JsonHelper.getBoolean(reader, "can_be_random", true);
			TexturedEntityEntry.SpectatorShader shaderPack = null;
			if (JsonHelper.hasJsonObject(reader, "shader")) {
				JsonObject shaderData = JsonHelper.getObject(reader, "shader");
				// Priority - defaults to 200
				// vanilla: 0
				// soup: 100
				// perspective: 50-200
				shaderPack = new TexturedEntityEntry.SpectatorShader(Identifier.of(JsonHelper.getString(shaderData, "registry", ShaderPacks.getShadersId().toString())), Identifier.of(JsonHelper.getString(shaderData, "pack")), JsonHelper.getInt(shaderData, "priority", 200));
			}
			boolean enabled = JsonHelper.getBoolean(reader, "enabled", true);
			add(identifier, namespace, type, name, entity_specific, overrides, flip, item_group, item_model, can_be_random, shaderPack, enabled);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to load perspective textured entity: {}", error));
		}
	}
}