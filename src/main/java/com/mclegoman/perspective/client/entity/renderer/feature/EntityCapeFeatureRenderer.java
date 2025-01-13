/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.renderer.feature;

import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.entity.states.PerspectiveLivingRenderState;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.EntityModels;
import com.mclegoman.perspective.client.entity.model.LivingEntityCapeModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class EntityCapeFeatureRenderer<T extends LivingEntityRenderState> extends FeatureRenderer<T, LivingEntityCapeModel<T>> {
	private final LivingEntityCapeModel<T> model;
	private final Identifier capeTexture;
	private final float scale;
	private final float offsetX;
	private final float offsetY;
	private final float offsetZ;
	private final Quaternionf rotation;
	private EntityCapeFeatureRenderer(FeatureRendererContext<T, LivingEntityCapeModel<T>> featureRendererContext, LivingEntityCapeModel<T> model, Identifier capeTexture, float offsetX, float offsetY, float offsetZ, Quaternionf rotation, float scale) {
		super(featureRendererContext);
		this.model = model;
		this.capeTexture = capeTexture;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.rotation = rotation;
		this.scale = scale;
	}
	public static class Builder<T extends LivingEntityRenderState> {
		FeatureRendererContext<T, LivingEntityCapeModel<T>> featureRendererContext;
		LivingEntityCapeModel<T> model;
		Identifier capeTexture;
		float scale = 1.0F;
		float offsetX = 0.0F;
		float offsetY = 0.0F;
		float offsetZ = 0.125F;
		Quaternionf rotation = new Quaternionf();
		public Builder(FeatureRendererContext<T, LivingEntityCapeModel<T>> featureRendererContext, LivingEntityCapeModel<T> model, Identifier capeTexture) {
			this.featureRendererContext = featureRendererContext;
			this.model = model;
			this.capeTexture = capeTexture;
		}
		public Builder scale(float scale) {
			this.scale = scale;
			return this;
		}
		public Builder offsetX(float x) {
			this.offsetX = x;
			return this;
		}
		public Builder offsetY(float y) {
			this.offsetY = y;
			return this;
		}
		public Builder offsetZ(float z) {
			this.offsetZ = z;
			return this;
		}
		public Builder rotation(Quaternionf rotation) {
			this.rotation = rotation;
			return this;
		}
		public EntityCapeFeatureRenderer<T> build() {
			return new EntityCapeFeatureRenderer<>(this.featureRendererContext, this.model, this.capeTexture, offsetX, offsetY, offsetZ, rotation, scale);
		}
	}
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light, T state, float limbAngle, float limbDistance) {
		float delta = ClientData.minecraft.getRenderTickCounter().getTickDelta(true);
		matrixStack.push();
		if (state.baby) {
			matrixStack.scale(0.5F, 0.5F, 0.5F);
			matrixStack.translate(0.0F, 1.5F, 0.0F);
		}
		matrixStack.scale(this.scale, this.scale, this.scale);
		matrixStack.translate(this.offsetX, this.offsetY, this.offsetZ);
		matrixStack.multiply(rotation);
		double g = (Math.cos(EntityModels.getEntityCapeY() * (2.0F * 3.141592653589793F / 80.0F)) + 1.0F) * 0.48F;
		float i = MathHelper.lerpAngleDegrees(delta, ((PerspectiveLivingRenderState)state).perspective$getPrevBodyYaw(), state.bodyYaw);
		double j = MathHelper.sin(i * 0.017453292F);
		double k = -MathHelper.cos(i * 0.017453292F);
		float l = MathHelper.clamp((float)g * 10.0F, -6.0F, 32.0F);
		float m = MathHelper.clamp((float)(0.0F * j + 0.0F * k) * 100.0F, 0.0F, 150.0F);
		float n = MathHelper.clamp((float)(0.0F * k - 0.0F * j) * 100.0F, -20.0F, 20.0F);
		if (state.sneaking) l += 25.0F;
		matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(6.0F + m / 2.0F + l));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(n / 2.0F));
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - n / 2.0F));

		this.model.renderCape(matrixStack, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TexturedEntity.getTexture(state, "", "_cape", capeTexture))), state, light, OverlayTexture.DEFAULT_UV);
		matrixStack.pop();
	}
}
