/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.google.gson.JsonObject;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.Optional;

public class CowFlowerFeatureRenderer extends FeatureRenderer<LivingEntityRenderState, CowEntityModel> {
	private final BlockRenderManager blockRenderManager;
	public CowFlowerFeatureRenderer(FeatureRendererContext<LivingEntityRenderState, CowEntityModel> context, BlockRenderManager blockRenderManager) {
		super(context);
		this.blockRenderManager = blockRenderManager;
	}
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntityRenderState state, float f, float g) {
		if (!state.baby) {
			boolean bl = state.hasOutline && state.invisible;
			if (!state.invisible || bl) {
				BlockState blockState = getBlockstate(state);
				int j = LivingEntityRenderer.getOverlay(state, 0.0F);
				BakedModel bakedModel = this.blockRenderManager.getModel(blockState);
				matrixStack.push();
				matrixStack.translate(0.2F, -0.35F, 0.5F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				matrixStack.scale(-1.0F, -1.0F, 1.0F);
				matrixStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderBlockstate(matrixStack, vertexConsumerProvider, i, bl, blockState, j, bakedModel);
				matrixStack.pop();
				matrixStack.push();
				matrixStack.translate(0.2F, -0.35F, 0.5F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(42.0F));
				matrixStack.translate(0.1F, 0.0F, -0.6F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				matrixStack.scale(-1.0F, -1.0F, 1.0F);
				matrixStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderBlockstate(matrixStack, vertexConsumerProvider, i, bl, blockState, j, bakedModel);
				matrixStack.pop();
				matrixStack.push();
				this.getContextModel().getHead().rotate(matrixStack);
				matrixStack.translate(0.0F, -0.7F, -0.2F);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-78.0F));
				matrixStack.scale(-1.0F, -1.0F, 1.0F);
				matrixStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderBlockstate(matrixStack, vertexConsumerProvider, i, bl, blockState, j, bakedModel);
				matrixStack.pop();
			}
		}
	}
	private void renderBlockstate(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean renderAsModel, BlockState blockState, int overlay, BakedModel model) {
		if (renderAsModel) this.blockRenderManager.getModelRenderer().render(matrices.peek(), vertexConsumers.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), blockState, model, 0.0F, 0.0F, 0.0F, light, overlay);
		else this.blockRenderManager.renderBlockAsEntity(blockState, matrices, vertexConsumers, light, overlay);
	}
	private BlockState getBlockstate(LivingEntityRenderState state) {
		if (state != null) {
			Optional<TexturedEntityData> entityData = TexturedEntity.getEntity(state);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("variants")) {
						JsonObject variants = JsonHelper.getObject(entitySpecific, "variants");
						if (variants != null) {
							// 1.21.5 adds cow variants, so i'm adding a default "temperate" variant in the mean time.
							String cowVariant = "temperate";
							if (variants.has(cowVariant)) {
								JsonObject typeRegistry = JsonHelper.getObject(variants, cowVariant);
								if (typeRegistry != null) {
									boolean enabled = JsonHelper.getBoolean(typeRegistry, "enabled", true);
									if (enabled) {
										if (typeRegistry.has("block") || typeRegistry.has("mushroom")) {
											JsonObject block = typeRegistry.has("block") ? JsonHelper.getObject(typeRegistry, "block") : JsonHelper.getObject(typeRegistry, "mushroom");
											if (block.has("identifier")) {
												Identifier blockId = IdentifierHelper.identifierFromString(JsonHelper.getString(block, "identifier", "minecraft:air"));
												if (Registries.BLOCK.containsId(blockId)) return Registries.BLOCK.get(blockId).getDefaultState();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return Blocks.AIR.getDefaultState();
	}
}
