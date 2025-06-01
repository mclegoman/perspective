/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.common.util;

import com.mclegoman.perspective.common.data.Data;
import net.minecraft.util.Identifier;

public class Identifiers {
    public static final Identifier APPEARANCE;
    public static final Identifier ARMOR_STAND;
    public static final Identifier BEE;
    public static final Identifier CLASSIC;
    public static final Identifier CONTRIBUTOR;
    public static final Identifier CONTRIBUTORS;
    public static final Identifier COW;
    public static final Identifier CPS_OVERLAY;
    public static final Identifier DEFAULT;
    public static final Identifier DYNAMIC_CROSSHAIR;
    public static final Identifier ENTITY;
    public static final Identifier FALLBACK_SHADER;
    public static final Identifier GAUSSIAN;
    public static final Identifier GIANT;
    public static final Identifier HIDE_ARMOR;
    public static final Identifier HIDE_NAME_TAGS;
    public static final Identifier HIDE_PLAYER;
    public static final Identifier KALEIDOSCOPE;
    public static final Identifier LEGACY;
    public static final Identifier LINEAR;
    public static final Identifier LOGARITHMIC;
    public static final Identifier MAIN;
    public static final Identifier MOOSHROOM;
    public static final Identifier NONE;
    public static final Identifier PERSPECTIVE_DEFAULT;
    public static final Identifier PERSPECTIVE_EXTENDED;
    public static final Identifier PIG;
    public static final Identifier PLAYER;
    public static final Identifier PRANK;
    public static final Identifier PRIDE;
    public static final Identifier RANDOM;
    public static final Identifier SKELETON;
    public static final Identifier SPLASHES;
    public static final Identifier TEXTURED_ENTITY;
    public static final Identifier UI_BACKGROUND;
    public static final Identifier WITHER_SKELETON;
    public static final Identifier ZOMBIE;
    private static Identifier of(String path) {
        return Identifier.of(Data.getVersion().getID(), path);
    }
    static {
        APPEARANCE = of("appearance");
        ARMOR_STAND = of("armor_stand");
        BEE = of("bee");
        CLASSIC = of("classic");
        CONTRIBUTOR = of("contributor");
        CONTRIBUTORS = of("contributors");
        COW = of("cow");
        CPS_OVERLAY = of("cps_overlay");
        DEFAULT = of("default");
        DYNAMIC_CROSSHAIR = of("dynamic_crosshair");
        ENTITY = of("entity");
        FALLBACK_SHADER = Identifier.ofVanilla("box_blur");
        GAUSSIAN = of("gaussian");
        GIANT = of("giant");
        HIDE_ARMOR = of("hide_armor");
        HIDE_NAME_TAGS = of("hide_name_tags");
        HIDE_PLAYER = of("hide_player");
        KALEIDOSCOPE = of("kaleidoscope");
        LEGACY = of("legacy");
        LINEAR = of("linear");
        LOGARITHMIC = of("logarithmic");
        MAIN = of("main");
        MOOSHROOM = of("mooshroom");
        NONE = of("none");
        PERSPECTIVE_DEFAULT = of("perspective_default");
        PERSPECTIVE_EXTENDED = of("perspective_extended");
        PIG = of("pig");
        PLAYER = of("player");
        PRANK = of("prank");
        PRIDE = of("pride");
        RANDOM = of("random");
        SKELETON = of("skeleton");
        SPLASHES = of("splashes");
        TEXTURED_ENTITY = of("textured_entity");
        UI_BACKGROUND = of("ui_background");
        WITHER_SKELETON = of("wither_skeleton");
        ZOMBIE = of("zombie");
    }
}
