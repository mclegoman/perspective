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

public enum HideUi implements ConfigSerializableObject<Object> {
	nothing("nothing"),
	hands("hands"),
	handsHud("hands_hud");

	private final String name;

	HideUi(String name) {
		this.name = name;
	}

	public String asString() {
		return this.name;
	}

	public HideUi convertFrom(Object representation) {
		try {
			return valueOf(String.valueOf(representation));
		} catch (IllegalArgumentException error) {
			PerspectiveLog.warn(CoreClient.getMod(), "Failed to convert Hide Hud from string representation, defaulting to nothing.");
			return nothing;
		}
	}

	public Object getRepresentation() {
		return this.asString();
	}

	public HideUi copy() {
		return this;
	}

	public HideUi next() {
		return values()[(this.ordinal() + 1) % values().length];
	}
}