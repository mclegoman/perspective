/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.texture;

import com.mclegoman.luminance.client.texture.ResourcePacks;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ResourcePackHelper {
	public static void register(Identifier id, ModContainer container, Text text, ResourcePackActivationType activationType) {
		ResourcePacks.register(id, container, text, activationType);
	}
}