/*
    Appearance
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Appearance
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.appearance;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.util.IdentifierHelper;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.Reader;
import java.util.*;

public class Appearance {
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new DataLoader());
	}
	public static class DataLoader extends SinglePreparationResourceReloader<Map<Identifier, JsonElement>> implements IdentifiableResourceReloadListener {
		public static final List<Data> registry = new ArrayList<>();
		private final Gson gson;
		private final String dataType;

		public DataLoader() {
			this.gson = new Gson();
			this.dataType = "appearance";
		}

		private void add(String uuid, boolean slim, boolean replace, Identifier skinTexture) {
			try {
				Data data = new Data(UUID.fromString(uuid), slim ? SkinTextures.Model.SLIM : SkinTextures.Model.WIDE, replace, skinTexture);
				if (!registry.contains(data)) registry.add(data);
			} catch (Exception error) {
				com.mclegoman.perspective.common.data.Data.version.sendToLog(LogType.ERROR, "Failed to add '" + uuid + "' to appearance registry: " + error);
			}
		}

		public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
			try {
				registry.clear();
				prepared.forEach(this::layout$perspective);
			} catch (Exception error) {
				com.mclegoman.perspective.common.data.Data.version.sendToLog(LogType.ERROR, "Failed to apply appearance dataloader: " + error);
			}
		}

		@Override
		public Identifier getFabricId() {
			return Identifier.of(com.mclegoman.perspective.common.data.Data.version.getID(), this.dataType);
		}

		private void layout$perspective(Identifier identifier, JsonElement jsonElement) {
			JsonObject reader = jsonElement.getAsJsonObject();
			String skin = JsonHelper.getString(reader, "texture", identifier.getNamespace() + ":textures/appearance/" + identifier.getPath());
			boolean replace = JsonHelper.getBoolean(reader, "replace", !skin.isEmpty());
			add(JsonHelper.getString(reader, "uuid", identifier.getPath()), JsonHelper.getBoolean(reader, "slim", false), replace, IdentifierHelper.identifierFromString(skin, com.mclegoman.perspective.common.data.Data.version.getID()));
		}

		protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, Profiler profiler) {
			Map<Identifier, JsonElement> map = new HashMap<>();
			load(resourceManager, this.dataType, this.gson, map);
			return map;
		}

		public static void load(ResourceManager manager, String dataType, Gson gson, Map<Identifier, JsonElement> results) {
			ResourceFinder resourceFinder = ResourceFinder.json(dataType);
			for (Map.Entry<Identifier, Resource> resourceEntry : resourceFinder.findResources(manager).entrySet()) {
				Identifier resourceEntryKey = resourceEntry.getKey();
				Identifier resourceId = resourceFinder.toResourceId(resourceEntryKey);
				try {
					Reader reader = resourceEntry.getValue().getReader();
					try {
						JsonElement jsonElement = JsonHelper.deserialize(gson, reader, JsonElement.class);
						JsonElement jsonElement2 = results.put(resourceId, jsonElement);
						if (jsonElement2 != null) {
							throw new IllegalStateException("Duplicate data file ignored with ID " + resourceId);
						}
					} catch (Throwable throwable) {
						if (reader != null) {
							try {
								reader.close();
							} catch (Throwable var12) {
								throwable.addSuppressed(var12);
							}
						}
						throw throwable;
					}
					reader.close();
				} catch (Exception error) {
					com.mclegoman.perspective.common.data.Data.version.sendToLog(LogType.ERROR, "Couldn't parse data file '" + resourceId + "' from '" + resourceEntryKey + "': " + error);
				}
			}
		}
	}
	public record Data(UUID uuid, SkinTextures.Model model, boolean replace, Identifier texture) {
	}
}
