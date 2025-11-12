/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.widget;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Callable;

public class ConfigButtonWidget extends ButtonWidget {
	private final Callable<Text> callableMessage;
	private final Callable<Tooltip> callableTooltip;
	protected ConfigButtonWidget(int x, int y, int width, int height, Callable<Text> message, PressAction onPress, NarrationSupplier narrationSupplier, Callable<Tooltip> tooltip) throws Exception {
		super(x, y, width, height, message.call(), onPress, narrationSupplier);
		callableMessage = message;
		callableTooltip = tooltip;
		updateTooltip();
	}
	private void updateTooltip() {
		try {
			this.setTooltip(this.callableTooltip != null ? this.callableTooltip.call() : null);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, "Error updating tooltip: " + error.getLocalizedMessage());
		}
	}
	@Override
	public void onPress() {
		super.onPress();
		try {
			this.setMessage(callableMessage.call());
			updateTooltip();
		} catch (Exception ignored) {
		}
	}
	public static Builder builder(Callable<Text> message, PressAction onPress) {
		return new Builder(message, onPress);
	}
	@Environment(EnvType.CLIENT)
	public static class Builder {
		private final Callable<Text> message;
		private final PressAction onPress;
		@Nullable
		private Callable<Tooltip> tooltip;
		private int x;
		private int y;
		private int width = 150;
		private int height = 20;
		private NarrationSupplier narrationSupplier;

		public Builder(Callable<Text> message, PressAction onPress) {
			this.narrationSupplier = DEFAULT_NARRATION_SUPPLIER;
			this.message = message;
			this.onPress = onPress;
		}

		public Builder position(int x, int y) {
			this.x = x;
			this.y = y;
			return this;
		}

		public Builder width(int width) {
			this.width = width;
			return this;
		}

		public Builder size(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		public Builder dimensions(int x, int y, int width, int height) {
			return this.position(x, y).size(width, height);
		}

		public Builder tooltip(@Nullable Callable<Tooltip> tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public Builder narrationSupplier(NarrationSupplier narrationSupplier) {
			this.narrationSupplier = narrationSupplier;
			return this;
		}

		public ConfigButtonWidget build() throws Exception {
			return new ConfigButtonWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, this.narrationSupplier, this.tooltip);
		}
	}
}
