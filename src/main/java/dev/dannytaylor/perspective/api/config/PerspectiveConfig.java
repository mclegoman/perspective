package dev.dannytaylor.perspective.api.config;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class PerspectiveConfig extends ReflectiveConfig {
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void reset(boolean saveToFile) {
        for (TrackedValue value : this.values()) value.setValue(value.getDefaultValue(), false);
        if (saveToFile) this.save();
    }
}
