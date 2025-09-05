/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.base.client.util;

import net.minecraft.client.MinecraftClient;

/**
 * This is where Perspective's common client-side utils are stored.
 */
public class ClientUtils {
    public static final MinecraftClient minecraft;
    static {
        minecraft = MinecraftClient.getInstance();
    }
}
