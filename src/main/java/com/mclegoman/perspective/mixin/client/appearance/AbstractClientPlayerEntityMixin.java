/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.appearance;

import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.perspective.client.appearance.Appearance;
import com.mclegoman.perspective.client.contributor.ContributorData;
import com.mclegoman.perspective.client.contributor.ContributorDataLoader;
import com.mclegoman.perspective.client.events.AprilFoolsPrank;
import com.mclegoman.perspective.client.events.AprilFoolsPrankDataLoader;
import com.mclegoman.perspective.client.texture.TextureHelper;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(priority = 100, value = AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin {
	@Shadow
	@Nullable
	private PlayerListEntry playerListEntry;

	@Inject(method = "getSkinTextures", at = @At("TAIL"), cancellable = true)
	private void getSkinTextures(CallbackInfoReturnable<SkinTextures> cir) {
		if (this.playerListEntry != null) {
			boolean isAprilFools = PerspectiveConfig.config.allowAprilFools.value() && AprilFoolsPrank.isAprilFools() && !AprilFoolsPrankDataLoader.registry.isEmpty();
			SkinTextures currentSkinTextures = cir.getReturnValue();
			Identifier skinTexture = currentSkinTextures.texture();
			Identifier capeTexture = currentSkinTextures.capeTexture();
			SkinTextures.Model model = currentSkinTextures.model();
			UUID uuid = this.playerListEntry.getProfile().getId();
			String stringifiedUUID = String.valueOf(uuid);
			if (isAprilFools) {
				Couple<Identifier, Boolean> skin = AprilFoolsPrankDataLoader.registry.get(AprilFoolsPrank.getAprilFoolsIndex(this.playerListEntry.getProfile().getId().getLeastSignificantBits(), AprilFoolsPrankDataLoader.registry.size()));
				skinTexture = skin.getFirst();
				model = skin.getSecond() ? SkinTextures.Model.SLIM : SkinTextures.Model.WIDE;
				stringifiedUUID = AprilFoolsPrankDataLoader.contributor;
			} else {
				if (!Appearance.DataLoader.registry.isEmpty()) {
					for (Appearance.Data data : Appearance.DataLoader.registry.values()) {
						if (uuid.equals(data.uuid())) {
							skinTexture = TextureHelper.getTexture(data.texture(), skinTexture);
							model = data.model();
							break;
						}
					}
				}
			}
			for (ContributorData developer : ContributorDataLoader.registry) {
				if (developer.getUuid().equals(stringifiedUUID)) {
					if (developer.getShouldReplaceCape()) {
						capeTexture = TextureHelper.getTexture(developer.getCapeTexture(), capeTexture);
					}
				}
			}
			cir.setReturnValue(new SkinTextures(skinTexture, currentSkinTextures.textureUrl(), capeTexture, capeTexture, model, currentSkinTextures.secure()));
		}
	}
}