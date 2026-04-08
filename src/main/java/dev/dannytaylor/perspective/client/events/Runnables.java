/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Runnables extends com.mclegoman.luminance.client.events.Runnables {
    public interface UseItem {
        void run(ItemStack stack, Level level, Player user, InteractionHand hand);
    }

    public interface FinishUsingItem {
        void run(ItemStack stack, Level level, LivingEntity user);
    }

    public interface OnTickClient {
        void run(Minecraft minecraft);
    }
}
