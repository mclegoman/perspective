/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.config.value.QualityToggle;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.util.Mouse;
import com.mclegoman.perspective.client.util.Position;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.common.util.Identifiers;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Overlays {
	private static final List<String> timeOverlayTypes = new ArrayList<>();
	public static void init() {
		timeOverlayTypes.add("false");
		timeOverlayTypes.add("twelve_hour");
		timeOverlayTypes.add("twenty_four_hour");
		Mouse.ProcessCPS.register(Identifiers.CPS_OVERLAY, PerspectiveConfig.config.cpsOverlay::value);
	}
	public static void updateStats() {
		if (ClientData.minecraft.getNetworkHandler() != null) ClientData.minecraft.getNetworkHandler().sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.REQUEST_STATS));
	}
	public static String getCurrentTimeOverlay() {
		return PerspectiveConfig.config.timeOverlay.value();
	}
	public static boolean isValidTimeOverlay(String timeOverlay) {
		return timeOverlayTypes.contains(timeOverlay);
	}
	public static void cycleTimeOverlay(boolean direction) {
		int currentIndex = timeOverlayTypes.indexOf(getCurrentTimeOverlay());
		PerspectiveConfig.config.timeOverlay.setValue(timeOverlayTypes.get(direction ? (currentIndex + 1) % timeOverlayTypes.size() : (currentIndex - 1 + timeOverlayTypes.size()) % timeOverlayTypes.size()), false);
	}
	public static Text getEntityPositionTextTitle() {
		return Translation.getTranslation(Data.getVersion().getID(), "position.title");
	}
	public static Text getEntityPositionTextDescription(Vec3d pos) {
		return Translation.getTranslation(Data.getVersion().getID(), "position.description", new Object[]{
				Position.getX(pos, true),
				Position.getY(pos, true),
				Position.getZ(pos, true),
		});
	}
	public static void renderOverlays(DrawContext context) {
		// TODO: Update config to have a overlays List<String> which gets parsed, this will replace the current overlay values, and the config screen will add ways to add them and custom ones!
		// TODO: Add a variable system... eg. {overlay_biome}.
		if (!ClientData.minecraft.getDebugHud().shouldShowDebugHud() && !ClientData.minecraft.options.hudHidden && !HUDHelper.shouldHideHUD()) {
			if (DebugOverlay.debugType.equals(DebugOverlay.Type.none)) {
				// Version Overlay
				if (PerspectiveConfig.config.versionOverlay.value())
					context.drawTextWithShadow(ClientData.minecraft.textRenderer, Translation.getTranslation(Data.getVersion().getID(), "version_overlay", new Object[]{SharedConstants.getGameVersion().getName()}), 2, 2, 0xffffff);
				// Other Overlays
				int y = 40;
				List<Text> overlayTexts = new ArrayList<>();
				if (PerspectiveConfig.config.positionOverlay.value()) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "position_overlay") + "](" + ("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "position.title") + "]," + (ClientData.minecraft.player != null ? "Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "position.description") + "](" + Position.getX(ClientData.minecraft.player.getPos(), true) + "," + Position.getY(ClientData.minecraft.player.getPos(), true) + "," + Position.getZ(ClientData.minecraft.player.getPos(), true) + ")" : "?")) + ")"));
				}
				if (!PerspectiveConfig.config.timeOverlay.value().equals("false")) {
					if (ClientData.minecraft.world != null) {
						long time = ClientData.minecraft.world.getTimeOfDay() % 24000L;
						int rawHour = (int)(time / 1000 + 6) % 24;
						int rawMinute = (int)(time / 16.666666) % 60;
						String hour = PerspectiveConfig.config.timeOverlay.value().equals("twelve_hour") ? String.valueOf(rawHour == 0 || rawHour == 12 ? 12 : rawHour % 12) : String.valueOf(rawHour);
						if (rawHour < 10 && rawHour != 0) hour = "0" + hour;
						overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "time_overlay") + "](" + hour + "," + (rawMinute < 10 ? "0" + rawMinute : String.valueOf(rawMinute)) + "," + Translation.getStringFromText(PerspectiveConfig.config.timeOverlay.value().equals("twelve_hour") ? (rawHour < 12 ? Translation.getTranslation(Data.getVersion().getID(), "time_overlay.am") : Translation.getTranslation(Data.getVersion().getID(), "time_overlay.pm")) : Text.literal("")) + ")"));
					}
				}
				if (PerspectiveConfig.config.dayOverlay.value()) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "day_overlay") + "](" + (ClientData.minecraft.world != null ? ClientData.minecraft.world.getTimeOfDay() / 24000L : "?") + ")"));
				}
				if (PerspectiveConfig.config.biomeOverlay.value()) {
					String biome = ClientData.minecraft.player != null && ClientData.minecraft.world != null ? ClientData.minecraft.world.getBiome(ClientData.minecraft.player.getBlockPos()).getKeyOrValue().map((biomeKey) -> biomeKey.getValue().toString(), (biome_) -> "[unregistered " + biome_ + "]") : null;
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "biome_overlay") + "](" + (biome != null ? ("Translatable[biome." + IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, biome) + "." + IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, biome)) + "])" : "?")));
				}
				if (PerspectiveConfig.config.deathsOverlay.value()) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "deaths_overlay") + "](" + (ClientData.minecraft.player != null ? ClientData.minecraft.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS)) : "?") + ")"));
				}
				if (PerspectiveConfig.config.totemsOverlay.value()) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "totems_overlay") + "](" + (ClientData.minecraft.player != null ? ClientData.minecraft.player.getStatHandler().getStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING)) : "?") + ")"));
				}
				if (PerspectiveConfig.config.cpsOverlay.value()) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "cps_overlay") + "](" + Mouse.getLeftCPS() + "," + Mouse.getMiddleCPS() + "," + Mouse.getRightCPS() + ")"));

				}
				if (PerspectiveConfig.config.lookingAtOverlay.value() != QualityToggle.off) {
					overlayTexts.add(Translation.getParsedTextFromString("Translatable[" + Translation.getTranslationKey(Data.getVersion().getID(), "looking_at_overlay") + "](" + Translation.getStringFromText(getLookingAt((LivingEntity) ClientData.minecraft.cameraEntity)) + ")"));
				}
				renderOverlays(context, overlayTexts, 0, y, false);
			} else DebugOverlay.renderDebugHUD(context);
		}
	}
	public static void renderOverlay(DrawContext context, int x, int y, Text text) {
		renderOverlay(context, x, y, text, -1873784752, 0xffffff, false);
	}
	public static void renderOverlay(DrawContext context, int x, int y, Text text, int backgroundColor, int textColor, boolean shadow) {
		context.fill(x, y, x + ClientData.minecraft.textRenderer.getWidth(text) + 4, y + 12, backgroundColor);
		context.drawText(ClientData.minecraft.textRenderer, text, x + 2, y + 2, textColor, shadow);
	}
	public static void renderOverlays(DrawContext context, List<Text> overlays, int x, int y, boolean wrap) {
		renderOverlays(context, overlays, x, y, wrap, 0);
	}
	public static void renderOverlays(DrawContext context, List<Text> overlays, int x, int y, boolean wrap, int wrapY) {
		int wrapX = 0;
		for (Text overlay : overlays) {
			if (!overlay.equals(Text.empty())) {
				wrapX = Math.max(wrapX, ClientData.minecraft.textRenderer.getWidth(overlay));
				if (wrap && (y > ClientData.minecraft.getWindow().getScaledHeight() - 2 - 9)) {
					y = wrapY;
					x += (wrapX + 4);
				}
				Overlays.renderOverlay(context, x, y, overlay);
			}
			y = HUDHelper.addY(y);
		}
	}
	public static Text getLookingAt(LivingEntity entity) {
		if (entity != null) {
			HitResult hitResult = ClientData.minecraft.crosshairTarget;
			if (hitResult != null) {
				switch (hitResult.getType()) {
					case ENTITY -> {
						return getLookingAtEntity((EntityHitResult) hitResult, PerspectiveConfig.config.lookingAtOverlay.value(), entity);
					} case BLOCK -> {
						return getBlock((BlockHitResult) hitResult, PerspectiveConfig.config.lookingAtOverlay.value(), entity);
					}
				}
			}
		}
		return getLookingAtFallbackText();
	}
	private static Text getLookingAtEntity(EntityHitResult hitResult, QualityToggle quality, LivingEntity entity) {
		switch (quality) {
			case fast -> {
				return hitResult.getEntity().getType().getName();
			}
			case fancy -> {
				Optional<Text> variant = getLookingAtEntityVariant(hitResult.getEntity());
				return Translation.getCombinedText((MutableText) variant.orElse(Text.empty()), (variant.isPresent() ? Text.literal(" ") : Text.empty()), (MutableText) hitResult.getEntity().getType().getName());
			}
		}
		return getLookingAtFallbackText();
	}
	private static Optional<Text> getLookingAtEntityVariant(Entity entity) {
		Optional<TexturedEntityEntry> texturedEntity = TexturedEntity.getEntity(entity);
		switch (entity) {
			case ParrotEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case FoxEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case PaintingEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case RabbitEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case VillagerEntity holderEntity -> {
				String profession = holderEntity.getVillagerData().getProfession().id().toLowerCase();
				Identifier variant = Identifier.of(holderEntity.getVariant().toString().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty(), !profession.equals("none") ? Text.literal(" ") : Text.empty(), !profession.equals("none") ? Text.translatable("merchant.level." + holderEntity.getVillagerData().getLevel()) : Text.empty(), !profession.equals("none") ? Text.literal(" ") : Text.empty(), !profession.equals("none") ? Text.translatableWithFallback(holderEntity.getType().getTranslationKey() + ".profession", Translation.getTitleCase(profession)) : Text.empty()));
			}
			case MooshroomEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case HorseEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case SalmonEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case LlamaEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case AxolotlEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case FrogEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case WolfEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case CatEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			case TropicalFishEntity holderEntity -> {
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? (MutableText) holderEntity.getVariant().getText() : Text.empty()));
			}
			case ShulkerEntity holderEntity -> {
				Identifier variant = holderEntity.getColor() != null ? Identifier.of(holderEntity.getColor().name().toLowerCase()) : null;
				return texturedEntity.isPresent() || variant != null ? Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), (texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant()) && variant != null ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty())) : Optional.empty();
			}
			case SheepEntity holderEntity -> {
				Identifier variant = Identifier.of(holderEntity.getColor().name().toLowerCase());
				return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getLookingAtIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
			}
			default -> {
			}
		}
		return texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName()));
	}
	private static String getLookingAtIdVariantTranslationKey(Entity entity, Identifier variant) {
		return variant.toTranslationKey(entity.getType().getTranslationKey() + ".variant");
	}
	private static Text getBlock(BlockHitResult hitResult, QualityToggle quality, LivingEntity entity) {
		switch (quality) {
			case fast -> {
				return entity.getWorld().getBlockState(hitResult.getBlockPos()).getBlock().getName();
			}
			case fancy -> {
				return entity.getWorld().getBlockState(((BlockHitResult)entity.raycast(entity.getAttributeValue(EntityAttributes.BLOCK_INTERACTION_RANGE), ClientData.minecraft.getRenderTickCounter().getTickDelta(true), true)).getBlockPos()).getBlock().getName();
			}
		}
		return getLookingAtFallbackText();
	}
	private static Text getLookingAtFallbackText() {
		return Translation.getTranslation(Data.getVersion().getID(), "looking_at_overlay.none");
	}
}