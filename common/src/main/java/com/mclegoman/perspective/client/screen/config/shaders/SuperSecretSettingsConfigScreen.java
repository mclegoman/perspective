/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.shaders;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.util.Formatting;

public class SuperSecretSettingsConfigScreen extends AbstractConfigScreen {
	private Formatting[] formatting;
	public SuperSecretSettingsConfigScreen(Screen parentScreen, boolean refresh, Formatting[] formatting) {
		super(parentScreen, refresh, 1);
		this.formatting = formatting;
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
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize super secret settings config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createShaders() {
		GridWidget shadersGrid = new GridWidget();
		shadersGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder shadersGridAdder = shadersGrid.createAdder(2);
		ButtonWidget cycleShaders = ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.cycle", new Object[]{ShaderPacks.getShader() != null ? ShaderPacks.getShader().translation().getTranslation(ShaderPacks.shouldShowNamespace(ShaderPacks.getShadersId(), ShaderPacks.getShader().translation().id())) : Translation.getShaderTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.not_loaded")}), (button) -> {
			ShaderPacks.cycle(!hasShiftDown());
			this.formatting = new Formatting[]{ShaderPacks.getRandomColor()};
			this.refresh = true;
		}).tooltip(ShaderPacks.getTooltip()).width(280).build();
		cycleShaders.active = ShaderPacks.isShadersEnabled();
		shadersGridAdder.add(cycleShaders);
		ButtonWidget listShaders = ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.list"), (button) -> ClientData.minecraft.setScreen(new ShaderPackSelectionScreen(getRefreshScreen(), new Formatting[]{ShaderPacks.getRandomColor()}, -1, PerspectiveConfig.config.superSecretSettingsSelectionBlur.value()))).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.list", true))).width(20).build();
		listShaders.active = ShaderPacks.isShadersEnabled();
		shadersGridAdder.add(listShaders);
		return shadersGrid;
	}
	private GridWidget createPageOne() {
		GridWidget shaderOptionsGrid = new GridWidget();
		shaderOptionsGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder shaderOptionsGridAdder = shaderOptionsGrid.createAdder(2);
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.mode", new Object[]{Translation.getShaderModeTranslation(Data.getVersion().getID(), PerspectiveConfig.config.superSecretSettingsMode.value().name())}), (button) -> {
			ShaderPacks.cycleShaderMode();
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.mode", new Object[]{Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.mode." + PerspectiveConfig.config.superSecretSettingsMode.value().name(), true)}, true))).build());
		ButtonWidget randomShader = ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.random"), (button) -> {
			ShaderPacks.randomize();
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.random", true))).build();
		randomShader.active = ShaderPacks.isShadersEnabled();
		shaderOptionsGridAdder.add(randomShader);
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.show_name", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.superSecretSettingsShowName.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.superSecretSettingsShowName, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.show_name", new Object[]{Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.show_name." + (PerspectiveConfig.config.superSecretSettingsShowName.value() ? "on" : "off"), true)}, true))).build());
		shaderOptionsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings.toggle", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.superSecretSettingsEnabled.value(), Translation.Type.ENDISABLE)}), (button) -> {
			ShaderPacks.toggle();
			this.refresh = true;
		}).build());
		return shaderOptionsGrid;
	}
	public Screen getRefreshScreen() {
		return new SuperSecretSettingsConfigScreen(parentScreen, false, formatting);
	}
	public String getPageId() {
		return "shaders.super_secret_settings";
	}
}