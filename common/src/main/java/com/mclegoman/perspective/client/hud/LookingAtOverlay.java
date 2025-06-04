/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.config.value.QualityToggle;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.VariantHolder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Optional;

public class LookingAtOverlay {
    public static Text getLookingAt(LivingEntity entity) {
        if (entity != null) {
            HitResult hitResult = ClientData.minecraft.crosshairTarget;
            if (hitResult != null) {
                switch (hitResult.getType()) {
                    case ENTITY -> {
                        return getEntity((EntityHitResult) hitResult, PerspectiveConfig.config.lookingAtOverlay.value(), entity);
                    } case BLOCK -> {
                        return getBlock((BlockHitResult) hitResult, PerspectiveConfig.config.lookingAtOverlay.value(), entity);
                    }
                }
            }
        }
        return getFallbackText();
    }
    private static Text getEntity(EntityHitResult hitResult, QualityToggle quality, LivingEntity entity) {
        switch (quality) {
            case fast -> {
                return hitResult.getEntity().getType().getName();
            }
            case fancy -> {
                Optional<Text> variant = getEntityVariant(hitResult.getEntity());
                return Translation.getCombinedText((MutableText) variant.orElse(Text.empty()), (variant.isPresent() ? Text.literal(" ") : Text.empty()), (MutableText) hitResult.getEntity().getType().getName());
            }
        }
        return getFallbackText();
    }
    private static Optional<Text> getEntityVariant(Entity entity) {
        Optional<TexturedEntityEntry> texturedEntity = TexturedEntity.getEntity(entity);
        switch (entity) {
            case ParrotEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case FoxEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case PaintingEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case RabbitEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case VillagerEntity holderEntity -> {
                String profession = holderEntity.getVillagerData().getProfession().id().toLowerCase();
                Identifier variant = Identifier.of(holderEntity.getVariant().toString().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty(), !profession.equals("none") ? Text.literal(" ") : Text.empty(), !profession.equals("none") ? Text.translatable("merchant.level." + holderEntity.getVillagerData().getLevel()) : Text.empty(), !profession.equals("none") ? Text.literal(" ") : Text.empty(), !profession.equals("none") ? Text.translatableWithFallback(holderEntity.getType().getTranslationKey() + ".profession", Translation.getTitleCase(profession)) : Text.empty()));
            }
            case MooshroomEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case HorseEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case SalmonEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case LlamaEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case AxolotlEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case FrogEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case WolfEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case CatEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getVariant().getIdAsString().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            case TropicalFishEntity holderEntity -> {
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? (MutableText) holderEntity.getVariant().getText() : Text.empty()));
            }
            case ShulkerEntity holderEntity -> {
                Identifier variant = holderEntity.getColor() != null ? Identifier.of(holderEntity.getColor().name().toLowerCase()) : null;
                return texturedEntity.isPresent() || variant != null ? Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), (texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant()) && variant != null ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty())) : Optional.empty();
            }
            case SheepEntity holderEntity -> {
                Identifier variant = Identifier.of(holderEntity.getColor().name().toLowerCase());
                return Optional.of(Translation.getCombinedText(texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName())).orElseGet(Text::empty), texturedEntity.isPresent() && !texturedEntity.get().getOverrideLookingAtVariant() ? Text.literal(" ") : Text.empty(), texturedEntity.isEmpty() || !texturedEntity.get().getOverrideLookingAtVariant() ? Text.translatableWithFallback(getIdVariantTranslationKey(holderEntity, variant), Translation.getTitleCase(variant.getPath())) : Text.empty()));
            }
            default -> {
            }
        }
        return texturedEntity.map(texturedEntityEntry -> Text.literal(texturedEntityEntry.getName()));
    }
    private static String getIdVariantTranslationKey(Entity entity, Identifier variant) {
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
        return getFallbackText();
    }
    private static Text getFallbackText() {
        return Translation.getTranslation(Data.getVersion().getID(), "looking_at_overlay.none");
    }
}