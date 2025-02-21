/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.screen.config.shaders;

import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.shaders.SuperSecretSettings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.client.gui.tooltip.TooltipState;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.OrderedText;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ShaderPacksListWidget<E extends ShaderListEntry<E>> extends AlwaysSelectedEntryListWidget<E> {
	protected ShaderPacksListWidget(int width, int height, int top, int bottom, int itemHeight, double scrollAmount) {
		super(ClientData.minecraft, width, height - top - bottom, top, itemHeight);
		SuperSecretSettings.getRegistryIds().forEach((id) -> this.addEntry((E) new ShaderListEntry<E>(id)));
		if (this.getEntryCount() > 0) {
			int index = SuperSecretSettings.getRegistryIds().indexOf(PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier());
			if (index >= 0) {
				super.setSelected(getEntry(index));
				this.setScrollY(scrollAmount >= 0 ? scrollAmount : index * 20);
			}
		}
		this.setFocused(true);
	}
	@Override
	public void setSelected(@Nullable E entry) {
		super.setSelected(entry);
		if (entry != null && PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier() != entry.id) SuperSecretSettings.setShader(entry.id);
	}
	@Override
	protected int addEntry(E entry) {
		return super.addEntry(entry);
	}
	@Override
	protected int getScrollbarX()  {
		return (ClientData.minecraft.getWindow().getScaledWidth()) - 6;
	}
	@Override
	public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		super.renderWidget(context, mouseX, mouseY, delta);
		if (ClientData.minecraft.currentScreen != null && getEntryTooltip().getTooltip() != null) {
			List<OrderedText> lines = getEntryTooltip().getTooltip().getLines(ClientData.minecraft);
			int width = 0;
			for (OrderedText text : lines) {
				int textWidth = ClientData.minecraft.textRenderer.getWidth(text);
				if (textWidth > width) width = textWidth;
			}
			int x = (mouseX + width > (ClientData.minecraft.currentScreen.width - 32)) ? mouseX - width : mouseX;
			TooltipBackgroundRenderer.render(context, x + 1, mouseY + 10, width + 4, (lines.size() * 10) + 4, 300, null);
			context.getMatrices().translate(0.0F, 0.0F, 400.0F);
			for (int i = 0; i < lines.size(); i++) context.drawTextWithShadow(ClientData.minecraft.textRenderer, lines.get(i), x + 2, mouseY + 12 + (i * 10), 0xFFFFFF);
		}
	}
	@Override
	protected void renderEntry(DrawContext context, int mouseX, int mouseY, float delta, int index, int x, int y, int entryWidth, int entryHeight) {
		E entry = this.getEntry(index);
		entry.drawBorder(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(this.getHoveredEntry(), entry), delta);
		if (this.isSelectedEntry(index)) this.drawSelectionHighlight(context, y, entryWidth, entryHeight, -1, -16777216);
		else if (Objects.equals(getEntry(index), getHoveredEntry())) this.drawSelectionHighlight(context, y, entryWidth, entryHeight, -8355712, -16777216);
		entry.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, Objects.equals(this.getHoveredEntry(), entry), delta);
	}
	private TooltipState getEntryTooltip() {
		TooltipState entryTooltip = new TooltipState();
		if (getHoveredEntry() != null) entryTooltip.setTooltip(SuperSecretSettings.getTooltip(getHoveredEntry().id));
		return entryTooltip;
	}
}