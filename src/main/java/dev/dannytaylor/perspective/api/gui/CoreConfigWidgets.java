package dev.dannytaylor.perspective.api.gui;

import com.mclegoman.luminance.client.events.Events;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import dev.dannytaylor.perspective.lens.events.LensEvents;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class CoreConfigWidgets {
    public static Button.Builder toggleButton(PerspectiveMod mod, String path, TrackedValue<Boolean> value) {
        return Button.builder(Components.configTranslatable(mod.idOf(path), Components.onffTranslatable(value.value())), (button) -> {
            value.setValue(!value.value(), false);
            button.setMessage(Components.configTranslatable(mod.idOf(path), Components.onffTranslatable(value.value())));
        });
    }

    public static Button.Builder hideUiButton(PerspectiveMod mod, String path, TrackedValue<HideUi> value) {
        return Button.builder(Components.configTranslatable(mod.idOf(path), Components.hideUiTranslatable(value.value())), (button) -> {
            value.setValue(value.value().next(), false);
            button.setMessage(Components.configTranslatable(mod.idOf(path), Components.hideUiTranslatable(value.value())));
        });
    }

    public static Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.Textable text, TrackedValue<ConfigIdentifier> value, Events.Registry<?> registry) {
        return eventButton(mod, path, text, value, registry, () -> {});
    }

    public static Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.Textable text, TrackedValue<ConfigIdentifier> value, Events.Registry<?> registry, Runnable after) {
        MutableComponent component = Component.empty();
        try {
            component = text.call(value.value().getIdentifier().withPrefix(path + "."));
        } catch (Exception error) {
            mod.getLogger().warn("Failed to get event button text: {}", error);
        }
        return Button.builder(Components.configTranslatable(mod.idOf(path), component), (button) -> {
            value.setValue(ConfigIdentifier.of(LensEvents.next(registry, value.value().getIdentifier())), false);
            try {
                button.setMessage(Components.configTranslatable(mod.idOf(path), text.call(value.value().getIdentifier().withPrefix(path + "."))));
            } catch (Exception error) {
                mod.getLogger().warn("Failed to set event button text: {}", error);
            }
            after.run();
        });
    }
}
