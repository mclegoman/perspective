/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.contributor;

import com.mclegoman.luminance.common.util.IdentifierHelper;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContributorData {
	private final List<String> ids;
	private final String uuid;
	private final String type;
	private final boolean shouldFlipUpsideDown;
	private final boolean shouldReplaceCape;
	private final Identifier capeTexture;
	private final boolean shouldRenderOverlay;
	private final Identifier overlayTexture;
	private final boolean isOverlayEmissive;
	private final boolean shouldBlink;
	private final Identifier blinkTexture;
	private ContributorData(List<String> ids, String uuid, String type, boolean shouldFlipUpsideDown, boolean shouldReplaceCape, Identifier capeTexture, boolean shouldRenderOverlay, Identifier overlayTexture, boolean isOverlayEmissive, boolean shouldBlink, Identifier blinkTexture) {
		this.ids = ids;
		this.uuid = uuid;
		this.type = type;
		this.shouldFlipUpsideDown = shouldFlipUpsideDown;
		this.shouldReplaceCape = shouldReplaceCape;
		this.capeTexture = capeTexture;
		this.shouldRenderOverlay = shouldRenderOverlay;
		this.overlayTexture = overlayTexture;
		this.isOverlayEmissive = isOverlayEmissive;
		this.shouldBlink = shouldBlink;
		this.blinkTexture = blinkTexture;
	}
	public static Builder builder(String uuid) {
		return new Builder(uuid);
	}
	public static class Builder {
		private final List<String> ids;
		private final String uuid;
		private String type;
		private boolean shouldFlipUpsideDown;
		private boolean shouldReplaceCape;
		private Identifier capeTexture;
		private boolean shouldRenderOverlay;
		private Identifier overlayTexture;
		private boolean isOverlayEmissive;
		private boolean shouldBlink;
		private Identifier blinkTexture;
		public Builder(String uuid) {
			this.ids = new ArrayList<>();
			this.uuid = uuid;
			this.type = Contributor.Type.CONTRIBUTOR.name();
			this.shouldFlipUpsideDown = false;
			this.shouldReplaceCape = false;
			this.capeTexture = IdentifierHelper.identifierFromString("none");
			this.shouldRenderOverlay = false;
			this.overlayTexture = IdentifierHelper.identifierFromString("none");
			this.isOverlayEmissive = false;
			this.shouldBlink = false;
			this.blinkTexture = IdentifierHelper.identifierFromString("none");
		}
		public Builder id(String... ids) {
			Collections.addAll(this.ids, ids);
			return this;
		}
		public Builder id(List<String> id) {
			this.ids.addAll(id);
			return this;
		}
		public Builder type(String type) {
			this.type = type;
			return this;
		}
		public Builder shouldFlipUpsideDown(boolean shouldFlipUpsideDown) {
			this.shouldFlipUpsideDown = shouldFlipUpsideDown;
			return this;
		}
		public Builder shouldReplaceCape(boolean shouldReplaceCape) {
			this.shouldReplaceCape = shouldReplaceCape;
			return this;
		}
		public Builder capeTexture(Identifier capeTexture) {
			this.capeTexture = capeTexture;
			return this;
		}
		public Builder shouldRenderOverlay(boolean shouldRenderOverlay) {
			this.shouldRenderOverlay = shouldRenderOverlay;
			return this;
		}
		public Builder overlayTexture(Identifier overlayTexture, boolean emissive) {
			this.overlayTexture = overlayTexture;
			this.isOverlayEmissive = emissive;
			return this;
		}
		public Builder overlayTexture(Identifier overlayTexture) {
			return overlayTexture(overlayTexture, false);
		}
		public Builder shouldBlink(boolean shouldBlink) {
			this.shouldBlink = shouldBlink;
			return this;
		}
		public Builder blinkTexture(Identifier blinkTexture) {
			this.blinkTexture = blinkTexture;
			return this;
		}
		public ContributorData build() {
			return new ContributorData(this.ids, this.uuid, this.type, this.shouldFlipUpsideDown, this.shouldReplaceCape, this.capeTexture, this.shouldRenderOverlay, this.overlayTexture, this.isOverlayEmissive, this.shouldBlink, this.blinkTexture);
		}
	}
	public List<String> getIds() {
		return this.ids;
	}
	public String getUuid() {
		return this.uuid;
	}
	public String getType() {
		return this.type;
	}
	public boolean getShouldFlipUpsideDown() {
		return this.shouldFlipUpsideDown;
	}
	public boolean getShouldReplaceCape() {
		return this.shouldReplaceCape;
	}
	public Identifier getCapeTexture() {
		return this.capeTexture;
	}
	public boolean getShouldRenderOverlay() {
		return this.shouldRenderOverlay;
	}
	public Identifier getOverlayTexture() {
		return this.overlayTexture;
	}
	public boolean getIsOverlayEmissive() {
		return this.isOverlayEmissive;
	}
	public boolean getShouldBlink() {
		return this.shouldBlink;
	}
	public Identifier getBlinkTexture() {
		return this.blinkTexture;
	}
}
