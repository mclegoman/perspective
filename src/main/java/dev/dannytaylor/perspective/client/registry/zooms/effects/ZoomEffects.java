/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.registry.zooms.effects;

import dev.dannytaylor.perspective.client.data.Identifiers;
import dev.dannytaylor.perspective.client.events.Events;
import dev.dannytaylor.perspective.client.registry.zooms.effects.effect.ScaledZoomEffect;
import dev.dannytaylor.perspective.client.registry.zooms.effects.effect.UnscaledZoomEffect;
import dev.dannytaylor.perspective.client.registry.zooms.effects.effect.ZoomEffect;
import dev.dannytaylor.perspective.common.data.Log;
import net.minecraft.resources.Identifier;

public class ZoomEffects {
    public static ZoomEffect SCALED = register(Identifiers.SCALED, new ScaledZoomEffect());
    public static ZoomEffect UNSCALED = register(Identifiers.UNSCALED, new UnscaledZoomEffect());

    public static void onInitializeClient() {
        Log.info("Initializing zoom transitions...");
    }

    public static ZoomEffect register(Identifier identifier, ZoomEffect zoomEffect) {
        Events.ZoomEffects.register(identifier, zoomEffect);
        return zoomEffect;
    }
}
