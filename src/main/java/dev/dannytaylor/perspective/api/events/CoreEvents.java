/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.events;

import com.mclegoman.luminance.client.events.Events;
import dev.dannytaylor.perspective.api.CoreClient;
import dev.dannytaylor.perspective.api.config.CoreConfig;
import dev.dannytaylor.perspective.api.config.value.HideHud;
import dev.dannytaylor.perspective.api.data.PerspectiveMod;
import dev.dannytaylor.perspective.api.data.log.PerspectiveLog;
import net.minecraft.client.Minecraft;

// TODO: Split events from Luminance into a shared library mod, so that Luminance isn't required for every v2 mod.
// Some of these methods could also be moved over to that mod tbh.
// Since we are extending, nothing *should* break, unless it's luminance specific (in which case, addons should be using the luminance's classes instead)
public class CoreEvents extends Events {
    public static final Registry<CoreRunnables.UseItem> OnClientStartItemUse = new Registry<>();
    public static final Registry<CoreRunnables.FinishUsingItem> OnClientFinishItemUse = new Registry<>();
    public static final Registry<CoreRunnables.ShouldHideHud> ShouldHideHud = new Registry<>();

    public static void onInitialize(PerspectiveMod mod, Initializer onInitialize) {
        onInitialize(mod, "", onInitialize, true);
    }

    public static void onInitialize(PerspectiveMod mod, String name, Initializer onInitialize) {
        onInitialize(mod, name, onInitialize, false);
    }

    public static void onInitialize(PerspectiveMod mod, Initializer onInitialize, boolean logFinish) {
        onInitialize(mod, "", onInitialize, logFinish);
    }

    public static void onInitialize(PerspectiveMod mod, String name, Initializer onInitialize, boolean logFinish) {
        PerspectiveLog.info(CoreClient.getMod(), "Initializing{}...", getName(name));
        try {
            onInitialize.onInitialize();
            if (logFinish) PerspectiveLog.info(CoreClient.getMod(), "Finished initializing{}!", getName(name));
        } catch (Exception error) {
            PerspectiveLog.error(CoreClient.getMod(), "Failed to initialize{}: {}", getName(name), error);
        }
    }

    private static HideHud hideHud;

    public static HideHud getHideHud() {
        if (CoreConfig.instance.checkHideHudOnTick.value()) {
            if (hideHud == null) hideHud = CoreExecute.updateHideHud();
            return hideHud;
        } else return CoreExecute.updateHideHud();
    }

    private static String getName(String name) {
        return name.isBlank() ? name : " " + name;
    }

    public interface Initializer {
        void onInitialize();
    }

    public static void onTickClient(Minecraft minecraft) {
        if (CoreConfig.instance.checkHideHudOnTick.value()) hideHud = CoreExecute.updateHideHud();
    }
}
