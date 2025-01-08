/*
    Perspective
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/Perspective
    Licence: GNU LGPLv3
*/

package com.mclegoman.perspective.client.item;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.perspective.client.entity.TexturedEntity;
import com.mclegoman.perspective.client.entity.TexturedEntityData;
import com.mclegoman.perspective.client.entity.TexturedEntityDataLoader;
import com.mclegoman.perspective.client.translation.Translation;
import com.mclegoman.perspective.common.data.Data;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
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
					SpawnEggItem item = SpawnEggItem.forEntity(TexturedEntity.getEntityTypeId(Identifier.of(data.getNamespace(), data.getType())));
					if (item != null) {
						ItemStack itemStack = item.getDefaultStack();
						itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.of(data.getName()));
						content.add(itemStack, net.minecraft.item.ItemGroup.StackVisibility.PARENT_TAB_ONLY);
					} else {
						Data.version.sendToLog(LogType.WARN, "Item returned null when adding '" + data.getNamespace() + ":" + data.getType() + ":" + data.getName() + "' to the Textured Entity Item Group!");
					}
				}
			}
		});
	}
	public static ItemGroupData register(Identifier id, net.minecraft.item.ItemGroup itemGroup) {
		RegistryKey<net.minecraft.item.ItemGroup> key = RegistryKey.of(Registries.ITEM_GROUP.getKey(), id);
		return new ItemGroupData(key, Registry.register(Registries.ITEM_GROUP, key, itemGroup));
	}
	static {
		texturedEntity = register(Identifier.of(Data.version.getID(), "textured_entity"), FabricItemGroup.builder().icon(() -> new ItemStack(Items.CAT_SPAWN_EGG)).displayName(Translation.getItemGroupTranslation(Data.version.getID(), "textured_entity", new Object[]{Translation.getTranslation(Data.version.getID(), "name")})).build());
	}

	public record ItemGroupData(RegistryKey<net.minecraft.item.ItemGroup> key, net.minecraft.item.ItemGroup itemGroup) {
	}
}
