/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.item.ItemGroup;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;

public class Perspective {
	public static void init() {
		try {
			Data.getVersion().sendToLog(LogType.INFO, Translation.getString("Initializing {}", Data.getVersion().getName()));
			ItemGroup.init();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to run common:init: {}", error));
		}
	}
}