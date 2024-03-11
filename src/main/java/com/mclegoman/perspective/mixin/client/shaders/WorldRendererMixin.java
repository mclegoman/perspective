/*
    Perspective
    Contributor(s): Nettakrim, MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.shaders;

import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.shaders.Shader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V", ordinal = 0), method = "render")
	public void perspective$saveDepthOutlines(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Shader.depthFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
		ClientData.CLIENT.getFramebuffer().beginWrite(false);
	}
	@Inject(at = {
			@At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V", ordinal = 1),
			@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWorldBorder(Lnet/minecraft/client/render/Camera;)V", ordinal = 1)
	}, method = "render")
	public void perspective$saveDepth(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Shader.depthFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
		if (ClientData.CLIENT.options.getGraphicsMode().getValue().getId() <= GraphicsMode.FANCY.getId()) ClientData.CLIENT.getFramebuffer().beginWrite(false);
	}

	// WorldRenderer$Render$RenderLayer(ordinal = 3/5)
	// if (Shader.shouldRenderShader && Shader.translucentFramebuffer != null) {
	//     Shader.translucentFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
	//     Shader.translucentFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
	// }
	@Inject(at = {
			@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", ordinal = 3),
			@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderLayer(Lnet/minecraft/client/render/RenderLayer;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V", ordinal = 5)
	}, method = "render")
	public void perspective$renderLayer(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		if (Shader.shouldRenderShader()) {
			if (Shader.translucentFramebuffer != null) {
				Shader.translucentFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				Shader.translucentFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
			}
			if (Shader.entityFramebuffer != null) {
				Shader.entityFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				Shader.entityFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
			}
			if (Shader.particlesFramebuffer != null) {
				Shader.particlesFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				Shader.particlesFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
			}
			if (Shader.weatherFramebuffer != null) {
				Shader.weatherFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				Shader.weatherFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
			}
			if (Shader.cloudsFramebuffer != null) {
				Shader.cloudsFramebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				Shader.cloudsFramebuffer.copyDepthFrom(ClientData.CLIENT.getFramebuffer());
			}
		}
	}



	// Panorama Shader Renderer
	@Inject(method = "render", at = @At(value = "RETURN"))
	private void perspective$renderShaderPanorama(float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		if (ClientData.CLIENT.gameRenderer.isRenderingPanorama()) {
			if (Shader.shouldRenderShader()) {
				Shader.render(tickDelta, "game:panorama");
			}
		}
	}
}