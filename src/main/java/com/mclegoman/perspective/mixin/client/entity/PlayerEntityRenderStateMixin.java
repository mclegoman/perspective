/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectivePlayerRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PerspectivePlayerRenderState {
	@Override
	public void perspective$setRandom(Random random) {
		this.perspective$random = random;
	}
	@Override
	public Random perspective$getRandom() {
		return this.perspective$random;
	}
	@Override
	public boolean perspective$getBlinking() {
		return this.perspective$blinking;
	}
	@Override
	public void perspective$setBlinking(boolean blinking) {
		this.perspective$blinking = blinking;
	}
	@Unique
	private boolean perspective$blinking;
	@Unique
	private Random perspective$random;
}
