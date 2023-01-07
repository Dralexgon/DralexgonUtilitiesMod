package net.dralexgon.learnmodding.block;

import net.dralexgon.learnmodding.LearnModding;
import net.dralexgon.learnmodding.item.ModItemGroups;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public class ModBlocks {

    public static final Block DRAGONITE_BLOCK = registerBlock("dragonite_block",
            new Block(FabricBlockSettings.of(Material.METAL).hardness(5.0f).requiresTool()),//play with these settings
            Arrays.asList(ModItemGroups.DRAGONITE, ItemGroups.BUILDING_BLOCKS));

    public static Block registerBlock(String name, Block block, List<ItemGroup> itemGroups) {
        registerBlockItem(name, block, itemGroups);
        return Registry.register(Registries.BLOCK, new Identifier(LearnModding.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block, List<ItemGroup> itemGroups) {
        Item item = Registry.register(Registries.ITEM, new Identifier(LearnModding.MOD_ID, name), new BlockItem(block, new Item.Settings()));
        for (ItemGroup itemGroup : itemGroups) {
            ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
        }
        return item;
    }

    public static void registerModBlocks() {
        LearnModding.LOGGER.debug("Registering Mod Blocks for " + LearnModding.MOD_ID);

    }
}
