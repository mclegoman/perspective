/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.util;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.common.data.Data;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;

public class Position {
	private static final Map<String, Boolean> hidePos = new HashMap<>();
	public static void register(String modId) {
		if (shouldShowPos()) Data.getVersion().sendToLog(LogType.INFO, "Positional data will now be obfuscated.");
		hidePos.putIfAbsent(modId, true);
	}
	public static boolean shouldShowPos() {
		return hidePos.isEmpty();
	}
	public static String getX(Vec3d pos, boolean integer) {
		return !shouldShowPos() ? "?" : (integer ? String.valueOf((int) pos.x) : String.valueOf(pos.x));
	}
	public static String getX(Vec3d pos) {
		return getX(pos, false);
	}
	public static String getX(LivingEntity entity) {
		return getX(entity.getPos());
	}
	public static String getX(LivingEntity entity, boolean integer) {
		return getX(entity.getPos(), integer);
	}
	public static String getY(Vec3d pos, boolean integer) {
		return !shouldShowPos() ? "?" : (integer ? String.valueOf((int) pos.y) : String.valueOf(pos.y));
	}
	public static String getY(Vec3d pos) {
		return getY(pos, false);
	}
	public static String getY(LivingEntity entity) {
		return getY(entity.getPos());
	}
	public static String getY(LivingEntity entity, boolean integer) {
		return getY(entity.getPos(), integer);
	}
	public static String getZ(Vec3d pos, boolean integer) {
		return !shouldShowPos() ? "?" : (integer ? String.valueOf((int) pos.z) : String.valueOf(pos.z));
	}
	public static String getZ(Vec3d pos) {
		return getZ(pos, false);
	}
	public static String getZ(LivingEntity entity) {
		return getZ(entity.getPos());
	}
	public static String getZ(LivingEntity entity, boolean integer) {
		return getZ(entity.getPos(), integer);
	}
}
