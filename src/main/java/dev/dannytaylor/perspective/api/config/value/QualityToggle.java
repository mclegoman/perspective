/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.config.value;

import folk.sisby.kaleido.lib.quiltconfig.api.values.ConfigSerializableObject;

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