/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.keybindings;

import com.mclegoman.perspective.client.data.ClientData;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;

public class KeybindingHelper extends com.mclegoman.luminance.client.keybindings.KeybindingHelper {
	public static KeyBinding getKeybinding(String namespace, String category, String key, int keyCode, boolean shouldRegister) {
		return shouldRegister ? getKeybinding(namespace, category, key, keyCode) : null;
	}
	public static boolean hasKeybindingConflicts(KeyBinding... keybindings) {
		for (KeyBinding currentKey1 : keybindings) {
			for (KeyBinding currentKey2 : ClientData.minecraft.options.allKeys) {
				if (!currentKey1.isUnbound() && !currentKey2.isUnbound()) {
					if (currentKey1 != currentKey2) {
						if (KeyBindingHelper.getBoundKeyOf(currentKey1) == KeyBindingHelper.getBoundKeyOf(currentKey2)) return true;
					}
				}
			}
		}
		return false;
	}
}
