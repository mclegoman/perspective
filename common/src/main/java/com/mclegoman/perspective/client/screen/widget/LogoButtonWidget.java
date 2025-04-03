/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;

public class LogoButtonWidget extends ButtonWidget {
	private final Callable<Identifier> texture;
	protected LogoButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier, Callable<Identifier> texture) {
		super(x, y, width, height, message, onPress, narrationSupplier);
		this.texture = texture;
	}
	public static Builder create(Callable<Identifier> texture, PressAction onPress) {
		return new Builder(texture, onPress);
	}
	@Environment(EnvType.CLIENT)
	public static class Builder {
		private final Callable<Identifier> texture;
		private final Text message;
		private final PressAction onPress;
		@Nullable
		private Tooltip tooltip;
		private int x;
		private int y;
		private int width = 150;
		private int height = 20;
		private NarrationSupplier narrationSupplier;

		public Builder(Callable<Identifier> texture, PressAction onPress) {
			this.narrationSupplier = ButtonWidget.DEFAULT_NARRATION_SUPPLIER;
			this.message = Text.of("");
			this.texture = texture;
			this.onPress = onPress;
		}

		public LogoButtonWidget.Builder position(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public LogoButtonWidget.Builder width(int width) {
			this.width = width;
			return this;
		}

		public LogoButtonWidget.Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public LogoButtonWidget.Builder dimensions(int x, int y, int width, int height) {
			return this.position(x, y).size(width, height);
		}

		public LogoButtonWidget.Builder tooltip(@Nullable Tooltip tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public LogoButtonWidget.Builder narrationSupplier(NarrationSupplier narrationSupplier) {
			this.narrationSupplier = narrationSupplier;
			return this;
		}

		public LogoButtonWidget build() {
			LogoButtonWidget buttonWidget = new LogoButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, this.narrationSupplier, this.texture);
			buttonWidget.setTooltip(this.tooltip);
			return buttonWidget;
		}
	}
	protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		super.renderWidget(context, mouseX, mouseY, delta);
		try {
			Identifier texture = this.texture.call();
			if (texture != null) {
				int i = this.active ? ColorHelper.getWhite(this.alpha) : 10526880;
				context.drawTexture(RenderLayer::getGuiTextured, texture, this.getX() + 1, this.getY() + 1, 0.0F, 0.0F, this.getWidth() - 2, this.getHeight() - 2, this.getWidth() - 2, this.getHeight() - 2, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
			}
		} catch (Exception ignored) {
		}
	}
}
