/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.component;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class Components {
    public static MutableComponent translatable(Identifier id, Object... args) {
        return Component.translatable("gui." + id.getNamespace() + "." + id.getPath(), args);
    }

    public static MutableComponent getCombinedText(MutableComponent... texts) {
        MutableComponent outputText = texts[0];
        for (int i = 1; i < texts.length; i++) outputText.append(texts[i]);
        return outputText;
    }
}
