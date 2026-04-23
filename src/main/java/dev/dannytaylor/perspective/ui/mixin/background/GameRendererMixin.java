/*
    User Interface
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.ui.mixin.background;

import dev.dannytaylor.perspective.ui.background.BackgroundRegistry;
import dev.dannytaylor.perspective.ui.events.UserInterfaceExecute;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 100)
public abstract class GameRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "processBlurEffect", at = @At("HEAD"), cancellable = true)
    private void perspective$processBlurEffect(CallbackInfo ci) {
        if (!BackgroundRegistry.getBackground().getBlurRenderer().renderBlur().call()) ci.cancel();
        UserInterfaceExecute.onBlur(this.minecraft);
    }
}
