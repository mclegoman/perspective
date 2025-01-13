/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.textured_entity.minecraft.husk;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import com.mclegoman.perspective.client.entity.renderer.feature.EntityCapeFeatureRenderer;
import com.mclegoman.perspective.client.entity.renderer.feature.OverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HuskEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(priority = 100, value = HuskEntityRenderer.class)
public abstract class HuskEntityRendererMixin extends ZombieEntityRenderer {
	public HuskEntityRendererMixin(EntityRendererFactory.Context context) {
		super(context);
	}
	@Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, CallbackInfo ci) {
		this.addFeature(new OverlayFeatureRenderer<>(this, new ZombieEntityModel<>(context.getPart(EntityModels.zombieOverlay)), new ZombieEntityModel<>(context.getPart(EntityModels.babyZombieOverlay)), Identifier.of("textures/entity/zombie/husk_overlay.png")));
		this.addFeature(new EntityCapeFeatureRenderer.Builder(this, new LivingEntityCapeModel(context.getPart(EntityModels.entityCape)), Identifier.of("perspective", "textures/entity/minecraft/zombie/husk_cape.png")).build());
	}
	@Inject(at = @At("RETURN"), method = "getTexture(Lnet/minecraft/client/render/entity/state/ZombieEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
	private void perspective$getTexture(ZombieEntityRenderState entity, CallbackInfoReturnable<Identifier> cir) {
		cir.setReturnValue(TexturedEntity.getTexture(entity, cir.getReturnValue()));
	}
}