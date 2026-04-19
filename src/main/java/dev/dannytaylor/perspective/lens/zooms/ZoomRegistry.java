/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms;

import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.MessageOverlay;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.keymappings.LensKeyMappings;
import dev.dannytaylor.perspective_old.client.events.Events;
import dev.dannytaylor.perspective.lens.zooms.effects.ZoomEffects;
import dev.dannytaylor.perspective.lens.zooms.scales.ZoomScales;
import dev.dannytaylor.perspective.lens.zooms.transitions.ZoomTransitions;
import dev.dannytaylor.perspective.lens.zooms.zoom.DefaultZoom;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import dev.dannytaylor.perspective.api.data.CoreData;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

import java.text.DecimalFormat;

public class ZoomRegistry {
    public static Zoom MAIN = register(getIdentifier(), new DefaultZoom(
            ZoomRegistry::shouldMainZoom,
            () -> Events.ZoomScales.get(LensConfig.instance.scaleType.value().getIdentifier()),
            () -> Events.ZoomTransitions.get(LensConfig.instance.transition.value().getIdentifier()),
            () -> Events.ZoomEffects.get(LensConfig.instance.effects.value().getIdentifier()),
            LensConfig.instance.amount::value,
            LensConfig.instance.smoothSpeedOut::value,
            LensConfig.instance.smoothSpeedIn::value,
            (minecraft) -> {
                if (LensKeyMappings.toggleZoom.consumeClick()) ZoomRegistry.isMainZoomToggled = !ZoomRegistry.isMainZoomToggled;
                if (!shouldMainZoom() && ZoomRegistry.wasConfigUpdated) {
                    LensConfig.instance.save();
                    ZoomRegistry.wasConfigUpdated = false;
                }
            }
    ));

    public static Identifier getIdentifier() {
        return LensClient.idOf("zoom");
    }

    public static float fov = 70.0F;
    public static float zoomFov = 70.0F;

    private static boolean isMainZoomToggled;

    private static boolean wasConfigUpdated;

    public static void onInitializeClient(PerspectiveMod mod) {
        CoreEvents.onInitialize(mod, "Zoom Registry", () -> {
            ZoomScales.onInitializeClient(mod);
            ZoomTransitions.onInitializeClient(mod);
            ZoomEffects.onInitializeClient(mod);

            Events.OnMouseScroll.register(getIdentifier(), (long windowHandle, double horizontal, double vertical, Vector2i vector2i) -> {
                if (ZoomRegistry.shouldMainZoom()) {
                    if (vector2i.y != 0) {
                        ZoomRegistry.adjustMainAmount(vector2i.y);
                        return true;
                    }
                }
                return false;
            });

            Events.OnMouseButton.register(getIdentifier(), (windowHandle, mouseButtonInfo, action) -> {
                if (ZoomRegistry.shouldMainZoom()) {
                    if (mouseButtonInfo.button() == 2) {
                        ZoomRegistry.setMainZoomAmount(LensConfig.instance.amount.getDefaultValue());
                        return true;
                    }
                }
                return false;
            });
        });
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
        if (LensConfig.instance.enabled.value()) {
            boolean shouldZoom = ZoomRegistry.isMainZoomToggled;
            if (LensKeyMappings.holdZoom.isDown()) shouldZoom = !shouldZoom;
            return shouldZoom;
        }
        return false;
    }

    private static void setMainZoomAmount(float amount) {
        float clampedAmount = clampMainAmount(amount);
        if (LensConfig.instance.showPercentage.value()) MessageOverlay.setOverlay(Translation.getCombinedText(Translation.getTranslation(CoreData.getModId(), "zoom.adjust"), Component.literal(" " + new DecimalFormat("#.##").format(clampedAmount) + "%")).withStyle(ChatFormatting.GOLD));
        LensConfig.instance.amount.setValue(clampedAmount, false);
        ZoomRegistry.wasConfigUpdated = true;
    }

    private static float clampMainAmount(float amount) {
        return Math.clamp(amount, 0.0F, 100.0F);
    }

    private static void adjustMainAmount(float scrollAmount) {
        if (shouldMainZoom()) setMainZoomAmount(clampMainAmount(LensConfig.instance.amount.value() + (scrollAmount * LensConfig.instance.incrementSize.value())));
    }

    public static float getCombinedBobViewMultiplier() {
        float multiplier = 1.0F;
        for (Zoom zoom : Events.Zooms.registry.values()) {
            if (zoom.getEffect() != null) multiplier *= zoom.getEffect().getBobViewMultiplier(zoom);
        }
        return multiplier;
    }

    public static float getCombinedMouseMultiplier() {
        float multiplier = 1.0F;
        for (Zoom zoom : Events.Zooms.registry.values()) {
            if (zoom.getEffect() != null) multiplier *= zoom.getEffect().getMouseMultiplier(zoom);
        }
        return multiplier;
    }

    public static boolean isZooming() {
        for (Zoom zoom : Events.Zooms.registry.values()) {
            if (zoom.isZooming()) return true;
        }
        return false;
    }
}
