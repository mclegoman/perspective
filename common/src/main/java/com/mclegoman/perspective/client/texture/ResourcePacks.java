/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.texture;

import com.mclegoman.luminance.client.texture.ActivationType;
import com.mclegoman.luminance.client.texture.ResourcePackHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.common.util.ModContainer;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.common.util.Identifiers;

import java.util.Optional;

public class ResourcePacks {
	/**
	 * To add a resource pack to this project, please follow these guidelines:
	 * 1. When registering your resource pack, ensure you include the resource pack's name, and the contributor(s) in the following format:
	 * - Resource Pack Name
	 * - Contributor(s): _________
	 * - Licence: _________
	 * You only need to include the licence in your comment if it is not GNU LGPLv3.
	 */
	public static void init() {
		try {
			Optional<ModContainer> modContainer = Data.getVersion().getModContainer();
			if (modContainer.isPresent()) {
				Data.getVersion().sendToLog(LogType.INFO, Translation.getString("Initializing resource packs!"));
				/*
		            Perspective: Default
		            Contributor(s): dannytaylor
		            Attribution(s): Phantazap ('Jester' Giant Textured Entity)
		        */
				ResourcePackHelper.register(Identifiers.PERSPECTIVE_DEFAULT, modContainer.get(), Translation.getTranslation(Data.getVersion().getID(), "resource_pack.perspective_default"), ActivationType.enabledDefault);
				/*
		            Perspective: Extended
		            Contributor(s): dannytaylor
		        */
				ResourcePackHelper.register(Identifiers.PERSPECTIVE_EXTENDED, modContainer.get(), Translation.getTranslation(Data.getVersion().getID(), "resource_pack.perspective_extended"), ActivationType.disabledDefault);
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to init resource packs: {}", error));
		}
	}
}