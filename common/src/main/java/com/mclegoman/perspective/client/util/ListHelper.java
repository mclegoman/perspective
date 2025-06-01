/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.util;

import java.util.List;
import java.util.UUID;

public class ListHelper {
    public static Object getRandom(UUID uuid, List<?> registry) {
        return registry.get(Math.floorMod(uuid.getLeastSignificantBits(), registry.size()));
    }
}
