/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.component;

import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.config.value.HideUi;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class Components {
    public static MutableComponent configTranslatable(Identifier id, Object... args) {
        return guiTranslatable(id.withPrefix("config."), args);
    }

    public static MutableComponent hideUiTranslatable(HideUi hideUi) {
        return guiTranslatable(CoreClient.idOf("hide_ui").withSuffix("." + hideUi.asString()));
    }

    public static MutableComponent onffTranslatable(boolean value) {
        return booleanTranslatable(CoreClient.idOf("onff"), value);
    }

    public static MutableComponent booleanTranslatable(Identifier id, boolean value) {
        return guiTranslatable(id.withPrefix("variable.").withSuffix("." + (value ? "true" : "false")));
    }

    public static MutableComponent guiTranslatable(Identifier id, Object... args) {
        return translatable("gui.", id, args);
    }

    public static MutableComponent translatable(Identifier id, Object... args) {
        return translatable("", id, args);
    }

    public static MutableComponent translatable(String prefix, Identifier id, Object... args) {
        return Component.translatable(prefix + id.getNamespace() + "." + id.getPath(), args);
    }

    public static MutableComponent getCombinedText(MutableComponent... texts) {
        MutableComponent outputText = texts[0];
        for (int i = 1; i < texts.length; i++) outputText.append(texts[i]);
        return outputText;
    }
}
