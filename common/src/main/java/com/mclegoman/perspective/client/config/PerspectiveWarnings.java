/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.config;

import com.mclegoman.luminance.config.LuminanceConfigHelper;
import com.mclegoman.perspective.common.data.Data;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;

public class PerspectiveWarnings extends ReflectiveConfig {
	public static final PerspectiveWarnings config = LuminanceConfigHelper.register(LuminanceConfigHelper.SerializerType.PROPERTIES, Data.getVersion().getID(), "warnings", PerspectiveWarnings.class);
	@SerializedName("halloween")
	public final TrackedValue<Boolean> halloween = this.value(false);
	@SerializedName("april_fools")
	public final TrackedValue<Boolean> aprilFools = this.value(false);
	public static void init() {
	}
}