/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.JsonDataLoader;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShaderPackDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	public static final String id = "perspective/shader_packs";
	public ShaderPackDataLoader() {
		super(new Gson(), id);
	}
	@Override
	public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		try {
			SuperSecretSettings.resetRegistry();
			prepared.forEach((identifier, jsonElement) -> {
				try {
					JsonObject reader = jsonElement.getAsJsonObject();
					List<ShaderPack.Shader> shaders = new ArrayList<>();
					for (JsonElement element : JsonHelper.getArray(reader, "shaders", new JsonArray())) {
						if (element instanceof JsonObject shaderData) shaders.add(new ShaderPack.Shader(Identifier.of(JsonHelper.getString(shaderData, "registry", Shaders.getMainRegistryId().toString())), Identifier.of(JsonHelper.getString(shaderData, "luminance_id"))));
					}
					SuperSecretSettings.addToRegistry(identifier, new ShaderPack.Translation(JsonHelper.getBoolean(reader, "translatable", false), identifier, true), shaders);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to load shader pack: {}", error));
				}
			});
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to apply shader packs dataloader: {}", error));
		}
	}
	@Override
	public Identifier getFabricId() {
		return Identifier.of(Data.getVersion().getID(), id);
	}
}
