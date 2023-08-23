/*
    Perspective
    Author: MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    License: CC-BY 4.0
*/

package com.mclegoman.perspective.mixin.client.compat;

import com.mclegoman.perspective.client.config.PerspectiveConfigHelper;
import com.mclegoman.perspective.client.config.screen.shaders.PerspectiveShadersConfigScreen;
import com.mclegoman.perspective.client.data.PerspectiveClientData;
import com.mclegoman.perspective.client.translation.PerspectiveTranslation;
import com.mclegoman.perspective.common.data.PerspectiveData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(priority = 10000, value = OptionsScreen.class)
public class PerspectiveOptionsScreen extends Screen {
    protected PerspectiveOptionsScreen(Text title) {
        super(title);
    }
    @Inject(method = "init", at = @At("TAIL"))
    private void perspective$renderOptions(CallbackInfo ci) {
        try {
            if ((boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_options_screen")) {
                this.addDrawableChild(ButtonWidget.builder(PerspectiveTranslation.getConfigTranslation("shaders"), (button) -> {
                    PerspectiveClientData.CLIENT.setScreen(new PerspectiveShadersConfigScreen(PerspectiveClientData.CLIENT.currentScreen, true, false));
                }).dimensions(this.width / 2 + 5, this.height / 6 + 17, 150, 20).build());
            }
        } catch (Exception e) {
            PerspectiveData.LOGGER.error(PerspectiveData.PREFIX + "An error occurred whilst trying to add super secret settings to the options screen.");
            PerspectiveData.LOGGER.error(e.getLocalizedMessage());
        }
    }
}