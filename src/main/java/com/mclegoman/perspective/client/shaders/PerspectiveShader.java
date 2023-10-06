/*
    Perspective
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Perspective
    License: GNU LGPLv3
*/

package com.mclegoman.perspective.client.shaders;

import com.mclegoman.perspective.client.config.PerspectiveConfigHelper;
import com.mclegoman.perspective.client.data.PerspectiveClientData;
import com.mclegoman.perspective.client.overlays.PerspectiveHUDOverlays;
import com.mclegoman.perspective.client.toasts.PerspectiveToast;
import com.mclegoman.perspective.client.translation.PerspectiveTranslation;
import com.mclegoman.perspective.client.translation.PerspectiveTranslationType;
import com.mclegoman.perspective.client.util.PerspectiveKeybindings;
import com.mclegoman.perspective.common.data.PerspectiveData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Environment(EnvType.CLIENT)
public class PerspectiveShader {
    public static Framebuffer DEPTH_FRAME_BUFFER;
    public static boolean DEPTH_FIX;
    @Nullable
    public static PostEffectProcessor postProcessor;
    private static final Formatting[] COLORS = new Formatting[]{Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA, Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA, Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW};
    private static Formatting LAST_COLOR;
    private static final List<Identifier> SOUND_EVENTS = new ArrayList<>();
    public static void init() {
        try {
            ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new PerspectiveShaderDataLoader());
            for (Identifier id : Registries.SOUND_EVENT.getIds()) {
                if (!id.toString().contains("music")) {
                    SOUND_EVENTS.add(id);
                }
            }
        } catch (Exception error) {
            PerspectiveData.PERSPECTIVE_VERSION.getLogger().warn("{} Caught an error whilst initializing Super Secret Settings", PerspectiveData.PERSPECTIVE_VERSION.getLoggerPrefix(), error);
        }
    }
    public static void tick(MinecraftClient client) {
        if (PerspectiveKeybindings.CYCLE_SHADERS.wasPressed()) cycle(client, !client.options.sneakKey.isPressed(), false, true);
        if (PerspectiveKeybindings.TOGGLE_SHADERS.wasPressed()) toggle(client, false, false);
        if (PerspectiveKeybindings.RANDOM_SHADER.wasPressed()) random(false, true);
    }
    public static void toggle(MinecraftClient client, boolean SILENT, boolean SHOW_SHADER_NAME) {
        if (!(Boolean) PerspectiveConfigHelper.getTutorialConfig("super_secret_settings")) {
            PerspectiveClientData.CLIENT.getToastManager().add(new PerspectiveToast(PerspectiveTranslation.getTranslation("toasts.title", new Object[]{PerspectiveTranslation.getTranslation("name"), PerspectiveTranslation.getTranslation("toasts.tutorial.super_secret_settings.title")}), PerspectiveTranslation.getTranslation("toasts.tutorial.super_secret_settings.description", new Object[]{KeyBindingHelper.getBoundKeyOf(PerspectiveKeybindings.CYCLE_SHADERS).getLocalizedText(), KeyBindingHelper.getBoundKeyOf(PerspectiveKeybindings.TOGGLE_SHADERS).getLocalizedText(), KeyBindingHelper.getBoundKeyOf(PerspectiveKeybindings.OPEN_CONFIG).getLocalizedText()}), 280, PerspectiveToast.Type.TUTORIAL));
            PerspectiveConfigHelper.setTutorialConfig("super_secret_settings", true);
        }


        PerspectiveConfigHelper.setConfig("super_secret_settings_enabled", !(boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled"));
        if (!SILENT) {
            if (SHOW_SHADER_NAME) setOverlay(Text.literal(PerspectiveShaderDataLoader.getShaderName((int)PerspectiveConfigHelper.getConfig("super_secret_settings"))));
            else setOverlay(PerspectiveTranslation.getVariableTranslation((boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled"), PerspectiveTranslationType.ENDISABLE));
        }
        if ((boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled")) set(client, true, true, true);
        else {
            if (postProcessor != null) {
                postProcessor.close();
                postProcessor = null;
            }
        }
        PerspectiveConfigHelper.saveConfig(true);
    }
    public static void cycle(MinecraftClient client, boolean FORWARDS, boolean SILENT, boolean SAVE_CONFIG) {
        try {
            if (FORWARDS) {
                if ((int)PerspectiveConfigHelper.getConfig("super_secret_settings") < PerspectiveShaderDataLoader.getShaderAmount()) PerspectiveConfigHelper.setConfig("super_secret_settings", (int)PerspectiveConfigHelper.getConfig("super_secret_settings") + 1);
                else PerspectiveConfigHelper.setConfig("super_secret_settings", 0);
            } else {
                if ((int)PerspectiveConfigHelper.getConfig("super_secret_settings") > 0) PerspectiveConfigHelper.setConfig("super_secret_settings", (int)PerspectiveConfigHelper.getConfig("super_secret_settings") - 1);
                else PerspectiveConfigHelper.setConfig("super_secret_settings", PerspectiveShaderDataLoader.getShaderAmount());
            }
            set(client, FORWARDS, SILENT, SAVE_CONFIG);
        } catch (Exception error) {
            PerspectiveData.PERSPECTIVE_VERSION.getLogger().warn("{} An error occurred whilst trying to cycle Super Secret Settings.", PerspectiveData.PERSPECTIVE_VERSION.getLoggerPrefix(), error);
        }
    }
    public static void random(boolean SILENT, boolean SAVE_CONFIG) {
        try {
            int SHADER = (int)PerspectiveConfigHelper.getConfig("super_secret_settings");
            while (SHADER == (int)PerspectiveConfigHelper.getConfig("super_secret_settings")) SHADER = Math.max(1, new Random().nextInt(PerspectiveShaderDataLoader.getShaderAmount()));
            PerspectiveConfigHelper.setConfig("super_secret_settings", SHADER);
            PerspectiveShader.set(PerspectiveClientData.CLIENT, true, SILENT, SAVE_CONFIG);
        } catch (Exception error) {
            PerspectiveData.PERSPECTIVE_VERSION.getLogger().warn("{} An error occurred whilst trying to randomize Super Secret Settings.", PerspectiveData.PERSPECTIVE_VERSION.getLoggerPrefix(), error);
        }
    }
    public static void set(MinecraftClient client, Boolean forwards, boolean SILENT, boolean SAVE_CONFIG) {
        try {
            DEPTH_FIX = true;
            if (postProcessor != null) postProcessor.close();
            postProcessor = new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), (Identifier)Objects.requireNonNull(PerspectiveShaderDataLoader.get((int) PerspectiveConfigHelper.getConfig("super_secret_settings"), PerspectiveShaderRegistryValue.ID)));
            postProcessor.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            if (!SILENT) setOverlay(Text.literal(PerspectiveShaderDataLoader.getShaderName((int)PerspectiveConfigHelper.getConfig("super_secret_settings"))));
            try {
                if (!SILENT && client.world != null && client.player != null && (boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_sound")) client.world.playSound(client.player, client.player.getBlockPos(), SoundEvent.of(SOUND_EVENTS.get(new Random().nextInt(SOUND_EVENTS.size() - 1))), SoundCategory.MASTER);
            } catch (Exception error) {
                PerspectiveData.PERSPECTIVE_VERSION.getLogger().warn("{} An error occurred whilst trying to play random Super Secret Settings sound.", PerspectiveData.PERSPECTIVE_VERSION.getLoggerPrefix(), error);
            }
            DEPTH_FIX = false;
            if (!(boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled")) toggle(client, true, false);
            if (SAVE_CONFIG) PerspectiveConfigHelper.saveConfig(true);
        } catch (Exception error) {
            PerspectiveData.PERSPECTIVE_VERSION.getLogger().warn("{} An error occurred whilst trying to set Super Secret Settings.", PerspectiveData.PERSPECTIVE_VERSION.getLoggerPrefix(), error);
            try {
                cycle(client, forwards, false, SAVE_CONFIG);
            } catch (Exception ignored) {
                PerspectiveConfigHelper.setConfig("super_secret_settings", 0);
                try {
                    if (postProcessor != null) postProcessor.close();
                    postProcessor = new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), (Identifier)Objects.requireNonNull(PerspectiveShaderDataLoader.get((int) PerspectiveConfigHelper.getConfig("super_secret_settings"), PerspectiveShaderRegistryValue.ID)));
                    postProcessor.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
                    if ((boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled")) toggle(client, true, false);
                    PerspectiveConfigHelper.saveConfig(true);
                } catch (Exception ignored2) {}
            }
            if (SAVE_CONFIG) PerspectiveConfigHelper.saveConfig(true);
        }
    }
    private static void setOverlay(Text message) {
        if ((boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_overlay_message")) PerspectiveHUDOverlays.setOverlay(Text.translatable("gui.perspective.message.shader", message).formatted(getRandomColor()));
    }
    public static Formatting getRandomColor() {
        Random random = new Random();
        Formatting COLOR = LAST_COLOR;
        while (COLOR == LAST_COLOR) COLOR = COLORS[(random.nextInt(COLORS.length))];
        LAST_COLOR = COLOR;
        return COLOR;
    }
    public static boolean shouldRenderShader() {
        return postProcessor != null && (boolean)PerspectiveConfigHelper.getConfig("super_secret_settings_enabled");
    }
    public static boolean shouldDisableScreenMode() {
        return (Boolean)Objects.requireNonNull(PerspectiveShaderDataLoader.get((int)PerspectiveConfigHelper.getConfig("super_secret_settings"), PerspectiveShaderRegistryValue.DISABLE_SCREEN_MODE));
    }
    public static void render(float tickDelta) {
        if (postProcessor != null) postProcessor.render(tickDelta);
    }
    public static void cycleShaderModes() {
        if (PerspectiveConfigHelper.getConfig("super_secret_settings_mode").equals("game")) PerspectiveConfigHelper.setConfig("super_secret_settings_mode", "screen");
        else if (PerspectiveConfigHelper.getConfig("super_secret_settings_mode").equals("screen")) PerspectiveConfigHelper.setConfig("super_secret_settings_mode", "game");
        else PerspectiveConfigHelper.setConfig("super_secret_settings_mode", "game");
    }
}