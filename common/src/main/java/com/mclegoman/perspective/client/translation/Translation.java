/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.translation;

import com.mclegoman.perspective.client.config.value.QualityToggle;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.zoom.Zoom;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translation extends com.mclegoman.luminance.client.translation.Translation {
	public static String getTitleCase(String key) {
		if (key != null && !key.isEmpty()) {
			String[] words = key.replace('_', ' ').split(" ");
			StringBuilder result = new StringBuilder();
			for (String word : words) {
				if (!word.isEmpty()) result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1).toLowerCase()).append(" ");
			}
			return result.toString().trim();
		}
		return key;
	}
	public static MutableText getCombinedText(List<MutableText> texts) {
		return getCombinedText(texts.toArray(new MutableText[0]));
	}
	public static MutableText getQualityTranslation(String namespace, QualityToggle type) {
		return type.asString().equalsIgnoreCase("off") ? getVariableTranslation(namespace, false, Type.ONFF) : getTranslation(namespace, "quality." + type.asString());
	}
	public static MutableText getVariableTranslation(String namespace, boolean toggle, Type type) {
		return toggle ? getTranslation(namespace, "variable." + type.asString() + ".on") : getTranslation(namespace, "variable." + type.asString() + ".off");
	}
	public static MutableText getShaderTranslation(String namespace, String shaderName) {
		return getText("shader." + namespace + "." + shaderName, true);
	}
	public static MutableText getShaderModeTranslation(String namespace, String key, boolean hover) {
		if (key.equalsIgnoreCase("game")) return getConfigTranslation(namespace, "shaders.super_secret_settings.mode.game", hover);
		else if (key.equalsIgnoreCase("ui") || key.equalsIgnoreCase("screen")) return getConfigTranslation(namespace, "shaders.super_secret_settings.mode.ui", hover);
		else return getErrorTranslation(namespace);
	}
	public static MutableText getShaderModeTranslation(String namespace, String key) {
		return getShaderModeTranslation(namespace, key, false);
	}
	public static MutableText getZoomTransitionTranslation(String namespace, String key) {
		if (key.equalsIgnoreCase("instant")) return getConfigTranslation(namespace, "zoom.transition.instant");
		else if (key.equalsIgnoreCase("smooth")) return getConfigTranslation(namespace, "zoom.transition.smooth");
		else return getErrorTranslation(namespace);
	}
	public static MutableText getZoomScaleModeTranslation(String namespace, String key) {
		if (key.equalsIgnoreCase("scaled")) return getConfigTranslation(namespace, "zoom.scale_mode.scaled");
		else if (key.equalsIgnoreCase("vanilla")) return getConfigTranslation(namespace, "zoom.scale_mode.vanilla");
		else return getErrorTranslation(namespace);
	}
	public static MutableText getZoomTypeTranslation(String namespace, String zoomType, boolean hover) {
		if (Zoom.isValidZoomType(Zoom.getZoomType())) {
			return getConfigTranslation(namespace, "zoom.type." + zoomType + (hover ? ".hover" : ""));
		}
		return getErrorTranslation(namespace);
	}
	public static MutableText getZoomTypeTranslation(String namespace, String zoomType) {
		return getZoomTypeTranslation(namespace, zoomType, false);
	}
	public static MutableText getUIBackgroundTranslation(String namespace, Identifier ui_background) {
		return getUIBackgroundTranslation(namespace, ui_background, false);
	}
	public static MutableText getUIBackgroundTranslation(String namespace, Identifier ui_background, boolean hover) {
		return getConfigTranslation(namespace, "shaders.ui_background.type." + ui_background.getNamespace() + "." + ui_background.getPath() + (hover ? ".hover" : ""));
	}
	public static MutableText getTimeOverlayTranslation(String namespace, String key) {
		return getConfigTranslation(namespace, "time_overlay.type." + key);
	}
	public static MutableText getCrosshairTranslation(String namespace, String key) {
		if (Arrays.stream(Hide.hideCrosshairModes).toList().contains(key)) return getConfigTranslation(namespace, "crosshair.type." + key);
		else return getErrorTranslation(namespace);
	}
	public static MutableText getDetectUpdateChannelTranslation(String namespace, String key) {
		if (key.equalsIgnoreCase("none")) return getConfigTranslation(namespace, "detect_update_channel.none");
		else if (key.equalsIgnoreCase("alpha")) return getConfigTranslation(namespace, "detect_update_channel.alpha");
		else if (key.equalsIgnoreCase("beta")) return getConfigTranslation(namespace, "detect_update_channel.beta");
		else if (key.equalsIgnoreCase("release")) return getConfigTranslation(namespace, "detect_update_channel.release");
		else return getErrorTranslation(namespace);
	}
	public static MutableText getItemGroupTranslation(String namespace, String key, Object[] variables, Formatting[] formattings) {
		return getTranslation("item_group", namespace, key, variables, formattings);
	}
	public static MutableText getItemGroupTranslation(String namespace, String key, Object[] variables) {
		return getTranslation("item_group", namespace, key, variables);
	}
	public static MutableText getItemGroupTranslation(String namespace, String key, Formatting[] formattings) {
		return getTranslation("item_group", namespace, key, formattings);
	}
	public static MutableText getItemGroupTranslation(String namespace, String key) {
		return getTranslation("item_group", namespace, key);
	}
	public static MutableText getZoomSmoothSpeedTranslation(String namespace, float value) {
		return getConfigTranslation(namespace, "zoom.smooth_speed." + (value <= 0.01F ? "min" : (value == 1.0F ? "normal" : (value >= 2.0F ? "max" : "value"))), new Object[]{value});
	}
	public enum Type implements StringIdentifiable {
		ENDISABLE("endisable"),
		ONFF("onff"),
		BLUR("blur");
		private final String name;
		Type(String name) {
			this.name = name;
		}
		@Override
		public String asString() {
			return this.name;
		}
	}
	public static Text getParsedTextFromString(String value) {
		Matcher matcher = Pattern.compile("Translatable\\[[^]]+]").matcher(value);
		int lastEnd = 0;
		List<MutableText> result = new ArrayList<>();
		while (matcher.find()) {
			if (matcher.start() > lastEnd) {
				String literalPart = value.substring(lastEnd, matcher.start());
				result.add(Text.literal(literalPart));
			}
			int index = matcher.start();
			int end = findEndOfTranslatable(value, index);
			String translatableText = value.substring(index, end);
			result.add((MutableText) parseStringToText(translatableText));
			lastEnd = end;
			matcher.region(lastEnd, value.length());
		}
		if (lastEnd < value.length()) {
			result.add(Text.literal(value.substring(lastEnd)));
		}
		return Translation.getCombinedText(result);
	}
	private static int findEndOfTranslatable(String value, int index) {
		int length = value.length();
		index = value.indexOf(']', index);
		if (index == -1) return length;
		index++;
		if (index >= length || value.charAt(index) != '(') return index;
		int depth = 1;
		index++;
		while (index < length && depth > 0) {
			char c = value.charAt(index);
			if (c == '(') depth++;
			else if (c == ')') depth--;
			index++;
		}
		return index;
	}
	private static Text parseStringToText(String input) {
		Text parsed = tryParseTranslatable(input.trim());
		if (parsed != null) return parsed;
		return Text.literal(input.trim());
	}
	private static Text tryParseTranslatable(String input) {
		Pattern pattern = Pattern.compile("^Translatable\\[([^]]+)](?:\\((.*)\\))?$");
		Matcher matcher = pattern.matcher(input);
		if (!matcher.matches()) return null;
		String key = matcher.group(1);
		String argsGroup = matcher.group(2);
		if (argsGroup == null || argsGroup.isEmpty()) return Text.translatable(key);
		return Text.translatable(key, splitArgs(argsGroup).stream().map(Translation::parseStringToText).toArray());
	}
	private static List<String> splitArgs(String argsString) {
		List<String> result = new ArrayList<>();
		int depth = 0;
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < argsString.length(); i++) {
			char c = argsString.charAt(i);
			if (c == '(' || c == '[') depth++;
			else if (c == ')' || c == ']') depth--;
			if (c == ',' && depth == 0) {
				result.add(current.toString().trim());
				current.setLength(0);
			} else current.append(c);
		}
		if (!current.isEmpty()) result.add(current.toString().trim());
		return result;
	}
	public static String getStringFromText(Text text) {
		if (text.getContent() instanceof TranslatableTextContent translatable) {
			Object[] args = translatable.getArgs();
			StringBuilder arguments = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				arguments.append(args[i].toString());
				if (i < args.length - 1) arguments.append(", ");
			}
			return "Translatable[" + translatable.getKey() + "]" + (!arguments.isEmpty() ? "(" + arguments + ")": "");
		}
		return text.getString();
	}
	public static String getTranslationKey(String namespace, String key) {
		return "gui." + namespace + "." + key;
	}
}