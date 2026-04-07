/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.value;

import dev.dannytaylor.perspective.common.data.Data;
import net.minecraft.resources.Identifier;
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
		return of(Identifier.parse(identifier));
	}
	public ConfigIdentifier convertFrom(String representation) {
		// We assume perspective as the namespace if none is provided - this is to make sure zoom_type is updated properly.
		return new ConfigIdentifier(Identifier.parse((!representation.contains(":") ? Data.getVersion().getID() + ":" : "") + representation));
	}
	public String getRepresentation() {
		return this.identifier.toString();
	}
	public ConfigIdentifier copy() {
		return this;
	}
}