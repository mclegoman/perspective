/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.hud;

import com.mclegoman.perspective.client.config.PerspectiveConfig;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class LookingAtOverlay {
    public static Text getLookingAt(World world) {
        if (ClientData.minecraft.cameraEntity != null) {
            HitResult hitResult = ClientData.minecraft.crosshairTarget;
            if (hitResult != null) {
                switch (hitResult.getType()) {
                    case ENTITY -> {
                        // TODO: Should entities have more data on FANCY? (Variant? Profession?)
                        return ((EntityHitResult)hitResult).getEntity().getType().getName();
                    } case BLOCK -> {
                        switch (PerspectiveConfig.config.lookingAtOverlay.value()) {
                            case fast -> {
                                return world.getBlockState(((BlockHitResult)hitResult).getBlockPos()).getBlock().getName();
                            }
                            case fancy -> {
                                return world.getBlockState(((BlockHitResult)ClientData.minecraft.cameraEntity.raycast(((LivingEntity)ClientData.minecraft.cameraEntity).getAttributeValue(EntityAttributes.BLOCK_INTERACTION_RANGE), ClientData.minecraft.getRenderTickCounter().getTickDelta(true), true)).getBlockPos()).getBlock().getName();
                            }
                        }
                    }
                }
            }
        }
        return Translation.getTranslation(Data.getVersion().getID(), "looking_at_overlay.none");
    }
}