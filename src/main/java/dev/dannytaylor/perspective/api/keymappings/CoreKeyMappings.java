/*
    Core API
    Contributor(s): dannytaylor
    Github: https://github.com/perspective-viewpoint
    Licence: LGPLv3 (or later)
*/

package dev.dannytaylor.perspective.api.keymappings;

import com.mclegoman.luminance.client.translation.Translation;

public class CoreKeyMappings {
    public static String getKey(String category, String key) {
        return Translation.getString("{}.{}", category, key);
    }
}
