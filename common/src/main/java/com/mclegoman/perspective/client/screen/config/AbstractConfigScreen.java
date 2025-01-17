/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.config.LuminanceConfigHelper;
import com.mclegoman.perspective.client.config.PerspectiveDefaultConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.keybindings.Keybindings;
import com.mclegoman.perspective.client.logo.PerspectiveLogo;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.update.Update;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractConfigScreen extends Screen {
	protected int page;
	protected final Screen parentScreen;
	protected final GridWidget grid;
	protected GridWidget.Adder gridAdder;
	protected boolean refresh;
	protected boolean shouldClose;
	public AbstractConfigScreen(Screen parentScreen, boolean refresh, int page) {
		super(Text.literal(""));
		this.grid = new GridWidget();
		this.parentScreen = parentScreen;
		this.refresh = refresh;
		this.page = page;
	}
	public void init() {
		this.grid.getMainPositioner().alignHorizontalCenter().margin(getGridMargin());
		this.gridAdder = grid.createAdder(1);
		this.gridAdder.add(new EmptyWidget(20, 20), 1);
		this.gridAdder.add(new EmptyWidget(20, 20), 1);
		this.gridAdder.add(new EmptyWidget(20, 20), 1);
	}
	public void postInit() {
		this.gridAdder.add(createFooter());
		this.grid.refreshPositions();
		this.grid.forEachChild(this::addDrawableChild);
		initTabNavigation();
	}
	public void tick() {
		try {
			if (this.isDefaults) {
				this.defaultsTicksRemaining--;
				if (this.defaultsTicksRemaining <= 0) {
					this.isDefaults = false;
					this.defaultsTicksRemaining = 0;
				}
			}
			if (this.refresh) ClientData.minecraft.setScreen(getRefreshScreen());
			if (this.shouldClose) {
				setParentScreen();
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to tick perspective$config screen: {}", error));
		}
	}
	protected void setParentScreen() {
		ClientData.minecraft.setScreen(this.parentScreen);
	}
	protected GridWidget createFooter() {
		GridWidget footerGrid = new GridWidget();
		footerGrid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder footerGridAdder = footerGrid.createAdder(3);
		footerGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "reset"), (button) -> {
			PerspectiveConfig.reset(false);
			this.refresh = true;
		}).build());
		footerGridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "back"), (button) -> {
			if (this.page <= 1) {
				this.shouldClose = true;
			} else {
				this.page -= 1;
				this.refresh = true;
			}
		}).width(73).build());
		ButtonWidget nextButtonWidget = ButtonWidget.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "next"), (button) -> {
			if (!(this.page >= getMaxPage())) {
				this.page += 1;
				this.refresh = true;
			}
		}).width(73).build();
		if (this.page >= getMaxPage()) nextButtonWidget.active = false;
		footerGridAdder.add(nextButtonWidget);
		return footerGrid;
	}
	public void initTabNavigation() {
		SimplePositioningWidget.setPos(this.grid, getNavigationFocus());
	}
	public Text getNarratedTitle() {
		return ScreenTexts.joinSentences();
	}
	public boolean shouldCloseOnEsc() {
		return false;
	}
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == KeyBindingHelper.getBoundKeyOf(Keybindings.openConfig).getCode()) {
			if (page <= 1) {
				this.shouldClose = true;
			} else {
				this.page -= 1;
				this.refresh = true;
			}
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_F5) {
			Update.checkForUpdates(Data.getVersion(), true);
			this.refresh = true;
		}
		if (hasControlDown() && keyCode == GLFW.GLFW_KEY_S) {
			PerspectiveDefaultConfig.setDefaults(true);
			this.setDefaults("defaults.saved");
		}
		if (hasControlDown() && hasAltDown() && keyCode == GLFW.GLFW_KEY_D) {
			LuminanceConfigHelper.reset(PerspectiveDefaultConfig.config, true);
			this.setDefaults("defaults.reset");
		}
		return super.keyReleased(keyCode, scanCode, modifiers);
	}
	private boolean isDefaults;
	private int defaultsTicksRemaining;
	private String defaultsType;
	private void setDefaults(String type) {
		this.isDefaults = true;
		this.defaultsTicksRemaining = 20;
		this.defaultsType = type;
	}
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
		if (this.isDefaults) context.drawTextWithShadow(textRenderer, Translation.getConfigTranslation(Data.getVersion().getID(), this.defaultsType), this.width - textRenderer.getWidth(Translation.getConfigTranslation(Data.getVersion().getID(), this.defaultsType)) - 2, 2, 0xFFFFFF);
		context.drawTextWithShadow(textRenderer, Translation.getTranslation(Data.getVersion().getID(), "version", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name", new Formatting[]{Formatting.WHITE}), Translation.getText(Data.getVersion().getFriendlyString(), false, new Formatting[]{Formatting.WHITE})}), 2, this.height - 10, 0xFFFFFF);
		Text licenceText = Translation.getTranslation(Data.getVersion().getID(), "license", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name", new Formatting[]{Formatting.WHITE}), Translation.getText(Data.getVersion().getFriendlyString(false), false, new Formatting[]{Formatting.WHITE})});
		context.drawTextWithShadow(textRenderer, licenceText, this.width - this.textRenderer.getWidth(licenceText) - 2, this.height - 10, 0xFFFFFF);
		getLogoWidget(this.width / 2 - 128, 30, getExperimental()).renderWidget(context, mouseX, mouseY, delta);
		context.drawCenteredTextWithShadow(textRenderer, getPageTitle(), this.width / 2, 78, 0xFFFFFF);
		if (isBeingReworked()) context.drawCenteredTextWithShadow(textRenderer, Translation.getConfigTranslation(Data.getVersion().getID(), "warning.reworked", new Object[]{Translation.getConfigTranslation(Data.getVersion().getID(), getReworkedId())}, new Formatting[]{Formatting.RED, Formatting.BOLD}), ClientData.minecraft.getWindow().getScaledWidth() / 2, 10, 0xFFFFFF);
		if (canShowUpdate() && isUpdateAvailable()) context.drawTextWithShadow(textRenderer, Translation.getConfigTranslation(Data.getVersion().getID(), "update.title"), 2, 2, 0xFFAA00);
	}
	public Screen getRefreshScreen() {
		return this;
	}
	public Text getPageTitle() {
		return !getPageId().isEmpty() ? Translation.getConfigTranslation(Data.getVersion().getID(), getPageId()) : Text.empty();
	}
	public String getPageId() {
		return "";
	}
	public int getMaxPage() {
		return 1;
	}
	public int getGridMargin() {
		return 2;
	}
	public boolean getExperimental() {
		return false;
	}
	public PerspectiveLogo.Widget getLogoWidget(int x, int y, boolean experimental) {
		return new PerspectiveLogo.Widget(x, y, experimental);
	}
	public boolean isBeingReworked() {
		return false;
	}
	public String getReworkedId() {
		return getPageId();
	}
	public boolean canShowUpdate() {
		return true;
	}
	public boolean isUpdateAvailable() {
		return Update.isNewerVersionFound();
	}

	public void resize(MinecraftClient client, int width, int height) {
		super.resize(client, width, height);
		this.refresh = true;
	}
}
