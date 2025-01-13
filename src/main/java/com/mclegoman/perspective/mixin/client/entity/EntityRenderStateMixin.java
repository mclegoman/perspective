/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.mixin.client.entity;

import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements PerspectiveRenderState {
	@Override
	public UUID perspective$getUUID() {
		return this.perspective$uuid;
	}
	@Override
	public void perspective$setUUID(UUID uuid) {
		this.perspective$uuid = uuid;
	}
	@Override
	public EntityType<?> perspective$getType() {
		return this.perspective$entityType;
	}
	@Override
	public void perspective$setType(EntityType<?> entityType) {
		this.perspective$entityType = entityType;
	}
	@Override
	public String perspective$getStringName() {
		return this.perspective$stringName;
	}
	@Override
	public void perspective$setStringName(String stringName) {
		this.perspective$stringName = stringName;
	}
	@Unique
	private UUID perspective$uuid;
	@Unique
	private EntityType<?> perspective$entityType;
	@Unique
	private String perspective$stringName;
}
