/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.gui;

import com.mclegoman.luminance.client.events.Events;
import dev.dannytaylor.perspective.api.component.Components;
import dev.dannytaylor.perspective.api.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.events.CoreEvents;
import dev.dannytaylor.perspective.api.events.CoreRunnables;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

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

    public static <V> Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, Events.Registry<V> registry) {
        return eventButton(mod, path, text, value, registry, () -> {});
    }

    public static <V> Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, Events.Registry<V> registry, Runnable after) {
        return eventButton(mod, path, text, value, registry, () -> true, after);
    }

    public static <V> Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, Events.Registry<V> registry, CoreRunnables.Callable<Boolean> isForwards) {
        return eventButton(mod, path, text, value, registry, isForwards, () -> {});
    }

    public static <V> Button.Builder eventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, Events.Registry<V> registry, CoreRunnables.Callable<Boolean> isForwards, Runnable after) {
        MutableComponent component = Component.empty();
        try {
            component = text.call(value.value().getIdentifier().withPrefix(path + "."));
        } catch (Exception error) {
            mod.getLogger().warn("Failed to get event button text: {}", error);
        }
        return Button.builder(Components.configTranslatable(mod.idOf(path), component), (button) -> {
            value.setValue(ConfigIdentifier.of(CoreEvents.cycle(registry, value.value().getIdentifier(), isForwards.call())), false);
            try {
                button.setMessage(Components.configTranslatable(mod.idOf(path), text.call(value.value().getIdentifier().withPrefix(path + "."))));
            } catch (Exception error) {
                mod.getLogger().warn("Failed to set event button text: {}", error);
            }
            after.run();
        });
    }

    public static <V> Button.Builder priorityEventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, CoreEvents.PriorityRegistry<V> registry) {
        return priorityEventButton(mod, path, text, value, registry, () -> {});
    }

    public static <V> Button.Builder priorityEventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, CoreEvents.PriorityRegistry<V> registry, Runnable after) {
        return priorityEventButton(mod, path, text, value, registry, () -> true, after);
    }

    public static <V> Button.Builder priorityEventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, CoreEvents.PriorityRegistry<V> registry, CoreRunnables.Callable<Boolean> isForwards) {
        return priorityEventButton(mod, path, text, value, registry, isForwards, () -> {});
    }

    public static <V> Button.Builder priorityEventButton(PerspectiveMod mod, String path, CoreRunnables.InputableCallable<Identifier, MutableComponent> text, TrackedValue<ConfigIdentifier> value, CoreEvents.PriorityRegistry<V> registry, CoreRunnables.Callable<Boolean> isForwards, Runnable after) {
        MutableComponent component = Component.empty();
        try {
            component = text.call(value.value().getIdentifier().withPrefix(path + "."));
        } catch (Exception error) {
            mod.getLogger().warn("Failed to get event button text: {}", error);
        }
        return Button.builder(Components.configTranslatable(mod.idOf(path), component), (button) -> {
            value.setValue(ConfigIdentifier.of(CoreEvents.cycle(registry, value.value().getIdentifier(), isForwards.call())), false);
            try {
                button.setMessage(Components.configTranslatable(mod.idOf(path), text.call(value.value().getIdentifier().withPrefix(path + "."))));
            } catch (Exception error) {
                mod.getLogger().warn("Failed to set event button text: {}", error);
            }
            after.run();
        });
    }
}
