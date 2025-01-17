/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.item;

import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.TexturedEntityDataLoader;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGroup {
	protected static ItemGroupData texturedEntity;
	public static void init() {
		// The items in the item group are refreshed on world load.
		// Ideally, we would be able to refresh them on resource load as well.
		ItemGroupEvents.modifyEntriesEvent(texturedEntity.key()).register(content -> {
			for (TexturedEntityData data : TexturedEntityDataLoader.getSortedRegistry()) {
				if (data.getEnabled() && data.getItemGroup() && !data.getName().equalsIgnoreCase("default")) {
					addItem(data, content);
				}
			}
		});
	}
	private static void addItem(TexturedEntityData data, FabricItemGroupEntries content) {
		Item item = SpawnEggItem.forEntity(TexturedEntity.getEntityType(Identifier.of(data.getNamespace(), data.getType())));
		ItemStack itemStack = item != null ? item.getDefaultStack() : Items.GOAT_SPAWN_EGG.getDefaultStack();
		itemStack.set(DataComponentTypes.CUSTOM_NAME, Translation.getItemTranslation(Data.getVersion().getID(), "textured_entity_spawn_egg", new Object[]{data.getName(), Text.translatable("entity." + data.getNamespace() + "." + data.getType())}));
		NbtCompound entityData = new NbtCompound();
		entityData.putString("id", Identifier.of(data.getNamespace(), data.getType()).toString());
		entityData.putString("CustomName", "[{\"text\": " + data.getName() + "}]");
		itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(entityData));
		content.add(itemStack, net.minecraft.item.ItemGroup.StackVisibility.PARENT_TAB_ONLY);
	}
	public static ItemGroupData register(Identifier id, net.minecraft.item.ItemGroup itemGroup) {
		RegistryKey<net.minecraft.item.ItemGroup> key = RegistryKey.of(Registries.ITEM_GROUP.getKey(), id);
		return new ItemGroupData(key, Registry.register(Registries.ITEM_GROUP, key, itemGroup));
	}
	static {
		texturedEntity = register(Identifier.of(Data.getVersion().getID(), "textured_entity"), FabricItemGroup.builder().icon(() -> new ItemStack(Items.CAT_SPAWN_EGG)).displayName(Translation.getItemGroupTranslation(Data.getVersion().getID(), "textured_entity", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name")})).build());
	}

	public record ItemGroupData(RegistryKey<net.minecraft.item.ItemGroup> key, net.minecraft.item.ItemGroup itemGroup) {
	}
}
