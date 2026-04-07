/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.client.config.sections;

import dev.dannytaylor.perspective.client.config.value.ConfigIdentifier;
import dev.dannytaylor.perspective.client.config.value.QualityToggle;
import net.minecraft.resources.Identifier;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.values.TrackedValue;

// TODO: allow custom overlays using variables, with the ability to reorder them.
// Since it would require most of the current system to be rewritten, I'll leave this for v2.
public class OverlaySettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> version;
    public final TrackedValue<Boolean> position;
    public final TrackedValue<String> time;
    public final TrackedValue<Boolean> day;
    public final Date date;
    public final TrackedValue<Boolean> biome;
    public final TrackedValue<QualityToggle> lookingAt;
    public final TrackedValue<Boolean> cps;
    public final TrackedValue<Boolean> deaths;
    public final TrackedValue<Boolean> totems;
    public final TrackedValue<Boolean> armor;

    public OverlaySettings(boolean versionValue, boolean positionValue, String timeValue, boolean dayValue, Date dateValue, boolean biomeValue, QualityToggle lookingAtValue, boolean cpsValue, boolean deathsValue, boolean totemsValue, boolean armorValue) {
        this.version = this.value(versionValue);
        this.position = this.value(positionValue);
        this.time = this.value(timeValue);
        this.day = this.value(dayValue);
        this.date = dateValue;
        this.biome = this.value(biomeValue);
        this.lookingAt = this.value(lookingAtValue);
        this.cps = this.value(cpsValue);
        this.deaths = this.value(deathsValue);
        this.totems = this.value(totemsValue);
        this.armor = this.value(armorValue);
    }

    public static class Date extends ReflectiveConfig.Section {
        public final TrackedValue<ConfigIdentifier> type;
        public final TrackedValue<Boolean> enabled;

        public Date(Identifier typeValue, boolean enabledValue) {
            this.type = this.value(ConfigIdentifier.of(typeValue));
            this.enabled = this.value(enabledValue);
        }
    }
}
