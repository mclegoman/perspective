/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.MessageOverlay;
import dev.dannytaylor.perspective.client.config.PerspectiveConfig;
import dev.dannytaylor.perspective.client.data.ClientData;
import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.keymappings.KeyMappingRegistry;
import dev.dannytaylor.perspective.client.registry.zooms.scales.ZoomScales;
import dev.dannytaylor.perspective.client.registry.zooms.transitions.ZoomTransitions;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.DefaultZoom;
import dev.dannytaylor.perspective.client.registry.zooms.zoom.Zoom;
import dev.dannytaylor.perspective.common.data.Data;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

import java.text.DecimalFormat;

public class ZoomRegistry {
    public static Zoom MAIN = register(Identifiers.ZOOM, new DefaultZoom(
            ZoomRegistry::shouldMainZoom,
            () -> Events.ZoomScales.get(PerspectiveConfig.config.zoom.scaleType.value().getIdentifier()),
            () -> Events.ZoomTransitions.get(PerspectiveConfig.config.zoom.transition.value().getIdentifier()),
            PerspectiveConfig.config.zoom.amount::value,
            PerspectiveConfig.config.zoom.smoothSpeedOut::value,
            PerspectiveConfig.config.zoom.smoothSpeedIn::value,
            (minecraft) -> {
                if (KeyMappingRegistry.ZoomKeyMappings.toggleZoom.consumeClick()) ZoomRegistry.isMainZoomToggled = !ZoomRegistry.isMainZoomToggled;
                if (!shouldMainZoom() && ZoomRegistry.wasConfigUpdated) {
                    PerspectiveConfig.config.save();
                    ZoomRegistry.wasConfigUpdated = false;
                }
            }
    ));

    public static float fov = 70.0F;
    public static float zoomFov = 70.0F;

    private static boolean isMainZoomToggled;

    private static boolean wasConfigUpdated;

    public static void onInitializeClient() {
        Log.info("Initializing zoom registries...");
        try {
            ZoomScales.onInitializeClient();
            ZoomTransitions.onInitializeClient();

            Events.OnMouseScroll.register(Identifiers.ZOOM, (long windowHandle, double horizontal, double vertical, ScrollWheelHandler scrollWheelHandler) -> {
                if (ZoomRegistry.shouldMainZoom()) {
                    boolean discreteMouseScroll = ClientData.minecraft.options.discreteMouseScroll().get();
                    double mouseWheelSensitivity = ClientData.minecraft.options.mouseWheelSensitivity().get();
                    double calculatedScroll = (discreteMouseScroll ? Math.signum(vertical) : vertical) * mouseWheelSensitivity;
                    Vector2i vector2i = scrollWheelHandler.onMouseScroll(calculatedScroll, calculatedScroll);
                    if (vector2i.y != 0) {
                        ZoomRegistry.adjustMainAmount(vector2i.y);
                        return true;
                    }
                }
                return false;
            });

            Events.OnMouseButton.register(Identifiers.ZOOM, (windowHandle, mouseButtonInfo, action) -> {
                if (ZoomRegistry.shouldMainZoom()) {
                    if (mouseButtonInfo.button() == 2) {
                        ZoomRegistry.setMainZoomAmount(PerspectiveConfig.config.zoom.amount.getDefaultValue());
                        return true;
                    }
                }
                return false;
            });
        } catch (Exception error) {
            Log.error("Failed to initialize zoom registries: {}", error);
        }
    }

    public static void onTickClient(Minecraft minecraft) {
        Events.Zooms.registry.forEach((identifier, zoom) -> zoom.onTickClient(minecraft));
    }

    public static Zoom register(Identifier identifier, Zoom zoom) {
        Events.Zooms.register(identifier, zoom);
        return zoom;
    }

    public static double getMultiplierFromFOV() {
        return zoomFov/fov;
    }

    public static boolean shouldMainZoom() {
        return PerspectiveConfig.config.zoom.enabled.value() && (KeyMappingRegistry.ZoomKeyMappings.holdZoom.isDown() || ZoomRegistry.isMainZoomToggled);
    }

    private static void setMainZoomAmount(float amount) {
        float clampedAmount = clampMainAmount(amount);
        if (PerspectiveConfig.config.zoom.showPercentage.value()) MessageOverlay.setOverlay(Translation.getCombinedText(Translation.getTranslation(Data.getModId(), "zoom.adjust"), Component.literal(" " + new DecimalFormat("#.##").format(clampedAmount) + "%")).withStyle(ChatFormatting.GOLD));
        PerspectiveConfig.config.zoom.amount.setValue(clampedAmount, false);
        ZoomRegistry.wasConfigUpdated = true;
    }

    private static float clampMainAmount(float amount) {
        return Math.clamp(amount, 0.0F, 100.0F);
    }

    private static void adjustMainAmount(float scrollAmount) {
        if (shouldMainZoom()) setMainZoomAmount(clampMainAmount(PerspectiveConfig.config.zoom.amount.value() + (scrollAmount * PerspectiveConfig.config.zoom.incrementSize.value())));
    }
}
