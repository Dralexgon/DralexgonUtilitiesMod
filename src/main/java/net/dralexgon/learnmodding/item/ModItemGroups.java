package net.dralexgon.learnmodding.item;

import net.dralexgon.learnmodding.LearnModding;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static ItemGroup DRAGONITE = FabricItemGroup.builder(new Identifier(LearnModding.MOD_ID, "dragonite"))
            //.displayName(Text.literal("Dragonite things"))
            .icon(() -> new ItemStack(ModItems.DRAGONITE))
            .build();

}
