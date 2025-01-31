/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.perspective.client.april_fools_prank.AprilFoolsPrank;
import com.mclegoman.perspective.config.ConfigHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.shaders.Shader;
import com.mclegoman.perspective.client.shaders.ShaderRegistryValue;
import com.mclegoman.perspective.client.util.UpdateChecker;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.ArrayList;
import java.util.List;

public class DebugOverlay {
	public static Type debugType = Type.NONE;
	public static Formatting shaderColor;
	public static void renderDebugHUD(DrawContext context) {
		int y = 2;
		int x = 2;
		List<Object> debugText = new ArrayList<>();
		if ((boolean) ConfigHelper.getConfig("version_overlay")) {
			debugText.add("\n");
		}
		debugText.add(Text.literal(Data.version.getName() + " " + Data.version.getFriendlyString(false)));
		if (debugType.equals(Type.MISC)) {
			debugText.add("\n");
			debugText.add(Text.literal("isAprilFools(): " + AprilFoolsPrank.isAprilFools()));
			debugText.add(Text.literal("isSaving(): " + ConfigHelper.isSaving()));
			debugText.add(Text.literal("isZooming(): " + Zoom.isZooming()));
			debugText.add(Text.literal("getZoomLevel(): " + Zoom.getZoomLevel()));
			debugText.add(Text.literal("Newer Version Found: " + UpdateChecker.NEWER_VERSION_FOUND));
			debugText.add("\n");
			debugText.add(Text.literal("Super Secret Settings").formatted(Formatting.BOLD, shaderColor));
			debugText.add(Text.literal("shader: " + ConfigHelper.getConfig("super_secret_settings_shader")));
			debugText.add(Text.literal("disable_screen_mode: " + Shader.getShaderData(ShaderRegistryValue.DISABLE_SCREEN_MODE)));
			debugText.add(Text.literal("render_type: " + Shader.RENDER_TYPE));
			debugText.add(Text.literal("use_depth: " + Shader.USE_DEPTH));
			debugText.add(Text.literal("shouldDisableScreenMode(): " + Shader.shouldDisableScreenMode()));
			debugText.add(Text.literal("shouldRenderShader(): " + Shader.shouldRenderShader()));
		}
		if (debugType.equals(Type.CONFIG)) {
			debugText.add("\n");
			debugText.add(Text.literal("Latest Saved Config Values").formatted(Formatting.BOLD));
			debugText.addAll(ConfigHelper.getDebugConfigText());
		}
		for (Object item : debugText) {
			if (item instanceof Text text) {
				TextRenderer textRenderer = ClientData.minecraft.textRenderer;
				int width = textRenderer.getWidth(text);
				if (y > ClientData.minecraft.getWindow().getScaledHeight() - 2 - 9) {
					y = 2;
					x += 256;
				}
				context.fill(x - 1, y - 1, x + width + 1, y + 9, -1873784752);
				context.drawText(ClientData.minecraft.textRenderer, text, x, y, 0xffffff, false);
				y = HUDHelper.addY(y);
			} else {
				if (item.equals("\n")) {
					y = HUDHelper.addY(y);
				}
			}
		}
	}
	public enum Type {
		NONE,
		MISC,
		CONFIG;
		private static final Type[] VALUES = values();
		public Type next() {
			return VALUES[(this.ordinal() + 1) % VALUES.length];
		}
	}
}