/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.hud;

import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.screen.config.ConfigScreen;
import com.mclegoman.perspective.client.screen.widget.LogoButtonWidget;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(priority = 100, value = VideoOptionsScreen.class)
public abstract class VideoOptionsScreenMixin extends GameOptionsScreen {
	@Unique
	private ButtonWidget perspective$widget;
	public VideoOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}
	protected void initFooter() {
		super.initFooter();
		perspective$widget = LogoButtonWidget.create(() -> {
			String path = FabricLoader.getInstance().getModContainer(Data.getVersion().getID()).get().getMetadata().getIconPath(64).orElse(null);
			if (CompatHelper.shouldOverrideModMenuIcon(Data.getVersion().getID())) path = CompatHelper.getOverrideModMenuIcon(Data.getVersion().getID());
			if (path != null) {
				path = path.replaceFirst("assets/", "").replaceFirst("/", ":");
			}
			return path != null ? Identifier.of(path) : null;
		}, (button) -> ClientData.minecraft.setScreen(new ConfigScreen(this, 1))).width(20).position(2, perspective$getY()).tooltip(Tooltip.of(Translation.getTranslation(Data.getVersion().getID(), "config", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name"), Translation.getConfigTranslation(Data.getVersion().getID(), "config")}))).build();
		this.addDrawableChild(perspective$widget);
	}
	protected void refreshWidgetPositions() {
		super.refreshWidgetPositions();
		perspective$widget.setPosition(2, perspective$getY());
	}
	@Unique
	private int perspective$getY() {
		assert ClientData.minecraft.currentScreen != null;
		return ClientData.minecraft.currentScreen.height - (this.layout.getFooterHeight() / 2) - 10;
	}
}