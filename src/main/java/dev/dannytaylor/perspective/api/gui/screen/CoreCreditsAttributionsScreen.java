/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.gui.screen;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.gui.screen.config.CreditsAttributionScreen;
import com.mclegoman.luminance.client.gui.widget.AttributionsWidget;
import com.mclegoman.luminance.client.translation.Translation;
import dev.dannytaylor.perspective.api.gui.PerspectiveLogo;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

// TODO:
// This is temporary for 1.3.x
public class CoreCreditsAttributionsScreen extends CreditsAttributionScreen {
    public CoreCreditsAttributionsScreen(Screen parent, double scrollY, Translation.@Nullable Data splashText, boolean isPride) {
        super(parent, scrollY, splashText, isPride);
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

    public void initBody() {
        this.info = AttributionsWidget.get(ClientData.minecraft, this.width, this.layout.getContentHeight(), this.layout.getHeaderHeight(), 11, this.scrollY, FabricLoader.getInstance().getModContainer("perspective").orElse(null));
        this.layout.addToContents(this.info);
    }

    public void renderDevNotice(GuiGraphics context) {
    }

    public Screen getRefreshScreen() {
        return new CoreCreditsAttributionsScreen(this.parent, this.info != null ? this.info.scrollAmount() : this.scrollY, this.splashText, this.isPride);
    }
}
