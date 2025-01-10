/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.pig;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.MuddyFlowerModel;
import com.mclegoman.perspective.client.entity.renderer.feature.MuddyFlowerFeatureRenderer;
import com.mclegoman.perspective.client.entity.renderer.feature.OverlayFeatureRenderer;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = PigEntityRenderer.class)
public abstract class PigEntityRendererMixin<T extends MobEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends MobEntityRenderer<T, S, M> {
	public PigEntityRendererMixin(EntityRendererFactory.Context context, M entityModel, float f) {
		super(context, entityModel, f);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new MuddyFlowerFeatureRenderer(this, new MuddyFlowerModel<>(context.getPart(EntityModels.pigMuddyFlower)), Identifier.of(Data.version.getID(), "textures/entity/minecraft/pig/muddy_flower.png")));
		this.addFeature(new OverlayFeatureRenderer(this, new PigEntityModel(context.getPart(EntityModels.pigOverlay)), new PigEntityModel(context.getPart(EntityModels.babyPigOverlay)), Identifier.of(Data.version.getID(), "textures/entity/minecraft/pig/pig_overlay.png")));
	}
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/PigEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(PigEntityRenderState renderState, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(renderState, cir.getReturnValue()));
	}
}