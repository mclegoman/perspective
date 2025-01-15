/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveGoatRenderState;
import net.minecraft.client.render.entity.state.GoatEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GoatEntityRenderState.class)
public class GoatEntityRenderStateMixin implements PerspectiveGoatRenderState {
	public boolean perspective$getScreaming() {
		return this.perspective$screaming;
	}
	public void perspective$setScreaming(boolean screaming) {
		this.perspective$screaming = screaming;
	}
	@Unique
	private boolean perspective$screaming;
}
