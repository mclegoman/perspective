/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.texture;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Identifier;

public class TextureHelper {
	public static void init() {
		try {
			ResourcePacks.init();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to init texture helper: {}", error));
		}
	}
	public static Identifier getTexture(Identifier texture, Identifier current) {
		return texture.getPath().equals("none") ? current : Identifier.of(texture.getNamespace(), texture.getPath().endsWith(".png") ? texture.getPath() : texture.getPath() + ".png");
	}
}