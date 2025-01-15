/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.shaders;

import fabric.com.mclegoman.luminance.client.events.Events;
import fabric.com.mclegoman.luminance.client.shaders.Shaders;
import fabric.com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ShadersConfigScreen extends AbstractConfigScreen {
	private Formatting[] formattings;
	public ShadersConfigScreen(Screen parentScreen, boolean refresh, Formatting[] formattings) {
		super(parentScreen, refresh, 1);
		this.formattings = formattings;
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) {
				this.gridAdder.add(createShaders());
				this.gridAdder.add(createPageOne());
				this.gridAdder.add(new EmptyWidget(16, 16));
			}
			postInit();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to initialize shaders config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createShaders() {
		GridWidget shadersGrid = new GridWidget();
		shadersGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder shadersGridAdder = shadersGrid.createAdder(3);
		ButtonWidget cycleShaders = ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.cycle", new Object[]{Events.ShaderRender.Shaders.exists(com.mclegoman.perspective.client.shaders.Shaders.superSecretSettingsId, com.mclegoman.perspective.client.shaders.Shaders.superSecretSettingsId) ?  Shaders.getShaderName(Shaders.getShaderIndex(Shaders.guessPostShader(String.valueOf(PerspectiveConfig.config.superSecretSettingsShader.value())))) : Translation.getShaderTranslation(Data.version.getID(), "shader.not_loaded")}, formattings), (button) -> {
			//Shader.cycle(true, !hasShiftDown(), true, false, false);
			this.formattings = new Formatting[]{com.mclegoman.perspective.client.shaders.Shaders.getRandomColor()};
			this.refresh = true;
		}).width(256).build();
		cycleShaders.active = com.mclegoman.perspective.client.shaders.Shaders.isShaderButtonsEnabled();
		shadersGridAdder.add(cycleShaders);
		ButtonWidget listShaders = ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.list"), (button) -> ClientData.minecraft.setScreen(new ShaderSelectionConfigScreen(getRefreshScreen(), new Formatting[]{com.mclegoman.perspective.client.shaders.Shaders.getRandomColor()}, -1, PerspectiveConfig.config.superSecretSettingsSelectionBlur.value()))).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "shaders.list", true))).width(20).build();
		listShaders.active = com.mclegoman.perspective.client.shaders.Shaders.isShaderButtonsEnabled();
		shadersGridAdder.add(listShaders);
		ButtonWidget randomShader = ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.random"), (button) -> {
			//com.mclegoman.perspective.client.shaders.Shaders.random(true, false, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "shaders.random", true))).width(20).build();
		randomShader.active = com.mclegoman.perspective.client.shaders.Shaders.isShaderButtonsEnabled();
		shadersGridAdder.add(randomShader);
		return shadersGrid;
	}
	private GridWidget createPageOne() {
		GridWidget shaderOptionsGrid = new GridWidget();
		shaderOptionsGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder shaderOptionsGridAdder = shaderOptionsGrid.createAdder(2);
		//shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.mode", new Object[]{Translation.getShaderModeTranslation(Data.version.getID(), (String) ConfigHelper.getConfig(ConfigHelper.ConfigType.normal, "super_secret_settings_mode")), ConfigHelper.getConfig(ConfigHelper.ConfigType.normal, "super_secret_settings_mode").equals("screen") ? Translation.getVariableTranslation(Data.version.getID(), Shaders.get(Shader.superSecretSettingsIndex).getDisableGameRendertype(), Translation.Type.DISABLE_SCREEN_MODE) : ""}), (button) -> {
			//Shader.cycleShaderModes();
			//this.refresh = true;
		//}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "shaders.mode", new Object[]{Translation.getConfigTranslation(Data.version.getID(), "shaders.mode." + ConfigHelper.getConfig(ConfigHelper.ConfigType.normal, "super_secret_settings_mode"), true)}, true))).build());
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.play_sound", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.superSecretSettingsSound.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.superSecretSettingsSound, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "shaders.play_sound", new Object[]{Translation.getConfigTranslation(Data.version.getID(), "shaders.play_sound." + (PerspectiveConfig.config.superSecretSettingsSound.value() ? "on" : "off"), true)}, true))).build());
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.show_name", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.superSecretSettingsShowName.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.superSecretSettingsShowName, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "shaders.show_name", new Object[]{Translation.getConfigTranslation(Data.version.getID(), "shaders.show_name." + (PerspectiveConfig.config.superSecretSettingsShowName.value() ? "on" : "off"), true)}, true))).build());
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "shaders.toggle", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.superSecretSettingsEnabled.value(), Translation.Type.ENDISABLE)}), (button) -> {
			//Shader.toggle(true, false, false, false);
			this.refresh = true;
		}).build());
		return shaderOptionsGrid;
	}
	public Screen getRefreshScreen() {
		return new ShadersConfigScreen(parentScreen, false, formattings);
	}
	public String getPageId() {
		return "shaders";
	}
	public boolean isBeingReworked() {
		return true;
	}
}