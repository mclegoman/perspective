/*
    Lens
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.lens.zooms;

import com.mclegoman.luminance.client.util.MessageOverlay;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.util.NumberHelper;
import dev.dannytaylor.perspective.lens.LensClient;
import dev.dannytaylor.perspective.lens.config.LensConfig;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import dev.dannytaylor.perspective.lens.events.LensRunnables;
import dev.dannytaylor.perspective.lens.keymappings.LensKeyMappings;
import dev.dannytaylor.perspective.lens.zooms.effects.ZoomEffects;
import dev.dannytaylor.perspective.lens.zooms.overlays.ZoomOverlays;
import dev.dannytaylor.perspective.lens.zooms.scales.ZoomScales;
import dev.dannytaylor.perspective.lens.zooms.transitions.ZoomTransitions;
import dev.dannytaylor.perspective.lens.zooms.zoom.DefaultZoom;
import dev.dannytaylor.perspective.lens.zooms.zoom.Zoom;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.joml.Vector2i;

public class ZoomRegistry {
    public static Zoom MAIN = register(getIdentifier(), DefaultZoom.builder()
            .isZooming(ZoomRegistry::isMainZooming)
            .scale(() -> LensEvents.ZoomScales.get(LensConfig.instance.scaleType.value().getIdentifier()))
            .transition(() -> LensEvents.ZoomTransitions.get(LensConfig.instance.transition.value().getIdentifier()))
            .effect(() -> LensEvents.ZoomEffects.get(LensConfig.instance.effects.value().getIdentifier()))
            .shouldEffect((zoom) -> zoom.isEnabled() && zoom.isZooming() || LensConfig.instance.effectsWhenNotZooming.value() && (zoom.getMultiplier() < LensConfig.instance.effectsThreshold.value()))
            .amount(LensConfig.instance.amount::value)
            .onTickClient((minecraft) -> {
                if (LensConfig.instance.checkOnTick.value()) ZoomRegistry.isMainZoomHeld = LensKeyMappings.holdZoom.isDown();
                if (LensKeyMappings.toggleZoom.consumeClick()) ZoomRegistry.isMainZoomToggled = !ZoomRegistry.isMainZoomToggled;
                if (!isMainZooming() && ZoomRegistry.wasConfigUpdated) {
                    LensConfig.instance.save();
                    ZoomRegistry.wasConfigUpdated = false;
                }
            })
            .guiOverlay((graphics, deltaTracker, zoom) -> {
                LensRunnables.ZoomOverlay drawable = LensEvents.ZoomOverlays.get(LensConfig.instance.overlay.value().getIdentifier());
                if (drawable != null) drawable.draw(graphics, deltaTracker, zoom);
            })
            .isEnabled((zoom) -> (LensConfig.instance.enabled.value() && (!LensConfig.instance.requireSpyglass.value() || ClientData.minecraft.player != null && ClientData.minecraft.player.getInventory().contains((itemStack) -> itemStack.is(Items.SPYGLASS)))))
            .build(LensClient.getMod()));

    public static Identifier getIdentifier() {
        return getIdentifier(null);
    }

    public static Identifier getIdentifier(String suffix) {
        Identifier identifier = LensClient.idOf("zoom");
        if (suffix != null && !suffix.isBlank()) identifier.withSuffix("_" + suffix);
        return identifier;
    }

    public static float fov = 70.0F;
    public static float zoomFov = 70.0F;

    private static boolean isMainZoomToggled;
    private static boolean isMainZoomHeld;

    private static boolean wasConfigUpdated;

    public static void onInitializeClient(PerspectiveMod mod) {
        LensEvents.onInitialize(mod, "Zoom Registry", () -> {
            ZoomScales.onInitializeClient(mod);
            ZoomTransitions.onInitializeClient(mod);
            ZoomEffects.onInitializeClient(mod);
            ZoomOverlays.onInitializeClient(mod);

            LensEvents.OnMouseScroll.register(getIdentifier(), (long windowHandle, double horizontal, double vertical, Vector2i vector2i) -> {
                if (ZoomRegistry.isMainZooming()) {
                    if (vector2i.y != 0) {
                        ZoomRegistry.adjustMainAmount(vector2i.y);
                        return true;
                    }
                }
                return false;
            });

            LensEvents.OnMouseButton.register(getIdentifier(), (windowHandle, mouseButtonInfo, action) -> {
                if (ZoomRegistry.isMainZooming()) {
                    if (mouseButtonInfo.button() == 2) {
                        ZoomRegistry.setMainZoomAmount(LensConfig.instance.amount.getDefaultValue());
                        return true;
                    }
                }
                return false;
            });

            LensEvents.ShouldHideHud.register(getIdentifier(), () -> ZoomRegistry.isMainZooming() ? LensConfig.instance.hideUi.value() : HideUi.nothing);
        });
    }

    public static void onTickClient(Minecraft minecraft) {
        LensEvents.Zooms.registry.forEach((identifier, zoom) -> {
            if (zoom.isEnabled()) zoom.onTickClient(minecraft);
        });
    }

    public static Zoom register(Identifier identifier, Zoom zoom) {
        LensEvents.Zooms.register(identifier, zoom);
        return zoom;
    }

    public static boolean isMainZooming() {
        if (MAIN.isEnabled()) {
            boolean shouldZoom = ZoomRegistry.isMainZoomToggled;
            if ((!LensConfig.instance.checkOnTick.value() && LensKeyMappings.holdZoom.isDown()) || ZoomRegistry.isMainZoomHeld) shouldZoom = !shouldZoom;
            return shouldZoom;
        }
        return false;
    }

    private static void setMainZoomAmount(float amount) {
        float clampedAmount = clampMainAmount(amount);
        if (LensConfig.instance.showPercentage.value()) MessageOverlay.setOverlay(getMainZoomAmountText(clampedAmount).withStyle(ChatFormatting.GOLD));
        LensConfig.instance.amount.setValue(clampedAmount, false);
        ZoomRegistry.wasConfigUpdated = true;
    }

    public static MutableComponent getMainZoomAmountText(float amount) {
        return Components.guiTranslatable(LensClient.getMod().idOf("zoom.adjust"), NumberHelper.floatToString(amount) + "%");
    }

    private static float clampMainAmount(float amount) {
        return Math.clamp(amount, 0.0F, 100.0F);
    }

    private static void adjustMainAmount(float scrollAmount) {
        if (isMainZooming()) setMainZoomAmount(clampMainAmount(LensConfig.instance.amount.value() + (scrollAmount * LensConfig.instance.incrementSize.value())));
    }

    public static float getCombinedBobViewMultiplier() {
        float multiplier = 1.0F;
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom.isEnabled() && zoom.getEffect() != null && zoom.shouldEffect()) multiplier *= zoom.getEffect().getBobViewMultiplier(zoom);
        }
        return multiplier;
    }

    public static boolean shouldMouseXUseCos() {
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom.shouldEffect() && zoom.getEffect().shouldMouseXUseCos()) return true;
        }
        return false;
    }

    public static float getCombinedMouseMultiplier() {
        float multiplier = 1.0F;
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom != null && zoom.isEnabled() && zoom.getEffect() != null && zoom.shouldEffect()) multiplier *= zoom.getEffect().getMouseMultiplier(zoom);
        }
        return multiplier;
    }

    public static float getCombinedMultiplier() {
        float multiplier = 1.0F;
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom != null && zoom.isEnabled()) multiplier *= zoom.getMultiplier();
        }
        return multiplier;
    }

    public static boolean isZooming() {
        for (Zoom zoom : LensEvents.Zooms.registry.values()) {
            if (zoom != null && zoom.isEnabled() && zoom.isZooming()) return true;
        }
        return false;
    }
}
