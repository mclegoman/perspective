/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config.value;

import net.minecraft.util.Identifier;
import org.quiltmc.config.api.values.ConfigSerializableObject;

@SuppressWarnings("unused")
public record ConfigIdentifier(Identifier identifier) implements ConfigSerializableObject<String> {
	public Identifier getIdentifier() {
		return this.identifier;
	}
	public static ConfigIdentifier of(Identifier identifier) {
		return new ConfigIdentifier(identifier);
	}
	public ConfigIdentifier convertFrom(String representation) {
		return new ConfigIdentifier(Identifier.of(representation));
	}
	public String getRepresentation() {
		return this.identifier.toString();
	}
	public ConfigIdentifier copy() {
		return this;
	}
}