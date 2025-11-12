/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.contributor.Contributor;
import com.mclegoman.perspective.client.entity.entity.PerspectivePlayerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PerspectivePlayerEntity {
	@Shadow public abstract GameProfile getGameProfile();
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "tick", at = @At("TAIL"))
	private void perspective$updateRenderState(CallbackInfo ci) {
		if (Contributor.canBlink(this.getGameProfile().getId().toString())) perspective$setPrevBlink(perspective$getPrevBlink() + 1);
	}
	@Override
	public float perspective$getPrevBlink() {
		return this.perspective$prevBlink;
	}
	@Override
	public void perspective$setPrevBlink(float blink) {
		this.perspective$prevBlink = blink;
	}
	@Unique
	private float perspective$prevBlink;
}
