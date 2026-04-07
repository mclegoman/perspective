/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.shaders.renderers;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.shaders.ShaderRenderers;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.Callable;

public class KaleidoscopeRenderer {
    private static Identifier shaderStack;

    public static void onInitializeClient() {
        Log.info("Initializing kaleidoscope renderer...");
        try {
            Events.AfterClientResourceReload.register(getId(), KaleidoscopeRenderer::clearShaderStack);
            Events.OnClientStartItemUse.register(getId(), (itemStack, level, player, hand) -> {
                if (itemStack.is(Items.SPYGLASS)) applyShaderStack(itemStack);
            });
        } catch (Exception error) {
            Log.error("Failed to initialize kaleidoscope renderer: {}", error);
        }
    }

    public static void clearShaderStack() {
        applyShaderStack(ItemStack.EMPTY);
    }

    public static void applyShaderStack(ItemStack itemStack) {
        Events.ShaderRender.register(getId());
        ShaderStacks.Entry shaderEntry = null;
        try {
            Callable<Identifier> stackId = tryGetShaderStackId(itemStack);
            if (stackId != null) {
                shaderStack = stackId.call();
                if (shaderStack != null) shaderEntry = ShaderStacks.getStack(stackId.call());
            }
        } catch (Exception error) {
            Log.error("Error getting kaleidoscope shader pack!");
        }
        Events.ShaderRender.modify(getId(), ShaderStacks.getShaders(getId(), shaderEntry, KaleidoscopeRenderer::getRenderLocation, KaleidoscopeRenderer::getEnabled, ShaderRenderers::getPhotosensitivity));
    }

    public static Identifier getId() {
        return Identifiers.KALEIDOSCOPE;
    }

    public static RenderLocations.RenderLocation getRenderLocation() {
        return RenderLocations.WORLD;
    }

    public static boolean getEnabled() {
        return shouldBeEnabled(false) && shaderStack != null;
    }

    private static Callable<Identifier> tryGetShaderStackId(ItemStack stack) {
        return shouldBeEnabled(true) ? () -> getShaderStackId(stack) : null;
    }

    private static Identifier getShaderStackId(Identifier shaderPack, boolean randomize) {
        return (!randomize && getEnabledNamed()) ? shaderPack : (getEnabledRandom() ? ShaderStacks.randomize(shaderPack) : null);
    }

    public static Identifier getShaderStackId(ItemStack stack) {
        return stack.getCustomName() != null ? getShaderStackId(ShaderRenderers.guessShaderStackId(stack.getCustomName().getString()).orElse(getShaderStackId(shaderStack, true)), false) : getShaderStackId(shaderStack, true);
    }

    public static boolean canActivate(Player player, boolean firstPerson) {
        return player.isScoping() && (ClientData.minecraft.options.getCameraType().isFirstPerson() || firstPerson);
    }

    public static boolean shouldBeEnabled(boolean firstPerson) {
        return (getEnabledNamed() || getEnabledRandom()) && (ClientData.minecraft.player != null && canActivate(ClientData.minecraft.player, firstPerson));
    }

    public static boolean getEnabledRandom() {
        return PerspectiveConfig.config.shaders.kaleidoscope.randomEnabled.value();
    }

    public static boolean getEnabledNamed() {
        return PerspectiveConfig.config.shaders.kaleidoscope.namedEnabled.value();
    }
}
