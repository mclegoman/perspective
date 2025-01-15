/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveLivingRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements PerspectiveLivingRenderState {
	public float perspective$getPrevBodyYaw() {
		return this.perspective$prevBodyYaw;
	}
	public void perspective$setPrevBodyYaw(float prevBodyYaw) {
		this.perspective$prevBodyYaw = prevBodyYaw;
	}
	public boolean perspective$getChestEmpty() {
		return this.perspective$chestEmpty;
	}
	public void perspective$setChestEmpty(boolean chestEmpty) {
		this.perspective$chestEmpty = chestEmpty;
	}
	@Unique
	private float perspective$prevBodyYaw;
	@Unique
	private boolean perspective$chestEmpty;
}
