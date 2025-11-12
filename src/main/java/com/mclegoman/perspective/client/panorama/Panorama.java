/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.panorama;

import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.SharedConstants;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.Callable;

public class Panorama {
	private static final Map<String, Callable<Boolean>> incompatible = new HashMap<>();

	public static void addIncompatibleCallable(String id, Callable<Boolean> callable) {
		if (!incompatible.containsKey(id)) incompatible.put(id, callable);
	}
	public static void addIncompatibleMod(String modID) {
		addIncompatibleCallable(modID, () -> Data.isModInstalled(modID));
	}

	public static void init() {
		addIncompatibleMod("canvas");
		addIncompatibleCallable("iris_shaders_enabled", CompatHelper::isIrisShadersEnabled);
	}
	private static boolean isCompatible() {
		boolean compatible = true;
		for (Callable<Boolean> value : incompatible.values()) {
			try {
				if (value.call()) {
					compatible = false;
					break;
				}
			} catch (Exception error) {
				compatible = false;
				break;
			}
		}
		return compatible;
	}

	public static List<String> getIncompatible() {
		List<String> incompatibleFound = new ArrayList<>();
		incompatible.forEach((id, callable) -> {
			try {
				if (callable.call()) incompatibleFound.add(id);
			} catch (Exception error) {
				incompatibleFound.add(id);
			}
		});
		return incompatibleFound;
	}

	public static void tick() {
		if (Keybindings.takePanoScreenshot.wasPressed()) takePanorama(1024, 0.0F);
	}

	private static String getFilename() {
		String currentTime = Util.getFormattedCurrentTime();
		String filename = currentTime;
		int i = 1;
		boolean shouldReturn = false;
		while (!shouldReturn) {
			String filename1 = currentTime + (i == 1 ? "" : "_" + i);
			File file = new File(ClientData.minecraft.runDirectory.getPath() + "/resourcepacks/", filename1);
			if (!file.exists()) {
				filename = filename1;
				shouldReturn = true;
			}
			i++;
		}
		return filename;
	}

	private static boolean shouldTakePanorama() {
		return PerspectiveConfig.config.debug.value() || isCompatible();
	}

	private static void takePanorama(int resolution, float startingYaw) {
		try {
			if (shouldTakePanorama()) {
				if (ClientData.minecraft.player != null) {
					float playerPitch = ClientData.minecraft.player.getPitch();
					float playerYaw = ClientData.minecraft.player.getYaw();

					int framebufferWidth = ClientData.minecraft.getWindow().getFramebufferWidth();
					int framebufferHeight = ClientData.minecraft.getWindow().getFramebufferHeight();
					Framebuffer framebuffer = ClientData.minecraft.getFramebuffer();

					Perspective playerPerspective = ClientData.minecraft.options.getPerspective();
					if (!playerPerspective.isFirstPerson()) ClientData.minecraft.options.setPerspective(Perspective.FIRST_PERSON);

					ClientData.minecraft.gameRenderer.setBlockOutlineEnabled(false);
					ClientData.minecraft.gameRenderer.setRenderingPanorama(true);

					String panoramaName = getFilename();
					File resourcePackDir = new File(ClientData.minecraft.runDirectory.getPath() + "/resourcepacks/" + panoramaName);
					File screenshotsDir = new File(resourcePackDir + "/assets/minecraft/textures/gui/title/background");

					if (screenshotsDir.mkdirs()) {
						// Pre-render world
						for (int l = 0; l < 100; ++l) {
							framebuffer.beginWrite(true);
							ClientData.minecraft.gameRenderer.renderWorld(RenderTickCounter.ONE);
						}
						// Create pack.png
						ClientData.minecraft.getWindow().setFramebufferWidth(64);
						ClientData.minecraft.getWindow().setFramebufferHeight(64);
						framebuffer.resize(64, 64);
						framebuffer.beginWrite(true);
						ClientData.minecraft.gameRenderer.renderWorld(RenderTickCounter.ONE);
						ScreenshotRecorder.saveScreenshot(resourcePackDir, "pack.png", ClientData.minecraft.getFramebuffer());

						ClientData.minecraft.getWindow().setFramebufferWidth(resolution);
						ClientData.minecraft.getWindow().setFramebufferHeight(resolution);
						framebuffer.resize(resolution, resolution);

						// Create panoramic screenshots
						for (int l = 0; l < 6; ++l) {
							switch (l) {
								case 0 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 0.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(0.0F);
								}
								case 1 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 90.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(0.0F);
								}
								case 2 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 180.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(0.0F);
								}
								case 3 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 270.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(0.0F);
								}
								case 4 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 0.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(-90.0F);
								}
								case 5 -> {
									ClientData.minecraft.player.setYaw((startingYaw + 0.0F) % 360.0F);
									ClientData.minecraft.player.setPitch(90.0F);
								}
							}
							framebuffer.beginWrite(true);
							ClientData.minecraft.gameRenderer.renderWorld(RenderTickCounter.ONE);
							ScreenshotRecorder.saveScreenshot(screenshotsDir, "panorama_" + l + ".png", ClientData.minecraft.getFramebuffer());
						}

						// Create pack.mcmeta
						File packFile = new File(resourcePackDir + "/pack.mcmeta");
						if (packFile.createNewFile()) {
							FileWriter packWriter = new FileWriter(packFile);
							packWriter.write("{\"pack\": {\"pack_format\": " + SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES) + ", \"supported_formats\": {\"min_inclusive\": 1, \"max_inclusive\": 2147483647}, \"description\": \"" + panoramaName + "\"}}\"}}");
							packWriter.close();
						}
					}

					ClientData.minecraft.player.setPitch(playerPitch);
					ClientData.minecraft.player.setYaw(playerYaw);

					ClientData.minecraft.getWindow().setFramebufferWidth(framebufferWidth);
					ClientData.minecraft.getWindow().setFramebufferHeight(framebufferHeight);
					framebuffer.resize(framebufferWidth, framebufferHeight);
					if (!playerPerspective.isFirstPerson()) ClientData.minecraft.options.setPerspective(playerPerspective);
					ClientData.minecraft.gameRenderer.setBlockOutlineEnabled(true);
					ClientData.minecraft.gameRenderer.setRenderingPanorama(false);
					ClientData.minecraft.getFramebuffer().beginWrite(true);
					ClientData.minecraft.player.sendMessage(Translation.getTranslation(Data.getVersion().getID(), "message.take_panorama_screenshot.success", new Object[]{Text.literal(panoramaName).formatted(Formatting.UNDERLINE).styled((style) -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, resourcePackDir.getAbsolutePath())))}), false);
				}
			} else {
				String[] incompatible = getIncompatible().toString().replace("[", "").replace("]", "").replace(" ", "").split(",");
				if (ClientData.minecraft.player != null) {
					Text[] incompatibleTranslated = new Text[incompatible.length];
					for (int i = 0; i < incompatible.length; i++) incompatibleTranslated[i] = Translation.getTranslation(Data.getVersion().getID(), "message.take_panorama_screenshot.fail.incompatible." + incompatible[i]);
					ClientData.minecraft.player.sendMessage(Translation.getTranslation(Data.getVersion().getID(), "message.take_panorama_screenshot.fail", new Object[]{Text.literal("")}), false);
					for (Text text : incompatibleTranslated) ClientData.minecraft.player.sendMessage(text, false);
				}
				else Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to take panorama: {}", Arrays.toString(incompatible)));
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to take panoramic screenshot: {}", error));
		}
	}
}