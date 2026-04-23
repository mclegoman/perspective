/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.gui.screen.config;

import com.mclegoman.luminance.client.gui.screen.AbstractScrollableListScreen;
import com.mclegoman.luminance.client.gui.screen.CursorableStringWidget;
import com.mclegoman.luminance.client.gui.widget.ListWidget;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.DateHelper;
import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.api.gui.PerspectiveLogo;
import dev.dannytaylor.perspective.api.gui.screen.CoreCreditsAttributionsScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends AbstractScrollableListScreen {
    public ListWidget list;
    private boolean refresh;

    public ConfigScreen(Screen parent) {
        super(parent);
    }

    public ConfigScreen(Screen parent, double scrollY) {
        super(parent, scrollY);
    }

    public ConfigScreen(Screen parent, double scrollY, @Nullable Translation.Data splashText, boolean isPride) {
        super("", parent, scrollY, splashText, isPride);
    }

    public static ConfigScreen open(Screen screen) {
        return new ConfigScreen(screen, 0, null, DateHelper.isPride());
    }

    public void initBody() {
        this.list = new ListWidget(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 22, getEntries(), this.scrollY);
        this.layout.addToContents(this.list);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.refresh) {
            ClientData.minecraft.setScreen(getRefreshScreen());
            this.refresh = false;
        }
    }

    @Override
    public void onClose() {
        for (CoreEvents.PriorityEntry<ConfigGroup> configGroup : CoreEvents.ConfigGroups.registry.values()) configGroup.entry().save();
        super.onClose();
    }

    @Override
    public void initHeader() {
        // TODO: Update logo renderer.
        PerspectiveLogo.Widget logo = new PerspectiveLogo.Widget(this.splashText != null, this.splashText, this.isPride);
        this.layout.addToHeader(logo, (positioner) -> {
            this.layout.setHeaderHeight(logo.getHeight());
            positioner.paddingTop(11);
        });
    }

    public List<ListWidget.ListEntry> getEntries() {
        List<ListWidget.ListEntry> widgets = new ArrayList<>();
        CoreEvents.getConfigGroups().forEach((id, configGroup) -> {
            widgets.add(new ListWidget.ListEntry(new CursorableStringWidget(Components.guiTranslatable(id), ClientData.minecraft.font)));
            widgets.addAll(configGroup.entry().getWidgets());
        });

        widgets.add(new ListWidget.ListEntry(new CursorableStringWidget(Translation.getConfigTranslation(Data.getVersion().getID(), "information"), ClientData.minecraft.font)));
        widgets.add(new ListWidget.ListEntry(Button.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "information.source_code").append(getExternal()), ConfirmLinkScreen.confirmLink(this, "https://github.com/mclegoman/perspective")).width(304).build(),
                Button.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "information.report").append(getExternal()), ConfirmLinkScreen.confirmLink(this, "https://github.com/mclegoman/perspective/issues")).width(304).build()));
        widgets.add(new ListWidget.ListEntry(Button.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "credits_attribution").append(getMore()), button -> ClientData.minecraft.setScreen(new CoreCreditsAttributionsScreen(getRefreshScreen(), 0, splashText, isPride))).width(304).build()));
        return widgets;
    }

    public void initFooter() {
        LinearLayout footerLayout = LinearLayout.horizontal().spacing(4);
        footerLayout.addChild(Button.builder(Translation.getConfigTranslation(Data.getVersion().getID(), "reset"), (button) -> {
            CoreEvents.ConfigGroups.registry.forEach((identifier, configGroup) -> {
                if (configGroup.entry().resetOnBulkReset()) configGroup.entry().reset();
            });
            this.refresh = true;
        }).build());
        footerLayout.addChild(Button.builder(Components.configTranslatable(CoreClient.getMod().idOf("close", true)), (button) -> this.onClose()).build());
        this.layout.addToFooter(footerLayout);
    }

    public Screen getRefreshScreen() {
        return new ConfigScreen(this.parent, this.list != null ? this.list.scrollAmount() : scrollY, this.splashText, this.isPride);
    }

    public void renderDevNotice(GuiGraphics context) {
    }
}
