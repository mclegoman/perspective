/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config.value;

import org.quiltmc.config.api.values.ConfigSerializableObject;

@SuppressWarnings("unused")
public enum QualityToggle implements ConfigSerializableObject<String> {
	off("off"),
	fast("fast"),
	fancy("fancy");
	private final String name;
	QualityToggle(String name) {
		this.name = name;
	}
	public String asString() {
		return this.name;
	}
	public QualityToggle convertFrom(String representation) {
		return valueOf(representation);
	}
	public String getRepresentation() {
		return this.name();
	}
	public QualityToggle copy() {
		return this;
	}
}