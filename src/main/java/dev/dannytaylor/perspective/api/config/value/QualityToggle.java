/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.config.value;

import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ConfigSerializableObject;

public enum QualityToggle implements ConfigSerializableObject<Object> {
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

	public QualityToggle convertFrom(Object representation) {
		try {
			return valueOf(String.valueOf(representation));
		} catch (IllegalArgumentException error) {
			PerspectiveLog.warn(CoreClient.getMod(), "Failed to convert Quality Toggle from string representation, defaulting to off.");
			return off;
		}
	}

	public Object getRepresentation() {
		return this.asString();
	}

	public QualityToggle copy() {
		return this;
	}

	public QualityToggle next() {
		return values()[(this.ordinal() + 1) % values().length];
	}
}