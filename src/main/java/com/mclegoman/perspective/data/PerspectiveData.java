package com.mclegoman.perspective.data;

import com.mclegoman.releasetypeutils.helper.RTUReleaseTypeHelper;
import com.mclegoman.releasetypeutils.util.RTUReleaseTypes;
import com.mclegoman.releasetypeutils.util.RTUTranslationTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerspectiveData {
    public static final String NAME = "Perspective";
    public static final Integer MAJOR_VERSION = 1;
    public static final Integer MINOR_VERSION = 0;
    public static final Integer PATCH_VERSION = 0;
    public static final RTUReleaseTypes RELEASE_TYPE = RTUReleaseTypes.RELEASE_CANDIDATE;
    public static final Integer BUILD_VERSION = 2;
    public static final Boolean IS_DEVELOPMENT = RTUReleaseTypeHelper.isDevelopment(RELEASE_TYPE);
    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + PATCH_VERSION + "-" + RTUReleaseTypeHelper.getString(RELEASE_TYPE, RTUTranslationTypes.NORMAL) + "." + BUILD_VERSION;
    public static final String PREFIX = "[" + NAME + " " + VERSION + "] ";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
}
