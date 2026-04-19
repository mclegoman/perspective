/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective_old.client.config.sections;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class HideSettings extends ReflectiveConfig.Section {
    public final TrackedValue<Boolean> showDeathCoordinates;
    public final TrackedValue<String> crosshairType;
    public final BlockOutline blockOutline;
    public final TrackedValue<Boolean> armor;
    public final TrackedValue<Boolean> nametags;
    public final TrackedValue<Boolean> players;
    public final TrackedValue<Float> starBrightnessMultiplier;
    public final TrackedValue<Boolean> showMessage;

    public HideSettings(boolean showDeathCoordinatesValue, String crosshairTypeValue, BlockOutline blockOutlineValue, boolean armorValue, boolean nametagsValue, boolean playersValue, float starBrightnessMultiplierValue, boolean showMessageValue) {
        this.showDeathCoordinates = this.value(showDeathCoordinatesValue);
        this.crosshairType = this.value(crosshairTypeValue);
        this.blockOutline = blockOutlineValue;
        this.armor = this.value(armorValue);
        this.nametags = this.value(nametagsValue);
        this.players = this.value(playersValue);
        this.starBrightnessMultiplier = this.value(starBrightnessMultiplierValue);
        this.showMessage = this.value(showMessageValue);
    }

    public static class BlockOutline extends ReflectiveConfig.Section {
        public final TrackedValue<Integer> transparency;
        public final TrackedValue<Boolean> rainbow;
        public final TrackedValue<Boolean> hide;

        public BlockOutline(int transparencyValue, boolean rainbowValue, boolean hideValue) {
            this.transparency = this.value(transparencyValue);
            this.rainbow = this.value(rainbowValue);
            this.hide = this.value(hideValue);
        }
    }
}
