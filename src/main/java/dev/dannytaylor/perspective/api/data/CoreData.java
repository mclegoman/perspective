/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.data;

import com.mclegoman.luminance.common.util.ModHelper;
import com.mclegoman.luminance.common.util.Version;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.Identifier;

public class CoreData {
    private static Version version;

    public static Version getVersion() {
        if (version == null) version = Version.parse(ModHelper.getModContainer("perspective").map(ModContainer::getMetadata).orElse(null), "6CTGnrNg");
        return version;
    }

    public static String getModId() {
        return getVersion().getID();
    }

    public static Identifier idOf(String path) {
        return Identifier.fromNamespaceAndPath(getModId(), path);
    }
}
