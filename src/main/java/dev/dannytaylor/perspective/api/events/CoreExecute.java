/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import com.mclegoman.luminance.client.events.Execute;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CoreExecute extends Execute {
    public static void onClientStartItemUse(PerspectiveMod mod, ItemStack stack, Level level, Player user, InteractionHand hand) {
        CoreEvents.OnClientStartItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user, hand);
            } catch (Exception error) {
                PerspectiveLog.error(mod, "Failed to execute OnClientStartItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static void onClientFinishItemUse(PerspectiveMod mod, ItemStack stack, Level level, LivingEntity user) {
        CoreEvents.OnClientFinishItemUse.registry.forEach((id, runnable) -> {
            try {
                if (user.equals(ClientData.minecraft.player)) runnable.run(stack, level, user);
            } catch (Exception error) {
                PerspectiveLog.error(mod, "Failed to execute OnClientFinishItemUse event with id: {}: {}", id, error);
            }
        });
    }

    public static HideUi updateHideHud() {
        int ordinal = 0;
        for (CoreRunnables.Callable<HideUi> hideHud : CoreEvents.ShouldHideHud.registry.values()) {
            int id = hideHud.call().ordinal();
            if (id > ordinal) ordinal = id;
        }
        return HideUi.values()[ordinal];
    }
}
