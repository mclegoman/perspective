/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveVariantRenderState;
import net.minecraft.client.render.entity.state.FrogEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FrogEntityRenderState.class)
public class FrogEntityRenderStateMixin implements PerspectiveVariantRenderState {
	public String perspective$getVariantId() {
		return this.perspective$variantId;
	}
	public void perspective$setVariantId(String variantId) {
		this.perspective$variantId = variantId;
	}
	@Unique
	private String perspective$variantId;
}
