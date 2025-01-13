/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveTamedRenderState;
import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WolfEntityRenderState.class)
public class WolfEntityRenderStateMixin implements PerspectiveVariantRenderState, PerspectiveTamedRenderState {
	public String perspective$getVariantId() {
		return this.perspective$variantId;
	}
	public void perspective$setVariantId(String variantId) {
		this.perspective$variantId = variantId;
	}
	public boolean perspective$getTamed() {
		return perspective$tamed;
	}
	public void perspective$setTamed(boolean tamed) {
		this.perspective$tamed = tamed;
	}
	@Unique
	private String perspective$variantId;
	@Unique
	private boolean perspective$tamed;
}
