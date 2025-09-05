/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: LGPL-3.0-or-later
*/

package dev.dannytaylor.perspective.base;

import net.minecraft.util.Identifier;

/**
 * This is where Perspective's common data is stored.
 */
public class Perspective {
    public static String id;
    public static Identifier ofId(String path) {
        return Identifier.of(id, path);
    }
    static {
        id = "perspective";
    }
}
