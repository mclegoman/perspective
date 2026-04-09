/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.mixin.game;

import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SkyRenderer.class)
public class SkyRenderMixin {
    @ModifyArgs(method = "renderStars", at = @At(value = "INVOKE", target = "Lorg/joml/Vector4f;<init>(FFFF)V"))
    private void perspective$renderStars(Args args) {
        for (int i = 0; i < args.size(); i++) args.set(i, (float)args.get(i) * PerspectiveConfig.config.hide.starBrightnessMultiplier.value());
    }
}
