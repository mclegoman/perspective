/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.perspective.client.config.PerspectiveDefaultConfig;
import com.mclegoman.perspective.client.entity.TexturedEntityDataReloader;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.update.Update;
import com.mclegoman.perspective.client.zoom.Zoom;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.quiltmc.config.api.values.ValueTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugOverlay {
	public static Type debugType = Type.none;
	public static Formatting shaderColor;

	public static void renderDebugHUD(DrawContext context) {
		List<Text> debugTexts = new ArrayList<>();
		debugTexts.add(Text.literal(Data.getVersion().getName() + " " + Data.getVersion().getFriendlyString(false)));
		if (debugType.equals(Type.misc)) {
			debugTexts.add(Text.empty());
			debugTexts.add(Text.literal("debug: " + PerspectiveConfig.config.debug.value()));
			debugTexts.add(Text.literal("isAprilFools(): " + AprilFoolsPrank.isAprilFools()));
			debugTexts.add(Text.literal("isZooming(): " + Zoom.isZooming()));
			debugTexts.add(Text.literal("getZoomLevel(): " + Zoom.getZoomLevel()));
			debugTexts.add(Translation.getCombinedText(Text.literal("getZoomType(): "), Translation.getZoomTypeTranslation(Zoom.getZoomType().getNamespace(), Zoom.getZoomType().getPath())));
			debugTexts.add(Text.literal("isNewerVersionFound(): " + Update.isNewerVersionFound()));
		}
		if (debugType.equals(Type.normalConfig) || debugType.equals(Type.defaultConfig)) {
			debugTexts.add(Text.empty());
			debugTexts.add(Translation.getTranslation(Data.getVersion().getID(), "debug.config", new Formatting[]{Formatting.BOLD}));
			if (debugType.equals(Type.normalConfig)) debugTexts.addAll(getDebugConfigText(ConfigType.normal));
			else if (debugType.equals(Type.defaultConfig)) debugTexts.addAll(getDebugConfigText(ConfigType.defaults));
		}
		if (debugType.equals(Type.texturedEntities) || debugType.equals(Type.enabledTexturedEntities)) {
			debugTexts.add(Text.empty());
			debugTexts.add(Translation.getTranslation(Data.getVersion().getID(), "debug.textured_entity" + (debugType.equals(Type.enabledTexturedEntities) ? ".enabled" : ""), new Formatting[]{Formatting.BOLD}));
			TexturedEntityDataReloader.getRegistryMap().forEach((id, data) -> {
				if (debugType.equals(Type.enabledTexturedEntities) && data.getEnabled() || debugType.equals(Type.texturedEntities)) debugTexts.add(Text.literal(id.toString() + ":" + data.getNamespace() + ":" + data.getType() + ":" + data.getName()));
			});
		}
		Overlays.renderOverlays(context, debugTexts, 0, 0, true);
	}

	public enum Type {
		none,
		misc,
		normalConfig,
		defaultConfig,
		texturedEntities,
		enabledTexturedEntities;
		private static final Type[] values = values();
		public Type prev() {
			return values[getIndex(false)];
		}
		public Type next() {
			return values[getIndex(true)];
		}
		private int getIndex(boolean forwards) {
			return nextIndex(forwards);
		}
		private int nextIndex(boolean forwards) {
			return forwards ? (this.ordinal() + 1) % values.length : (this.ordinal() - 1) < 0 ? values.length - 1 : this.ordinal() - 1;
		}
	}
	public static List<Text> getDebugConfigText(ConfigType... types) {
		List<Text> text = new ArrayList<>();
		if (Arrays.stream(types).toList().contains(ConfigType.normal)) {
			text.add(Translation.getTranslation(Data.getVersion().getID(), "debug.config.normal", new Formatting[]{Formatting.BOLD}));
			for (ValueTreeNode treeNode : PerspectiveConfig.config.nodes())
				text.add(Text.literal(treeNode.key() + ": " + PerspectiveConfig.config.getValue(treeNode.key()).value()));
		}
		if (Arrays.stream(types).toList().contains(ConfigType.defaults)) {
			text.add(Translation.getTranslation(Data.getVersion().getID(), "debug.config.default", new Formatting[]{Formatting.BOLD}));
			for (ValueTreeNode treeNode : PerspectiveDefaultConfig.config.nodes())
				text.add(Text.literal(treeNode.key() + ": " + PerspectiveDefaultConfig.config.getValue(treeNode.key()).value()));
		}
		return text;
	}
	public enum ConfigType {
		normal,
		defaults
	}
}