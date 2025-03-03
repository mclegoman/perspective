/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.events;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.util.JsonDataLoader;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AprilFoolsPrankDataLoader extends JsonDataLoader {
	public static final Map<String, PrankData> registry = new HashMap<>();
	public static final String ID = "prank";
	public AprilFoolsPrankDataLoader() {
		super(new Gson(), ID);
	}
	private void add(List<Identifier> textures, boolean isSlim, String contributor) {
		try {
			registry.put(contributor + (isSlim ? "_slim" : "_wide"), new PrankData(textures, isSlim, contributor));
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to add april fools prank to registry: {}", error));
		}
	}
	private void addSkins(JsonArray textureIds, boolean isSlim, String contributor) {
		List<Identifier> textures = new ArrayList<>();
		for (JsonElement skin : textureIds) {
			String id = skin.getAsString();
			String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id);
			String texture = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, id);
			if (namespace != null && texture != null) {
				texture = texture.toLowerCase();
				texture = !texture.startsWith("textures/") ? "textures/" + texture : texture;
				texture = !texture.endsWith(".png") ? texture + ".png" : texture;
				textures.add(Identifier.of(namespace, texture));
			}
		}
		if (!textures.isEmpty()) add(textures, isSlim, contributor);
	}
	private void reset() {
		try {
			registry.clear();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to reset april fools prank registry: {}", error));
		}
	}
	@Override
	public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		try {
			reset();
			for (Resource resource : manager.getAllResources(Identifier.of(Data.getVersion().getID(), ID + ".json"))) {
				JsonObject reader = JsonHelper.deserialize(resource.getReader());
				if (JsonHelper.getBoolean(reader, "replace", false)) reset();
				JsonArray values = JsonHelper.getArray(reader, "values", new JsonArray());
				for (JsonElement element : values) {
					if (element instanceof JsonObject value) {
						String contributor = JsonHelper.getString(value, "contributor", "772eb47b-a24e-4d43-a685-6ca9e9e132f7");
						JsonObject skins = JsonHelper.getObject(value, "skins", new JsonObject());
						addSkins(JsonHelper.getArray(skins, "slim", new JsonArray()), true, contributor);
						addSkins(JsonHelper.getArray(skins, "wide", new JsonArray()), false, contributor);
					}
				}
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to load prank values: {}", error));
		}
	}
	public record PrankData(List<Identifier> textures, boolean isSlim, String contributor) {
	}
}