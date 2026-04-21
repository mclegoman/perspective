/*
    Classic Rendering
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.classic_rendering.render;

import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.data.ClientData;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.classic_rendering.ClassicRenderingClient;
import dev.dannytaylor.perspective.classic_rendering.config.ClassicRenderingConfig;
import dev.dannytaylor.perspective.classic_rendering.events.ClassicRenderingEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class HudRendering {
    public static void onInitializeClient(PerspectiveMod mod) {
        ClassicRenderingEvents.onInitialize(mod, "Hud Rendering", () -> {
            HudElementRegistry.addLast(mod.idOf("version"), HudRendering::renderVersion);
        });
    }

    public static void renderVersion(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (ClassicRenderingConfig.instance.versionOverlay.value() && !ClientData.minecraft.getDebugOverlay().showDebugScreen()) guiGraphics.drawString(ClientData.minecraft.font, Components.guiTranslatable(ClassicRenderingClient.getMod().idOf("version_overlay"), SharedConstants.getCurrentVersion().name()), 2, 2, 0xFFFFFFFF);
    }
}
