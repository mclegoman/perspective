/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.PerspectiveRenderState;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
	@Inject(method = "updateRenderState", at = @At("TAIL"))
	private void perspective$updateRenderState(T entity, S state, float tickDelta, CallbackInfo ci) {
		((PerspectiveRenderState)state).perspective$setUUID(entity.getUuid());
		((PerspectiveRenderState)state).perspective$setType(entity.getType());
		((PerspectiveRenderState)state).perspective$setStringName(entity.getCustomName() != null ? entity.getCustomName().getLiteralString() : "default");
	}
}
