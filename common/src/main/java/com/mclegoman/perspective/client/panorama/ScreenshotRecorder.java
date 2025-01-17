/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.panorama;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Util;

import java.io.File;

public class ScreenshotRecorder {
	public static void saveScreenshot(File gameDirectory, String fileName, Framebuffer framebuffer) {
		if (!RenderSystem.isOnRenderThread()) RenderSystem.recordRenderCall(() -> saveScreenshotInner(gameDirectory, fileName, framebuffer));
		else saveScreenshotInner(gameDirectory, fileName, framebuffer);
	}
	private static void saveScreenshotInner(File screenshotDir, String fileName, Framebuffer framebuffer) {
		try(NativeImage nativeImage = net.minecraft.client.util.ScreenshotRecorder.takeScreenshot(framebuffer)) {
			Util.getIoWorkerExecutor().execute(() -> {
				try {
					nativeImage.writeTo(new File(screenshotDir, fileName));
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to write screenshot: ", error));
				}
				nativeImage.close();
			});
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to take screenshot: ", error));
		}
	}
}