/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.events;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.screen.config.AbstractConfigScreen;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;

public class EventsConfigScreen extends AbstractConfigScreen {
	public EventsConfigScreen(Screen parentScreen, boolean refresh, int page) {
		super(parentScreen, refresh, page);
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
		eventsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "events.april_fools_prank.allow", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.allowAprilFools.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.allowAprilFools, false);
			refresh = true;
		}).build());
		eventsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "events.april_fools_prank.force", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forceAprilFools.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.forceAprilFools, false);
			refresh = true;
		}).build());
		eventsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "events.halloween.allow", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.allowHalloween.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.allowHalloween, false);
			refresh = true;
		}).build());
		eventsGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "events.halloween.force", new Object[]{Translation.getVariableTranslation(Data.getVersion().getID(), PerspectiveConfig.config.forceHalloween.value(), Translation.Type.ONFF)}), (button) -> {
			PerspectiveConfig.toggle(PerspectiveConfig.config.forceHalloween, false);
			refresh = true;
		}).build());
		eventsGridAdder.add(new EmptyWidget(20, 20), 2);
		eventsGridAdder.add(new EmptyWidget(20, 20), 2);
		return eventsGrid;
	}
	public Screen getRefreshScreen() {
		return new EventsConfigScreen(this.parentScreen, false, this.page);
	}
	public String getPageId() {
		return "events";
	}
}