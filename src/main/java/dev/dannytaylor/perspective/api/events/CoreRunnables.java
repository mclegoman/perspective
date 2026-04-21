/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import com.mclegoman.luminance.client.events.Runnables;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
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

    public interface ShouldHideHud {
        HideUi call();
    }

    public interface OnTickClient {
        void run(Minecraft minecraft);
    }

    public interface Drawable {
        void draw(GuiGraphics guiGraphics);
    }

    public interface DeltaDrawable {
        void draw(GuiGraphics guiGraphics, DeltaTracker deltaTracker);
    }

    public interface CancellableDrawable {
        boolean draw(GuiGraphics guiGraphics);
    }

    public interface ApplyValue {
        void apply(double value);
    }

    public interface Textable {
        MutableComponent call(Identifier identifier);
    }
}
