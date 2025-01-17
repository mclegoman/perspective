/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.hold_perspective;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.screen.widget.ConfigSliderWidget;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;

public class HoldPerspectiveConfigScreen extends AbstractConfigScreen {
	public HoldPerspectiveConfigScreen(Screen parentScreen, boolean refresh, int page) {
		super(parentScreen, refresh, page);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) this.gridAdder.add(createPageOne());
			else shouldClose = true;
			postInit();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize zoom config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createPageOne() {
		GridWidget holdPerspectiveGrid = new GridWidget();
		holdPerspectiveGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder holdPerspectiveGridAdder = holdPerspectiveGrid.createAdder(2);
		holdPerspectiveGridAdder.add(new ConfigSliderWidget(holdPerspectiveGridAdder.getGridWidget().getX(), holdPerspectiveGridAdder.getGridWidget().getY(), 150, 20, Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.back.multiplier", new Object[]{String.format("%.2f", PerspectiveConfig.config.holdPerspectiveBackMultiplier.value())}, false), ((PerspectiveConfig.config.holdPerspectiveBackMultiplier.value() - 0.5F) / 7.5F)) {
			@Override
			protected void updateMessage() {
				setMessage(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.back.multiplier", new Object[]{String.format("%.2f", PerspectiveConfig.config.holdPerspectiveBackMultiplier.value())}, false));
			}
			@Override
			protected void applyValue() {
				PerspectiveConfig.config.holdPerspectiveBackMultiplier.setValue(Float.valueOf(String.format("%.2f", ((value * 7.5F) + 0.5F))), false);
			}
		}).setTooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.back.multiplier", true)));
		holdPerspectiveGridAdder.add(new ConfigSliderWidget(holdPerspectiveGridAdder.getGridWidget().getX(), holdPerspectiveGridAdder.getGridWidget().getY(), 150, 20, Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.front.multiplier", new Object[]{String.format("%.2f", PerspectiveConfig.config.holdPerspectiveFrontMultiplier.value())}, false), ((PerspectiveConfig.config.holdPerspectiveFrontMultiplier.value() - 0.5F) / 7.5F)) {
			@Override
			protected void updateMessage() {
				setMessage(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.front.multiplier", new Object[]{String.format("%.2f", PerspectiveConfig.config.holdPerspectiveFrontMultiplier.value())}, false));
			}
			@Override
			protected void applyValue() {
				PerspectiveConfig.config.holdPerspectiveFrontMultiplier.setValue(Float.valueOf(String.format("%.2f", ((value * 7.5F) + 0.5F))), false);
			}
		}).setTooltip(Tooltip.of(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.front.multiplier", true)));
		holdPerspectiveGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.back.hide_hud", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.holdPerspectiveBackHideHud.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.holdPerspectiveBackHideHud, false);
			this.refresh = true;
		}).build());
		holdPerspectiveGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "hold_perspective.front.hide_hud", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.holdPerspectiveFrontHideHud.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.holdPerspectiveFrontHideHud, false);
			this.refresh = true;
		}).build());
		holdPerspectiveGridAdder.add(new EmptyWidget(20, 20), 2);
		holdPerspectiveGridAdder.add(new EmptyWidget(20, 20), 2);
		return holdPerspectiveGrid;
	}
	public Screen getRefreshScreen() {
		return new HoldPerspectiveConfigScreen(this.parentScreen, false, this.page);
	}
	public String getPageId() {
		return "hold_perspective";
	}
}