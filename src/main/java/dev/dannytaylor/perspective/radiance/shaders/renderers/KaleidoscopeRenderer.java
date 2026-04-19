/*
    Radiance
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.radiance.shaders.renderers;

import com.mclegoman.luminance.client.shaders.RenderLocations;
import com.mclegoman.luminance.client.shaders.ShaderStacks;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.radiance.RadianceClient;
import dev.dannytaylor.perspective.radiance.config.RadianceConfig;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective_old.client.events.Events;
import dev.dannytaylor.perspective.radiance.shaders.ShaderRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.concurrent.Callable;

public class KaleidoscopeRenderer {
    private static Identifier shaderStack;

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Kaleidoscope Renderer", () -> {
            Events.AfterClientResourceReload.register(getIdentifier(), () -> KaleidoscopeRenderer.clearShaderStack(mod));
            Events.OnClientStartItemUse.register(getIdentifier(), (itemStack, level, player, hand) -> {
                if (itemStack.is(Items.SPYGLASS)) applyShaderStack(mod, itemStack);
            });
        });
    }

    public static void clearShaderStack(PerspectiveMod mod) {
        applyShaderStack(mod, ItemStack.EMPTY);
    }

    private static void applyShaderStack(PerspectiveMod mod, ItemStack itemStack) {
        Events.ShaderRender.register(getIdentifier());
        ShaderStacks.Entry shaderEntry = null;
        try {
            Callable<Identifier> stackId = tryGetShaderStackId(itemStack);
            if (stackId != null) {
                shaderStack = stackId.call();
                if (shaderStack != null) shaderEntry = ShaderStacks.getStack(stackId.call());
            }
        } catch (Exception error) {
            mod.getLogger().error("Failed to get kaleidoscope shader stack!");
        }
        Events.ShaderRender.modify(getIdentifier(), ShaderStacks.getShaders(getIdentifier(), shaderEntry, KaleidoscopeRenderer::getRenderLocation, KaleidoscopeRenderer::getEnabled, ShaderRenderers::getPhotosensitivity));
    }

    public static Identifier getIdentifier() {
        return RadianceClient.idOf("kaleidoscope");
    }

    public static RenderLocations.RenderLocation<?> getRenderLocation() {
        return RenderLocations.GAME;
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
        return RadianceConfig.instance.kaleidoscope.randomEnabled.value();
    }

    public static boolean getEnabledNamed() {
        return RadianceConfig.instance.kaleidoscope.namedEnabled.value();
    }
}
