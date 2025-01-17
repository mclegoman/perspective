/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.logo.PerspectiveLogo;
import com.mclegoman.perspective.client.screen.config.overlays.OverlaysConfigScreen;
import com.mclegoman.perspective.client.logo.SplashesDataloader;
import com.mclegoman.perspective.client.shaders.Shaders;
import com.mclegoman.perspective.client.ui.UIBackground;
import com.mclegoman.perspective.client.config.ConfigHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.config.events.EventsConfigScreen;
import com.mclegoman.perspective.client.screen.config.hide.HideConfigScreen;
import com.mclegoman.perspective.client.screen.config.hold_perspective.HoldPerspectiveConfigScreen;
import com.mclegoman.perspective.client.screen.config.information.InformationScreen;
import com.mclegoman.perspective.client.screen.config.shaders.ShadersConfigScreen;
import com.mclegoman.perspective.client.screen.config.textured_entity.TexturedEntityConfigScreen;
import com.mclegoman.perspective.client.screen.config.zoom.ZoomConfigScreen;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.update.Update;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.util.Formatting;

public class ConfigScreen extends AbstractConfigScreen {
	public ConfigScreen(Screen parentScreen, boolean refresh, int page) {
		super(parentScreen, refresh, page);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) this.gridAdder.add(createPageOne());
			else if (this.page == 2) this.gridAdder.add(createPageTwo());
			else shouldClose = true;
			postInit();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	protected void setParentScreen() {
		PerspectiveConfig.config.save();
		super.setParentScreen();
	}
	private GridWidget createPageOne() {
		GridWidget grid = new GridWidget();
		grid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder gridAdder = grid.createAdder(2);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "zoom"), (button) -> ClientData.minecraft.setScreen(new ZoomConfigScreen(getRefreshScreen(), false, 1))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "shaders", new Formatting[]{Shaders.getRandomColor()}), (button) -> ClientData.minecraft.setScreen(new ShadersConfigScreen(getRefreshScreen(), false, new Formatting[]{Shaders.getRandomColor()}))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "textured_entity"), (button) -> ClientData.minecraft.setScreen(new TexturedEntityConfigScreen(getRefreshScreen(), false))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "events"), (button) -> ClientData.minecraft.setScreen(new EventsConfigScreen(getRefreshScreen(), false, 1))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "hide"), (button) -> ClientData.minecraft.setScreen(new HideConfigScreen(getRefreshScreen(), false, 1))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective"), (button) -> ClientData.minecraft.setScreen(new HoldPerspectiveConfigScreen(getRefreshScreen(), false, 1))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "overlays"), (button) -> ClientData.minecraft.setScreen(new OverlaysConfigScreen(getRefreshScreen(), false, 1))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "luminance"), (button) -> ClientData.minecraft.setScreen(new com.mclegoman.luminance.client.screen.config.ConfigScreen(getRefreshScreen(), false, SplashesDataloader.getSplashText(), PerspectiveLogo.isPride()))).build());
		return grid;
	}
	private GridWidget createPageTwo() {
		GridWidget grid = new GridWidget();
		grid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder gridAdder = grid.createAdder(2);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "ui_background", new Object[]{Translation.getUIBackgroundTranslation(Data.getVersion().getID(), UIBackground.getCurrentUIBackground().getId())}), (button) -> {
			UIBackground.cycleUIBackgroundType(!hasShiftDown());
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getUIBackgroundTranslation(Data.getVersion().getID(), UIBackground.getCurrentUIBackground().getId(), true))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "show_death_coordinates", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.showDeathCoordinates.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.showDeathCoordinates);
			this.refresh = true;
		}).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "tutorials", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.tutorials.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.tutorials);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "tutorials", true))).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "force_pride", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forcePride.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.forcePride);
			this.refresh = true;
		}).build());
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "detect_update_channel", new Object[]{Translation.getDetectUpdateChannelTranslation(Data.getVersion().getID(), PerspectiveConfig.config.detectUpdateChannel.value())}), (button) -> {
			PerspectiveConfig.config.detectUpdateChannel.setValue(Update.nextUpdateChannel(), false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "detect_update_channel", true))).width(304).build(), 2);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "information"), (button) -> ClientData.minecraft.setScreen(new InformationScreen(getRefreshScreen(), false))).build());
		ButtonWidget experimental = ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "experimental"), (button) -> {}).build();
		experimental.active = ConfigHelper.experimentsAvailable;
		gridAdder.add(experimental);
		return grid;
	}
	public Screen getRefreshScreen() {
		return new ConfigScreen(this.parentScreen, false, this.page);
	}
	public String getPageId() {
		return "config";
	}
	public int getMaxPage() {
		return 2;
	}
}