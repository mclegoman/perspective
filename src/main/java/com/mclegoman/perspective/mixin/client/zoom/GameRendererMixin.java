/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.zoom;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.zoom.Zoom;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow
	public abstract boolean isRenderingPanorama();

	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;getFov(Lnet/minecraft/client/render/Camera;FZ)D"), method = "renderHand")
	private double perspective$renderHand(double fov) {
		return Zoom.fov;
	}
	@Inject(method = "updateFovMultiplier", at = @At("TAIL"))
	private void perspective$updateFovMultiplier(CallbackInfo ci) {
		Zoom.updateMultiplier();
	}
	@ModifyReturnValue(method = "getFov", at = @At("RETURN"))
	private double perspective$getFov(double fov, Camera camera, float tickDelta, boolean changingFov) {
		Zoom.fov = fov;
		double newFOV = fov;
		if (!this.isRenderingPanorama()) {
			newFOV *= MathHelper.lerp(tickDelta, Zoom.getPrevMultiplier(), Zoom.getMultiplier());
		}
		Zoom.zoomFOV = Zoom.Logarithmic.getLimitFOV(newFOV);
		return Zoom.zoomFOV;
	}
	@ModifyExpressionValue(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;"))
	private Object perspective$getDamageTiltStrength(Object value) {
		return (value instanceof Double) ? ((Double)value) * Math.max(Zoom.getMultiplier(), 0.001) : value;
	}
	@ModifyExpressionValue(method = "tiltViewWhenHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDamageTiltYaw()F"))
	private float perspective$getDamageTiltYaw(float value) {
		return (float) (value * Math.max(Zoom.getMultiplier(), 0.001));
	}
	@Inject(method = "bobView", at = @At(value = "HEAD"), cancellable = true)
	private void perspective$bobViewStrideDistance(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		if (ClientData.minecraft.player != null) {
			float f = ClientData.minecraft.player.horizontalSpeed - ClientData.minecraft.player.prevHorizontalSpeed;
			float g = -(ClientData.minecraft.player.horizontalSpeed + f * tickDelta);
			float h = (float) (MathHelper.lerp(tickDelta, ClientData.minecraft.player.prevStrideDistance, ClientData.minecraft.player.strideDistance) * Math.max(Zoom.getMultiplier(), 0.001));
			matrices.translate(MathHelper.sin(g * 3.1415927F) * h * 0.5F, -Math.abs(MathHelper.cos(g * 3.1415927F) * h), 0.0F);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(g * 3.1415927F) * h * 3.0F));
			matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(Math.abs(MathHelper.cos(g * 3.1415927F - 0.2F) * h) * 5.0F));
		}
		ci.cancel();
	}
}