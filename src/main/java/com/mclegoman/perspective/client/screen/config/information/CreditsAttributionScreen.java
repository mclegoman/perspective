/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.information;

import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.logo.PerspectiveLogo;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;

public class CreditsAttributionScreen extends com.mclegoman.luminance.client.screen.config.information.CreditsAttributionScreen {
	private final boolean overrideLogo;
	public CreditsAttributionScreen(Screen parentScreen, Identifier creditsJsonId) {
		super(parentScreen, PerspectiveLogo.isPride(), creditsJsonId);
		this.overrideLogo = true;
	}
	public CreditsAttributionScreen(Screen parentScreen) {
		super(parentScreen, PerspectiveLogo.isPride());
		this.overrideLogo = false;
	}
	protected void renderLogo(DrawContext context) {
		if (this.overrideLogo) PerspectiveLogo.renderLogo(context, ClientData.minecraft.getWindow().getScaledWidth() / 2 - 128, ClientData.minecraft.getWindow().getScaledHeight() + 2, 256, 64, PerspectiveLogo.getLogoTexture(false));
		else super.renderLogo(context);
	}
}
