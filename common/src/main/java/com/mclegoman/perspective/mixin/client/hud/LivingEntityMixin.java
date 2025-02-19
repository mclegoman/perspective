/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.hud;

import com.mclegoman.perspective.client.hud.Overlays;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = LivingEntity.class)
public abstract class LivingEntityMixin {
	@Inject(at = @At("RETURN"), method = "tryUseDeathProtector")
	private void perspective$tryUseDeathProtector(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		Overlays.updateStats();
	}
}