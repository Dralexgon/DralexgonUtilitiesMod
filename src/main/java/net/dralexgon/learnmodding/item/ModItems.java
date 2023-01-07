package net.dralexgon.learnmodding.item;

import net.dralexgon.learnmodding.LearnModding;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class ModItems {


    //public static final Item[] ITEMS = {}
    public static final Item RAW_DRAGONITE = registerItem("raw_dragonite");

    public static final Item DRAGONITE = registerItem("dragonite");

    public static ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("dragonite", "dragonite"))
            .displayName(Text.literal("All the dragonite things"))
            .icon(() -> new ItemStack(DRAGONITE))
            .build();


    public static Item registerItem(String name) {
        return Registry.register(Registries.ITEM, new Identifier(LearnModding.MOD_ID, name), new Item(new Item.Settings()));
    }

    public static void registerModItems() {
        LearnModding.LOGGER.debug("Registering Mod Items for " + LearnModding.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(DRAGONITE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(RAW_DRAGONITE));
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> entries.add(RAW_DRAGONITE));
    }

}
