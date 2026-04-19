/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.config.sections;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class EventSettings extends ReflectiveConfig.Section {
    public final AprilFools aprilFools;
    public final Halloween halloween;

    public EventSettings(AprilFools aprilFoolsValue, Halloween halloweenValue) {
        this.aprilFools = aprilFoolsValue;
        this.halloween = halloweenValue;
    }

    public static class AprilFools extends Event {
        public AprilFools(boolean allowValue, boolean forceValue) {
            super(allowValue, forceValue);
        }
    }

    public static class Halloween extends Event {
        public Halloween(boolean allowValue, boolean forceValue) {
            super(allowValue, forceValue);
        }
    }

    public static class Event extends ReflectiveConfig.Section {
        public final TrackedValue<Boolean> allow;
        public final TrackedValue<Boolean> force;

        public Event(boolean allowValue, boolean forceValue) {
            this.allow = this.value(allowValue);
            this.force = this.value(forceValue);
        }
    }
}
