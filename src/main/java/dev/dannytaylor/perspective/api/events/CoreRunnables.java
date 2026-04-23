/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import com.mclegoman.luminance.client.events.Runnables;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoreRunnables extends Runnables {
    public interface UseItem {
        void run(ItemStack stack, Level level, Player user, InteractionHand hand);
    }

    public interface FinishUsingItem {
        void run(ItemStack stack, Level level, LivingEntity user);
    }

    public interface Callable<O> {
        O call();
    }

    public interface InputableCallable<I, O> {
        O call(I input);
    }

    public interface InputableRunnable<I> {
        void run(I input);
    }
}
