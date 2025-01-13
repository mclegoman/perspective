/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.toasts;

import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

@Environment(EnvType.CLIENT)
public class Toast implements net.minecraft.client.toast.Toast {
	private static final Identifier texture = Identifier.of(Data.version.getID(), "toast/info");
	private final Text title;
	private final List<OrderedText> lines;
	private Visibility visibility;
	private final int width;
	private final long display_time;
	private long startTime;
	private boolean justUpdated;
	private boolean hidden;

	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return 20 + Math.max(this.lines.size(), 1) * 12;
	}

	public Toast(Text title, Text description) {
		this.title = title;
		this.visibility = Visibility.HIDE;
		this.display_time = 8000L;
		List<OrderedText> list = ClientData.minecraft.textRenderer.wrapLines(description, 200);
		this.width = Math.max(200, list.stream().mapToInt((ClientData.minecraft.textRenderer::getWidth)).max().orElse(200));
		this.lines = list;
	}

	public Toast(Text title, List<OrderedText> lines, int width) {
		this.title = title;
		this.visibility = Visibility.HIDE;
		this.display_time = 8000L;
		this.width = width;
		this.lines = lines;
	}

	public net.minecraft.client.toast.Toast.Visibility getVisibility() {
		return this.visibility;
	}

	public void hide() {
		this.hidden = true;
	}

	public void update(ToastManager manager, long time) {
		if (this.justUpdated) {
			this.startTime = time;
			this.justUpdated = false;
		}

		double d = (double)this.display_time * manager.getNotificationDisplayTimeMultiplier();
		long l = time - this.startTime;
		this.visibility = !this.hidden && (double)l < d ? Visibility.SHOW : Visibility.HIDE;
	}

	public void draw(DrawContext context, TextRenderer textRenderer, long startTime) {
		int i = this.getWidth();
		int j;
		if (i == 160 && this.lines.size() <= 1) {
			context.drawGuiTexture(RenderLayer::getGuiTextured, texture, 0, 0, i, this.getHeight());
		} else {
			j = this.getHeight();
			int l = Math.min(4, j - 28);
			this.drawPart(context, i, 0, 0, 28);

			for (int m = 28; m < j - l; m += 10) {
				this.drawPart(context, i, 16, m, Math.min(16, j - m - l));
			}

			this.drawPart(context, i, 32 - l, j - l, l);
		}
		if (this.lines == null) {
			context.drawText(textRenderer, this.title, 26, 12, 0xFFAA00, false);
		} else {
			context.drawText(textRenderer, this.title, 26, 7, 0xFFAA00, false);

			for (j = 0; j < this.lines.size(); ++j) {
				context.drawText(textRenderer, this.lines.get(j), 26, 18 + j * 12, 0x00AAAA, false);
			}
		}
	}

	private void drawPart(DrawContext context, int i, int j, int k, int l) {
		int m = j == 0 ? 20 : 5;
		int n = Math.min(60, i - m);
		context.drawGuiTexture(RenderLayer::getGuiTextured, texture, 160, 32, 0, j, 0, k, m, l);
		for (int o = m; o < i - n; o += 64) {
			context.drawGuiTexture(RenderLayer::getGuiTextured, texture, 160, 32, 32, j, o, k, Math.min(64, i - o - n), l);
		}
		context.drawGuiTexture(RenderLayer::getGuiTextured, texture, 160, 32, 160 - n, j, i - n, k, n, l);
	}
}
