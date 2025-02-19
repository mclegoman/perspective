/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.hud;

import com.mclegoman.perspective.client.hud.Overlays;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;remove(Lnet/minecraft/entity/Entity$RemovalReason;)V"), method = "updatePostDeath")
	private void perspective$updatePostDeath(CallbackInfo ci) {
		Overlays.updateStats();
	}
	@Inject(at = @At("RETURN"), method = "init")
	private void perspective$init(CallbackInfo ci) {
		Overlays.updateStats();
	}
}