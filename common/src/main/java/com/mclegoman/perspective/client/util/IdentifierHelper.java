package com.mclegoman.perspective.client.util;

import net.minecraft.util.Identifier;

public class IdentifierHelper extends fabric.com.mclegoman.luminance.common.util.IdentifierHelper {
	public static Identifier identifierFromString(String identifier, String defaultNamespace) {
		return of(identifier, defaultNamespace);
	}
	private static Identifier of(String id, String defaultNamespace) {
		int index = id.indexOf(':');
		if (index >= 0) {
			String string = id.substring(index + 1);
			if (index != 0) return Identifier.of(id.substring(0, index), string);
			else return Identifier.of(defaultNamespace, string);
		} else return Identifier.of(defaultNamespace, id);
	}
}
