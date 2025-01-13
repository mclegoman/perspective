/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.boat;

import com.mclegoman.perspective.client.entity.states.PerspectiveBoatStateGetter;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.BoatEntityRenderState;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(priority = 100, value = net.minecraft.client.render.entity.BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModelLayer;id()Lnet/minecraft/util/Identifier;"))
	private Identifier perspective$replaceTexture(EntityModelLayer layer) {
		BoatEntityRenderState state = ((PerspectiveBoatStateGetter) this).perspective$getState();
		Identifier fallback = layer.id().withPath((path) -> "textures/entity/" + path + ".png");
		return state != null ? TexturedEntity.getTexture(state, fallback) : fallback;
	}
}