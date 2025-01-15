/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.logo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fabric.com.mclegoman.luminance.client.util.JsonDataLoader;
import fabric.com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.events.Halloween;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SplashesDataloader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	public static final List<Translation.Data> registry = new ArrayList<>();
	public static final String id = "splashes";
	private static Translation.Data splashText;
	public static Translation.Data getSplashText() {
		if (PerspectiveLogo.isPerspectiveBirthday()) return new Translation.Data("splashes.perspective.special.birthday", true);
		else if (PerspectiveLogo.isActuallyPride()) return new Translation.Data("splashes.perspective.special.pride_month", true);
		else if (AprilFoolsPrank.isAprilFools() && !AprilFoolsPrank.isForceAprilFools()) return new Translation.Data("splashes.perspective.special.april_fools", true);
		else if (Halloween.isHalloween() && !Halloween.isForceHalloween()) return new Translation.Data("splashes.perspective.special.halloween", true);
		else return splashText;
	}
	public static void randomizeSplashText() {
		if (registry.size() > 1) {
			List<Translation.Data> splashes = new ArrayList<>(registry);
			if (getSplashText() != null) splashes.remove(getSplashText());
			splashText = splashes.get(new Random().nextInt(splashes.size()));
		} else {
			if (registry.size() == 1) splashText = registry.getFirst();
			else splashText = new Translation.Data("", false);
		}
	}
	public SplashesDataloader() {
		super(new Gson(), id);
	}
	private void add(String text, Boolean translatable) {
		try {
			Translation.Data splash = new Translation.Data(text, translatable);
			if (!registry.contains(splash)) registry.add(splash);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to add splash text to registry: {}", error));
		}
	}
	private void reset() {
		try {
			registry.clear();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to reset splash text registry: {}", error));
		}
	}
	@Override
	public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		try {
			reset();
			for (Resource resource : manager.getAllResources(Identifier.of(Data.version.getID(), id + ".json"))) {
				JsonObject reader = JsonHelper.deserialize(resource.getReader());
				if (JsonHelper.getBoolean(reader, "replace", false)) reset();
				JsonArray translatableTexts = JsonHelper.getArray(reader, "translatable");
				for (JsonElement splashText : translatableTexts) add(splashText.getAsString(), true);
				JsonArray literalTexts = JsonHelper.getArray(reader, "literal");
				for (JsonElement splashText : literalTexts) add(splashText.getAsString(), false);
			}
			randomizeSplashText();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to load splash text from dataloader: {}", error));
		}
	}
	@Override
	public Identifier getFabricId() {
		return Identifier.of(Data.version.getID(), id);
	}
}