package net.dralexgon.learnmodding.item;

import net.dralexgon.learnmodding.LearnModding;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModItems {

    public static final List<ItemStack> ITEMS = Arrays.asList(
            registerItemStack("dragonite"),
            registerItemStack("raw_dragonite")
    );

    public static ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier("dragonite", "dragonite"))
            .displayName(Text.literal("Dragonite things"))
            .icon(() -> ITEMS.get(0))
            .build();

    public static void registerModItems() {
        LearnModding.LOGGER.debug("Registering Mod Items for " + LearnModding.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> entries.addAll(ITEMS));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.addAll(ITEMS.subList(0,2)));
    }

    public static Item registerItem(String name) {
        return Registry.register(Registries.ITEM, new Identifier(LearnModding.MOD_ID, name), new Item(new Item.Settings()));
    }

    public static ItemStack registerItemStack(String name) {
        return new ItemStack(registerItem(name));
    }
}
