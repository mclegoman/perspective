/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.item;

import com.mclegoman.perspective.client.entity.TexturedEntityEntry;
import com.mclegoman.perspective.client.entity.TexturedEntityDataReloader;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
			for (TexturedEntityEntry data : TexturedEntityDataReloader.getSortedRegistry()) {
				if (data.getEnabled() && data.getItemGroup() && !data.getName().equalsIgnoreCase("default")) {
					addItem(data, content);
				}
			}
		});
	}
	private static void addItem(TexturedEntityEntry data, FabricItemGroupEntries content) {
		ItemStack itemStack = Items.PIG_SPAWN_EGG.getDefaultStack();
		itemStack.set(DataComponentTypes.ITEM_NAME, Translation.getItemTranslation(Data.getVersion().getID(), "textured_entity_spawn_egg", new Object[]{data.getName(), Text.translatable("entity." + data.getNamespace() + "." + data.getType())}));
		NbtCompound entityData = new NbtCompound();
		entityData.putString("id", Identifier.of(data.getNamespace(), data.getType()).toString());
		entityData.putString("CustomName", "[{\"text\": " + data.getName() + "}]");
		itemStack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(entityData));
		if (data.getItemModel() != null) itemStack.set(DataComponentTypes.ITEM_MODEL, data.getItemModel());
		content.add(itemStack, net.minecraft.item.ItemGroup.StackVisibility.PARENT_TAB_ONLY);
	}
	public static ItemGroupData register(Identifier id, net.minecraft.item.ItemGroup itemGroup) {
		RegistryKey<net.minecraft.item.ItemGroup> key = RegistryKey.of(Registries.ITEM_GROUP.getKey(), id);
		return new ItemGroupData(key, Registry.register(Registries.ITEM_GROUP, key, itemGroup));
	}
	private static ItemStack getIconStack() {
		ItemStack itemStack = Items.PIG_SPAWN_EGG.getDefaultStack();
		itemStack.set(DataComponentTypes.ITEM_MODEL, Identifier.of("technoblade_pig_spawn_egg"));
		return itemStack;
	}
	static {
		texturedEntity = register(Identifier.of(Data.getVersion().getID(), "textured_entity"), FabricItemGroup.builder().icon(ItemGroup::getIconStack).displayName(Translation.getItemGroupTranslation(Data.getVersion().getID(), "textured_entity", new Object[]{Translation.getTranslation(Data.getVersion().getID(), "name")})).build());
	}

	public record ItemGroupData(RegistryKey<net.minecraft.item.ItemGroup> key, net.minecraft.item.ItemGroup itemGroup) {
	}
}
