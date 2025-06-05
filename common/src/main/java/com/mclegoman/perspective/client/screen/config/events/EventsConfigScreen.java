/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.events;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.widget.ConfigButtonWidget;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;

public class EventsConfigScreen extends AbstractConfigScreen {
	public EventsConfigScreen(Screen parentScreen, int page) {
		super(parentScreen, page);
	}
	public void init() {
		try {
			super.init();
			if (this.page == 1) this.gridAdder.add(createPageOne());
			else shouldClose = true;
			postInit();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize {} config screen: {}", getPageTitle(), error));
			ClientData.minecraft.setScreen(this.parentScreen);
		}
	}
	private GridWidget createPageOne() {
		GridWidget eventsGrid = new GridWidget();
		eventsGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder eventsGridAdder = eventsGrid.createAdder(2);
		try {
			eventsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "PerspectiveEvents.april_fools_prank.allow", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.allowAprilFools.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.allowAprilFools, false);
			}).build());
			eventsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "PerspectiveEvents.april_fools_prank.force", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forceAprilFools.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.forceAprilFools, false);
			}).build());
			eventsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "PerspectiveEvents.halloween.allow", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.allowHalloween.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.allowHalloween, false);
			}).build());
			eventsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "PerspectiveEvents.halloween.force", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forceHalloween.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.forceHalloween, false);
			}).build());
			eventsGridAdder.add(ConfigButtonWidget.builder(() -> Translation.getConfigTranslation(Data.getVersion().getID(), "force_pride", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forcePride.value(), Translation.Type.ONFF)}), (button) -> {
				PerspectiveConfig.toggle(PerspectiveConfig.config.forcePride);
			}).width(304).build(), 2);
			eventsGridAdder.add(new EmptyWidget(20, 20), 2);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, "Error creating config/events/page1: " + error.getLocalizedMessage());
		}
		return eventsGrid;
	}
	public Screen getRefreshScreen() {
		return new EventsConfigScreen(this.parentScreen, this.page);
	}
	public String getPageId() {
		return "events";
	}
}