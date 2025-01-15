/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.hide;

import fabric.com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.screen.widget.ConfigSliderWidget;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hide.Hide;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class HideConfigScreen extends AbstractConfigScreen {
	public HideConfigScreen(Screen parentScreen, boolean refresh, int page) {
		super(parentScreen, refresh, page);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) this.gridAdder.add(createPageOne());
			else shouldClose = true;
			postInit();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to initialize zoom config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createPageOne() {
		GridWidget hideGrid = new GridWidget();
		hideGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder hideGridAdder = hideGrid.createAdder(2);
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.block_outline", new Object[]{Translation.getVariableTranslation(Data.version.getID(), !PerspectiveConfig.config.hideBlockOutline.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideBlockOutline, false);
			this.refresh = true;
		}).build());
		double blockOutlineLevel = (double) PerspectiveConfig.config.blockOutline.value() / 100;
		hideGridAdder.add(new ConfigSliderWidget(hideGridAdder.getGridWidget().getX(), hideGridAdder.getGridWidget().getY(), 150, 20, Translation.getConfigTranslation(Data.version.getID(), "hide.block_outline", new Object[]{Text.literal(PerspectiveConfig.config.blockOutline.value() + "%")}, false), blockOutlineLevel) {
			@Override
			protected void updateMessage() {
				setMessage(Translation.getConfigTranslation(Data.version.getID(),  "hide.block_outline.level", new Object[]{Text.literal(PerspectiveConfig.config.blockOutline.value() + "%")}, false));
			}
			@Override
			protected void applyValue() {
				PerspectiveConfig.config.blockOutline.setValue((int) ((value) * 100), false);
			}
		});
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.rainbow_block_outline", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.rainbowBlockOutline.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.rainbowBlockOutline, false);
			this.refresh = true;
		}).build());
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.crosshair", new Object[]{Translation.getCrosshairTranslation(Data.version.getID(), PerspectiveConfig.config.crosshairType.value())}), (button) -> {
			PerspectiveConfig.config.crosshairType.setValue(Hide.nextCrosshairMode(), false);
			this.refresh = true;
		}).build());
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_armor", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hideArmor.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideArmor, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_armor", true))).build());
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_nametags", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hideNametags.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideNametags, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_nametags", true))).build());
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_players", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hidePlayers.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hidePlayers, false);
			this.refresh = true;
		}).tooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "hide.hide_players", true))).build());
		hideGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "hide.show_message", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.hideShowMessage.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.hideShowMessage, false);
			this.refresh = true;
		}).build());
		return hideGrid;
	}
	public Screen getRefreshScreen() {
		return new HideConfigScreen(this.parentScreen, false, this.page);
	}
	public String getPageId() {
		return "hide";
	}
}