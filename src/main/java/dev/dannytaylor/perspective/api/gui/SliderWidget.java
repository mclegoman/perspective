package dev.dannytaylor.perspective.api.gui;

import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.concurrent.Callable;

public class SliderWidget extends AbstractSliderButton {
    private final CoreRunnables.ApplyValue applyValue;
    private final Callable<Component> text;

    public SliderWidget(int x, int y, int width, int height, double value, CoreRunnables.ApplyValue applyValue, Callable<Component> text) {
        super(x, y, width, height, Component.empty(), value);
        this.applyValue = applyValue;
        this.text = text;
        this.updateMessage();
    }

    protected void updateMessage() {
        try {
            this.setMessage(this.text.call());
        } catch (Exception error) {
            PerspectiveLog.error(CoreClient.getMod(), "Failed to update slider message: {}", error);
        }
    }

    protected void applyValue() {
        this.applyValue.apply(this.value);
    }
}