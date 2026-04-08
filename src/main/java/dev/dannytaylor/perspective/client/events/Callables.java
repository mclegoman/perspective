/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.events;

import net.minecraft.client.ScrollWheelHandler;
import net.minecraft.client.input.MouseButtonInfo;

public class Callables extends com.mclegoman.luminance.client.events.Callables {
    @FunctionalInterface
    public interface OnMouseScroll {
        boolean call(long windowHandle, double horizontal, double vertical, ScrollWheelHandler scrollWheelHandler);
    }

    @FunctionalInterface
    public interface OnMouseButton {
        boolean call(long windowHandle, MouseButtonInfo mouseButtonInfo, @MouseButtonInfo.Action int action);
    }
}
