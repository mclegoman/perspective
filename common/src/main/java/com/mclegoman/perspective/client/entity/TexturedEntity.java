/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.data.ClientData;
import com.mclegoman.perspective.client.entity.states.PerspectiveRenderState;
import com.mclegoman.perspective.client.events.PerspectiveEvents;
import com.mclegoman.perspective.client.shaders.ShaderPackEntry;
import com.mclegoman.perspective.client.shaders.ShaderPacks;
import com.mclegoman.perspective.client.shaders.TexturedEntityShader;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.client.texture.TextureHelper;
import com.mclegoman.perspective.client.util.ListHelper;
import com.mclegoman.perspective.common.data.Data;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.perspective.client.config.PerspectiveConfig;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TexturedEntity {
	private static final List<Identifier> forbiddenEntities = new ArrayList<>();
	public static List<Identifier> getForbiddenEntities() {
		return forbiddenEntities;
	}
	public static void addForbiddenEntity(Identifier... entityIds) {
		Collections.addAll(forbiddenEntities, entityIds);
	}
	public static void addForbiddenEntity(EntityType<?>... entityTypes) {
		for (EntityType<?> entityType : entityTypes) addForbiddenEntity(Registries.ENTITY_TYPE.getId(entityType));
	}
	public static boolean isForbiddenEntity(Identifier entityId) {
		return forbiddenEntities.contains(entityId);
	}
	public static Identifier getEntityTypeId(EntityType<?> entityType) {
		return Registries.ENTITY_TYPE.getId(entityType);
	}
	public static EntityType<?> getEntityType(Identifier entityTypeId) {
		return Registries.ENTITY_TYPE.get(entityTypeId);
	}
	private static void addDefaultForbiddenEntities() {
		try {
			// This prevents users from trying to use textured entity features on players. Use appearance instead.
			addForbiddenEntity(EntityType.PLAYER);
			// The dragon is simply just not compatible, if it ever becomes compatible, this can be removed.
			addForbiddenEntity(EntityType.ENDER_DRAGON);
			// Fireworks now use the itemStack itself to render.
			addForbiddenEntity(EntityType.FIREWORK_ROCKET);
			// TNT now use the blockState to render.
			addForbiddenEntity(EntityType.TNT);
			// Boat/Raft Rendering has changed, ideally we should be able to replace the texture of boats.
			addForbiddenEntity(EntityType.OAK_BOAT, EntityType.OAK_CHEST_BOAT, EntityType.SPRUCE_BOAT, EntityType.SPRUCE_CHEST_BOAT, EntityType.BIRCH_BOAT, EntityType.BIRCH_CHEST_BOAT, EntityType.JUNGLE_BOAT, EntityType.JUNGLE_CHEST_BOAT, EntityType.ACACIA_BOAT, EntityType.ACACIA_CHEST_BOAT, EntityType.DARK_OAK_BOAT, EntityType.DARK_OAK_CHEST_BOAT, EntityType.MANGROVE_BOAT, EntityType.MANGROVE_CHEST_BOAT, EntityType.CHERRY_BOAT, EntityType.CHERRY_CHEST_BOAT, EntityType.PALE_OAK_BOAT, EntityType.PALE_OAK_CHEST_BOAT, EntityType.BAMBOO_RAFT, EntityType.BAMBOO_CHEST_RAFT);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to add default forbidden textured entities: {}", error));
		}
	}
	public static void init() {
		try {
			addDefaultForbiddenEntities();
			PerspectiveEvents.ClientResourceReloaders.register(Identifier.of(Data.getVersion().getID(), "textured_entity"), new TexturedEntityDataReloader());
			PerspectiveEvents.SpectatorHandlers.register(Identifier.of(Data.getVersion().getID(), "textured_entity"), new TexturedEntityShader());
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to initialize textured entity: {}", error));
		}
	}
	private static Identifier getOverrideTexture(String prefix, String suffix, JsonArray overrides, Identifier fallback, Identifier vanilla) {
		if (!overrides.isEmpty()) {
			for (JsonElement element : overrides) {
				String entityPrefix = JsonHelper.getString((JsonObject) element, "prefix", "");
				String entitySuffix = JsonHelper.getString((JsonObject) element, "suffix", "");
				String entityTexture = JsonHelper.getString((JsonObject) element, "texture", "");
				String entityTextureNamespace = entityTexture.contains(":") ? entityTexture.substring(0, entityTexture.lastIndexOf(":")) : "minecraft";
				String entityTexturePath = entityTexture.contains(":") ? entityTexture.substring(entityTexture.lastIndexOf(":") + 1) : entityTexture;
				if (prefix.equals(entityPrefix) && suffix.equals(entitySuffix)) return entityTexture.equalsIgnoreCase("") ? vanilla : Identifier.of(entityTextureNamespace, entityTexturePath.endsWith(".png") ? entityTexturePath : entityTexturePath + ".png");
			}
		}
		return fallback;
	}
	private static Identifier getOverrideTexture(JsonArray overrides, Identifier fallback, Identifier vanilla) {
		return getOverrideTexture("", "", overrides, fallback, vanilla);
	}
	public static Identifier getTexture(EntityRenderState renderState, Identifier fallback) {
		return getTexture(renderState, "", "", "", fallback);
	}
	public static Identifier getTexture(EntityRenderState renderState, String overrideNamespace, Identifier fallback) {
		return getTexture(renderState, overrideNamespace, "", "", fallback);
	}
	public static Identifier getTexture(EntityRenderState renderState, String prefix, String suffix, Identifier fallback) {
		return getTexture(renderState, "", prefix, suffix, fallback);
	}
	public static Identifier getTexture(EntityRenderState renderState, String overrideNamespace, String prefix, String suffix, Identifier fallback) {
		try {
			if (TexturedEntityDataReloader.isReady) {
				Identifier entityType = getEntityTypeId(((PerspectiveRenderState)renderState).perspective$getType());
				String namespace = fallback.getNamespace();
				if (!overrideNamespace.isEmpty()) namespace = overrideNamespace;
				Optional<TexturedEntityEntry> entityData = getEntity(renderState);
				if (entityData.isPresent()) {
					boolean shouldReplaceTexture = true;
						if (renderState instanceof LivingEntityRenderState) {
							JsonObject entitySpecific = entityData.get().getEntitySpecific();
							if (entitySpecific != null) {
								if (entitySpecific.has("ages")) {
									JsonObject ages = JsonHelper.getObject(entitySpecific, "ages", new JsonObject());
									if (((LivingEntityRenderState) renderState).baby) {
										if (ages.has("baby")) {
											JsonObject typeRegistry = JsonHelper.getObject(ages, "baby", new JsonObject());
											shouldReplaceTexture = JsonHelper.getBoolean(typeRegistry, "enabled", true);
										}
									} else {
										if (ages.has("adult")) {
											JsonObject typeRegistry = JsonHelper.getObject(ages, "adult", new JsonObject());
											shouldReplaceTexture = JsonHelper.getBoolean(typeRegistry, "enabled", true);
										}
									}
								}
							}
						}
					if (shouldReplaceTexture) return TextureHelper.getTexture(getOverrideTexture(prefix, suffix, entityData.get().getOverrides(), Identifier.of(namespace, "textures/textured_entity/" + entityType.getNamespace() + "/" + entityType.getPath() + "/" + (prefix + entityData.get().getName().toLowerCase() + suffix) + ".png"), fallback), fallback);
				}
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to set textured entity texture: {}", error));
		}
		return fallback;
	}
	private static List<TexturedEntityEntry> getRegistry(String namespace, String entity_type) {
		List<TexturedEntityEntry> entityRegistry = new ArrayList<>();
		try {
			for (TexturedEntityEntry registry : TexturedEntityDataReloader.getRegistry()) {
				if (registry.getNamespace().equals(namespace) && registry.getType().equals(entity_type)) entityRegistry.add(registry);
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to get textured entity string registry: {}", error));
		}
		return entityRegistry;
	}
	private static List<TexturedEntityEntry> getRandomRegistry(List<TexturedEntityEntry> registry) {
		List<TexturedEntityEntry> entityRegistry = new ArrayList<>();
		try {
			for (TexturedEntityEntry data : registry) if (data.getCanBeRandom() && (data.getEnabled() || data.getName().equalsIgnoreCase("default"))) entityRegistry.add(data);
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to get random textured entity string registry: {}", error));
		}
		return entityRegistry;
	}
	private static Optional<String> getEntityName(EntityRenderState renderState) {
		if (renderState.displayName != null) return Optional.of(renderState.displayName.getString());
		else return Optional.of("default");
	}
	private static Optional<TexturedEntityEntry> getEntityData(List<TexturedEntityEntry> registry, String entityName) {
		try {
			for (TexturedEntityEntry entityData : registry) {
				if (entityName.equals(entityData.getName())) {
					if (entityData.getEnabled()) return Optional.of(entityData);
				}
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to get textured entity entity data for entity '{}': {}", entityName, error));
		}
		return Optional.empty();
	}
	public static Optional<Identifier> getEntitySpecificModel(EntityRenderState renderState) {
		if (renderState != null) {
			Optional<TexturedEntityEntry> entityData = getEntity(renderState);
			if (entityData.isPresent()) {
				JsonObject entitySpecific = entityData.get().getEntitySpecific();
				if (entitySpecific != null) {
					if (entitySpecific.has("model")) {
						return Optional.of(Identifier.of(JsonHelper.getString(entitySpecific, "model").toLowerCase()));
					}
				}
			}
		}
		return Optional.of(Identifier.of(Data.getVersion().getID(), "default"));
	}
	public static Optional<TexturedEntityEntry> getEntity(Entity entity) {
		return getEntity(entity.getCustomName() != null ? entity.getCustomName().getLiteralString() : null, entity.getUuid(), entity.getType());
	}
	public static Optional<TexturedEntityEntry> getEntity(EntityRenderState renderState) {
		return getEntity(((PerspectiveRenderState) renderState).perspective$getStringName(), ((PerspectiveRenderState)renderState).perspective$getUUID(), ((PerspectiveRenderState)renderState).perspective$getType());
	}
	private static Optional<TexturedEntityEntry> getEntity(@Nullable String entityName, UUID uuid, EntityType<?> entityType) {
		try {
			if (entityName == null) entityName = "default";
			Identifier entityId = getEntityTypeId(entityType);
			if (!isForbiddenEntity(entityId)) {
				List<TexturedEntityEntry> registry = getRegistry(IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, IdentifierHelper.stringFromIdentifier(entityId)), IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, IdentifierHelper.stringFromIdentifier(entityId)));
				List<TexturedEntityEntry> randomRegistry = getRandomRegistry(registry);
				if (TexturedEntityDataReloader.isReady && !registry.isEmpty()) {
					if (PerspectiveConfig.config.texturedNamedEntity.value()) {
						Optional<TexturedEntityEntry> entityData = getEntityData(registry, entityName);
						if (entityData.isPresent()) return entityData;
					}
					if (PerspectiveConfig.config.texturedRandomEntity.value()) {
						TexturedEntityEntry data = (TexturedEntityEntry) ListHelper.getRandom(uuid, randomRegistry);
						if (data.getName().equalsIgnoreCase("default")) {
							Optional<TexturedEntityEntry> entityData = getEntityData(randomRegistry, "default");
							if (entityData.isPresent()) return entityData;
						} else return Optional.of(data);
					}
					if (PerspectiveConfig.config.texturedNamedEntity.value()) {
						// If the entity texture isn't replaced by the previous checks, it doesn't have a valid textured entity,
						// so we return the default textured entity if it exists.
						if (!entityName.equalsIgnoreCase("default")) {
							Optional<TexturedEntityEntry> entityData = getEntityData(registry, "default");
							if (entityData.isPresent()) return entityData;
						}
					}
				}
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.ERROR, Translation.getString("Failed to get textured entity entity data: {}", error));
		}
		return Optional.empty();
	}
	public static boolean setTexturedEntity(boolean oldValue, boolean newValue) {
		return oldValue && newValue;
	}
	public static void applyShader(Entity entity) {
		setShader(getShaders(entity, getShaderPack(entity)));
	}
	public static void clearShader() {
		setShader(new ArrayList<>());
	}
	public static void setShader(List<Shader.Data> shaders) {
		PerspectiveEvents.ShaderRender.register(getTexturedEntityId(), new ArrayList<>());
		PerspectiveEvents.ShaderRender.modify(getTexturedEntityId(), shaders);
	}
	public static Identifier getTexturedEntityId(String suffix) {
		return Identifier.of(Data.getVersion().getID(), "textured_entity" + suffix);
	}
	public static Identifier getTexturedEntityId() {
		return getTexturedEntityId("");
	}
	public static TexturedEntityEntry.SpectatorShader getShaderPack(Entity entity) {
		Optional<TexturedEntityEntry> texturedEntityEntry = getEntity(entity);
		return texturedEntityEntry.map(TexturedEntityEntry::getShaderPack).orElse(null);
	}
	public static List<Shader.Data> getShaders(Entity entity, TexturedEntityEntry.SpectatorShader spectatorShader) {
		List<Shader.Data> shaders = new ArrayList<>();
		if (entity != null) {
			if (spectatorShader != null) {
				ShaderPackEntry shaderPack = ShaderPacks.getShaderPack(spectatorShader.registry(), spectatorShader.shaderPack());
				if (shaderPack == null && spectatorShader.shaderPack().equals(TexturedEntityShader.random)) {
					// Check if shader isn't valid AND id is perspective:random, then set shaderPack to random based on uuid.
					shaderPack = (ShaderPackEntry) ListHelper.getRandom(entity.getUuid(), ShaderPacks.getRegistry(spectatorShader.registry()).values().stream().toList());
				}
				if (shaderPack != null) {
					int i = 0;
					for (ShaderPackEntry.Shader shader : shaderPack.shaders()) {
						shaders.add(new Shader.Data(getTexturedEntityId(String.valueOf(i++)), new Shader(Shaders.get(shader.registry(), shader.luminance()), () -> Shader.RenderType.WORLD, () -> ClientData.minecraft.cameraEntity != null && (ClientData.minecraft.cameraEntity == entity))));
					}
				} else Data.getVersion().sendToLog(LogType.WARN, "Could not locate the current shader pack!: " + spectatorShader.registry() + ":" + spectatorShader.shaderPack());
			}
		}
		return shaders;
	}
}