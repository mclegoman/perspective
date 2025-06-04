/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.overlays;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.config.value.QualityToggle;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.hud.Overlays;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.screen.widget.ConfigButtonWidget;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;

public class OverlaysConfigScreen extends AbstractConfigScreen {
	public OverlaysConfigScreen(Screen parentScreen, int page) {
		super(parentScreen, page);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) this.gridAdder.add(createPageOne());
			else if (this.page == 2) this.gridAdder.add(createPageTwo());
			else shouldClose = true;
			postInit();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize zoom config screen: {}", error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createPageOne() {
		GridWidget overlaysGrid = new GridWidget();
		overlaysGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder overlaysGridAdder = overlaysGrid.createAdder(2);
		try {
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.version_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.versionOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.versionOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.position_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.positionOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.positionOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.time_overlay", new Object[]{Translation.getTimeOverlayTranslation(Data.getVersion().getID(), Overlays.getCurrentTimeOverlay())}), (button) -> {
				Overlays.cycleTimeOverlay(!hasShiftDown());
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.day_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.dayOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.dayOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.biome_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.biomeOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.biomeOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.looking_at_overlay", new Object[]{Translation.getQualityTranslation(Data.getVersion().getID(), PerspectiveConfig.config.lookingAtOverlay.value())}), (button) -> {
				PerspectiveConfig.config.lookingAtOverlay.setValue(switch (PerspectiveConfig.config.lookingAtOverlay.value()) {
					case off -> hasShiftDown() ? QualityToggle.fancy : QualityToggle.fast;
					case fast -> hasShiftDown() ? QualityToggle.off : QualityToggle.fancy;
					case fancy -> hasShiftDown() ? QualityToggle.fast : QualityToggle.off;
				}, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.deaths_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.deathsOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.deathsOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.totems_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.totemsOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.totemsOverlay, false);
			}).build());
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, "error occurred on overlays screen: " + error.getLocalizedMessage());
		}
		return overlaysGrid;
	}
	private GridWidget createPageTwo() {
		GridWidget overlaysGrid = new GridWidget();
		overlaysGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder overlaysGridAdder = overlaysGrid.createAdder(2);
		try {
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.cps_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.cpsOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.cpsOverlay, false);
			}).build());
			overlaysGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "overlays.armor_overlay", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.armorOverlay.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.armorOverlay, false);
			}).build());
			overlaysGridAdder.add(new EmptyWidget(20, 20), 2);
			overlaysGridAdder.add(new EmptyWidget(20, 20), 2);
			overlaysGridAdder.add(new EmptyWidget(20, 20), 2);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, "error occurred on overlays screen: " + error.getLocalizedMessage());
		}
		return overlaysGrid;
	}
	public Screen getRefreshScreen() {
		return new OverlaysConfigScreen(this.parentScreen, this.page);
	}
	public String getPageId() {
		return "overlays";
	}
	public int getMaxPage() {
		return 2;
	}
}