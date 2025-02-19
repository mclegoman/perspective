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
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import org.jetbrains.annotations.Nullable;

public class ShadersListWidget extends AlwaysSelectedEntryListWidget<ShaderListEntry> {
	protected ShadersListWidget(int width, int height, int top, int bottom, int itemHeight, double scrollAmount) {
		super(ClientData.minecraft, width, height - top - bottom, top, itemHeight);
		SuperSecretSettings.getRegistryIds().forEach((id) -> this.addEntry(new ShaderListEntry(id)));
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
	public void setSelected(@Nullable ShaderListEntry entry) {
		super.setSelected(entry);
		if (entry != null && PerspectiveConfig.config.superSecretSettingsShader.value().getIdentifier() != entry.id) SuperSecretSettings.setShader(entry.id);
	}
	@Override
	protected int addEntry(ShaderListEntry entry) {
		return super.addEntry(entry);
	}

	@Override
	protected int getScrollbarX()  {
		return (ClientData.minecraft.getWindow().getScaledWidth()) - 6;
	}
}