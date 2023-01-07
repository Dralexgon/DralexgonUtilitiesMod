package net.dralexgon.learnmodding.item;

import net.dralexgon.learnmodding.LearnModding;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModItems {

    public static final Item DRAGONITE = registerItem("dragonite", Arrays.asList(ModItemGroups.DRAGONITE, ItemGroups.INGREDIENTS));
    public static final Item RAW_DRAGONITE = registerItem("raw_dragonite", Arrays.asList(ModItemGroups.DRAGONITE, ItemGroups.INGREDIENTS));

    public static Item registerItem(String name, List<ItemGroup> itemGroups) {
        Item item = Registry.register(Registries.ITEM, new Identifier(LearnModding.MOD_ID, name), new Item(new Item.Settings()));
        for (ItemGroup itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        }
        return item;
    }

    public static void registerModItems() {
        LearnModding.LOGGER.debug("Registering Mod Items for " + LearnModding.MOD_ID);
    }
}
