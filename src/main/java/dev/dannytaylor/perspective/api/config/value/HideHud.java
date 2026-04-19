/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.config.value;

import dev.dannytaylor.perspective.api.CoreClient;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ConfigSerializableObject;

public enum HideHud implements ConfigSerializableObject<Object> {
	nothing("nothing"),
	hudOnly("hud_only"),
	everything("everything");

	private final String name;

	HideHud(String name) {
		this.name = name;
	}

	public String asString() {
		return this.name;
	}

	public HideHud convertFrom(Object representation) {
		try {
			return valueOf(String.valueOf(representation));
		} catch (IllegalArgumentException error) {
			CoreClient.getMod().getLogger().warn("Failed to convert Hide Hud from string representation, defaulting to nothing.");
			return nothing;
		}
	}

	public Object getRepresentation() {
		return this.asString();
	}

	public HideHud copy() {
		return this;
	}
}