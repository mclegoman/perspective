/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity.states;

import java.util.Random;

public interface PerspectivePlayerRenderState {
	Random perspective$getRandom();
	void perspective$setRandom(Random random);
	boolean perspective$getBlinking();
	void perspective$setBlinking(boolean blinking);
}
