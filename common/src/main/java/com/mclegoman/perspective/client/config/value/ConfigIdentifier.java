/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config.value;

import com.mclegoman.perspective.common.data.Data;
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
	public static ConfigIdentifier of(String identifier) {
		return of(Identifier.of(identifier));
	}
	public ConfigIdentifier convertFrom(String representation) {
		// We assume perspective as the namespace if none is provided - this is to make sure zoom_type is updated properly.
		return new ConfigIdentifier(Identifier.of((!representation.contains(":") ? Data.getVersion().getID() + ":" : "") + representation));
	}
	public String getRepresentation() {
		return this.identifier.toString();
	}
	public ConfigIdentifier copy() {
		return this;
	}
}