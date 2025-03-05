/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.states;

public interface PerspectiveLivingRenderState {
	float perspective$getPrevBodyYaw();
	void perspective$setPrevBodyYaw(float prevBodyYaw);
	boolean perspective$getChestEmpty();
	void perspective$setChestEmpty(boolean chestEmpty);
}
