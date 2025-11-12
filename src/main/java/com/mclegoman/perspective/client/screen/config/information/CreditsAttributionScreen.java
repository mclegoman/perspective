package com.mclegoman.perspective.client.screen.config.information;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.gui.screen.AbstractScrollableScreen;
import com.mclegoman.perspective.client.logo.PerspectiveLogo;
import com.mclegoman.perspective.client.logo.SplashesDataloader;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

public class CreditsAttributionScreen extends AbstractScrollableScreen {
    public InfoWidget info;

    public CreditsAttributionScreen(Screen parent) {
        this(parent, 0);
    }

    public CreditsAttributionScreen(Screen parent, double scrollY) {
        super("credits_attribution", parent, scrollY, SplashesDataloader.getSplashText(), PerspectiveLogo.isPride());
    }

    public void initHeader() {
        PerspectiveLogo.Widget logo = new PerspectiveLogo.Widget(0, 0, false);
        this.layout.addHeader(logo, (positioner) -> {
            this.layout.setHeaderHeight(logo.getHeight());
            positioner.marginTop(11);
        });
    }

    public void initBody() {
        this.info = new InfoWidget(ClientData.minecraft, Identifier.of(Data.getVersion().getID(), "texts/info.json"), this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 11, this.scrollY);
        this.layout.addBody(this.info);
    }

    public Screen getRefreshScreen() {
        return new CreditsAttributionScreen(this.parent, this.info != null ? this.info.getScrollY() : this.scrollY);
    }
}