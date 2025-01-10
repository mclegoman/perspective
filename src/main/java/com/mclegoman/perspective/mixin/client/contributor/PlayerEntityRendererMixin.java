/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.contributor;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	private void perspective$init(EntityRendererFactory.Context context, boolean slim, CallbackInfo ci) {
		//this.addFeature(new ContributorHeadFeatureRenderer<>(this, context.getModelLoader(), context.getHeldItemRenderer()));
		//this.addFeature(new ContributorOverlayFeatureRenderer<>(this, new PlayerEntityModel<>(context.getPart(slim ? EntityModels.contributorOverlaySlim : EntityModels.contributorOverlayWide), slim)));
	}
}