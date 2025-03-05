/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.data;

import net.minecraft.client.MinecraftClient;

public class ClientData {
	public static final MinecraftClient minecraft;
	public static final float configVersion;
	static {
		minecraft = MinecraftClient.getInstance();
		configVersion = 24.0F;
	}
}