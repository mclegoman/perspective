/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.states;

import net.minecraft.entity.EntityType;

import java.util.UUID;

public interface PerspectiveRenderState {
	UUID perspective$getUUID();
	void perspective$setUUID(UUID uuid);
	EntityType<?> perspective$getType();
	void perspective$setType(EntityType<?> entityType);
	String perspective$getStringName();
	void perspective$setStringName(String name);
}
