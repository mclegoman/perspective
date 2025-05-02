/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.shaders;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.widget.ConfigButtonWidget;
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.ui.UIBackground;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.util.Formatting;

public class ShadersConfigScreen extends AbstractConfigScreen {
	public ShadersConfigScreen(Screen parentScreen) {
		super(parentScreen, 1);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) {
				this.gridAdder.add(createPageOne());
			}
			postInit();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize shaders config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createPageOne() {
		GridWidget shaderOptionsGrid = new GridWidget();
		shaderOptionsGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder shaderOptionsGridAdder = shaderOptionsGrid.createAdder(2);
		try {
			shaderOptionsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.super_secret_settings", new Formatting[]{ShaderPacks.getRandomColor()}), (button) -> ClientData.minecraft.setScreen(new SuperSecretSettingsConfigScreen(getRefreshScreen(), new Formatting[]{ShaderPacks.getRandomColor()}))).width(304).build(), 2);
			shaderOptionsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.kaleidoscope.random", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.randomKaleidoscope.value(), Translation.Type.ONFF)}), (button) -> PerspectiveConfig.toggle(PerspectiveConfig.config.randomKaleidoscope)).tooltip(() -> Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.kaleidoscope.random", true))).build());
			shaderOptionsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.kaleidoscope.named", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.namedKaleidoscope.value(), Translation.Type.ONFF)}), (button) -> PerspectiveConfig.toggle(PerspectiveConfig.config.namedKaleidoscope)).tooltip(() -> Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.kaleidoscope.named", true))).build());
			shaderOptionsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "shaders.ui_background", new Object[]{Translation.getUIBackgroundTranslation(Data.getVersion().getID(), UIBackground.getCurrentUIBackground().getId())}), (button) -> UIBackground.cycleUIBackgroundType(!hasShiftDown())).tooltip(() -> Tooltip.of(Translation.getUIBackgroundTranslation(Data.getVersion().getID(), UIBackground.getCurrentUIBackground().getId(), true))).width(304).build(), 2);
			shaderOptionsGridAdder.add(new EmptyWidget(20, 20), 2);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, "An error occurred on config/shaders/page1: " + error.getLocalizedMessage());
		}
		return shaderOptionsGrid;
	}
	public Screen getRefreshScreen() {
		return new ShadersConfigScreen(parentScreen);
	}
	public String getPageId() {
		return "shaders";
	}
}