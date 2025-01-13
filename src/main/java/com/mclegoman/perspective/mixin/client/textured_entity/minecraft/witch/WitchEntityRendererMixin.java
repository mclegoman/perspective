/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.witch;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import com.mclegoman.perspective.client.entity.renderer.feature.EntityCapeFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.WitchEntityRenderer;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.render.entity.state.WitchEntityRenderState;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = WitchEntityRenderer.class)
public abstract class WitchEntityRendererMixin extends MobEntityRenderer<WitchEntity, WitchEntityRenderState, WitchEntityModel> {
	public WitchEntityRendererMixin(EntityRendererFactory.Context context, WitchEntityModel entityModel, float f) {
		super(context, entityModel, f);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new EntityCapeFeatureRenderer.Builder(this, new LivingEntityCapeModel(context.getPart(EntityModels.entityCape)), Identifier.of("perspective", "textures/entity/minecraft/witch/witch_cape.png")).offsetZ(0.18F).offsetY(0.025F).build());
	}
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/WitchEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(WitchEntityRenderState state, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(state, cir.getReturnValue()));
	}
}