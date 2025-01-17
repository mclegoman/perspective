/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.texture;

import com.mclegoman.luminance.common.util.DateHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Identifier;

import java.time.LocalDate;
import java.time.Month;

public class TextureHelper {
	public static void init() {
		try {
			ResourcePacks.init();
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to init texture helper: {}", error));
		}
	}
	public static Identifier getTexture(Identifier texture, Identifier current) {
		String path = texture.getPath();
		if (!path.equalsIgnoreCase("none")) {
			Identifier textureId = texture;
			if (path.equalsIgnoreCase("developer_cape")) {
				LocalDate date = DateHelper.getDate();
				textureId = Identifier.of(Data.getVersion().getID(), "textures/contributors/cape/dev_" + (((date.getYear() >= 2026) || date.getYear() == 2025 && (date.getMonth().getValue() >= Month.JULY.getValue() || (date.getMonth() == Month.JUNE && date.getDayOfMonth() >= 14))) ? "two" : "one") + "year.png");
			}
			return Identifier.of(textureId.getNamespace(), textureId.getPath().endsWith(".png") ? textureId.getPath() : textureId.getPath() + ".png");
		}
		return current;
	}
}