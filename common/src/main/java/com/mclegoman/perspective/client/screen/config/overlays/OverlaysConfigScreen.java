/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.overlays;

import fabric.com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hud.Overlays;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;

public class OverlaysConfigScreen extends AbstractConfigScreen {
	public OverlaysConfigScreen(Screen parentScreen, boolean refresh, int page) {
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
		GridWidget overlaysGrid = new GridWidget();
		overlaysGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder overlaysGridAdder = overlaysGrid.createAdder(2);
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.version_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.versionOverlay.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.versionOverlay, false);
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.position_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.positionOverlay.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.positionOverlay, false);
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.time_overlay", new Object[]{Translation.getTimeOverlayTranslation(Data.version.getID(), Overlays.getCurrentTimeOverlay())}), (button) -> {
			Overlays.cycleTimeOverlay(!hasShiftDown());
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.day_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.dayOverlay.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.dayOverlay, false);
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.biome_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.biomeOverlay.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.biomeOverlay, false);
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "overlays.cps_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), PerspectiveConfig.config.cpsOverlay.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.cpsOverlay, false);
			this.refresh = true;
		}).build());
		overlaysGridAdder.add(new EmptyWidget(20, 20), 2);
		return overlaysGrid;
	}
	public Screen getRefreshScreen() {
		return new OverlaysConfigScreen(this.parentScreen, false, this.page);
	}
	public String getPageId() {
		return "overlays";
	}
}