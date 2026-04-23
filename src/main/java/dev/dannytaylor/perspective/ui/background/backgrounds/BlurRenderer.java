package dev.dannytaylor.perspective.ui.background.backgrounds;

import dev.dannytaylor.perspective.api.events.CoreRunnables;
import net.minecraft.client.gui.GuiGraphics;

public record BlurRenderer(CoreRunnables.InputableRunnable<GuiGraphics> render, CoreRunnables.Callable<Boolean> renderBlur) {
}