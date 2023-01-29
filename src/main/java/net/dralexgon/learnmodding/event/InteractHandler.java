package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.learnmodding.LearnModdingClient;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class InteractHandler {

    public static void registerInteractHandler() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            MinecraftClient mc = MinecraftClient.getInstance();

            if (LearnModdingClient.ENCHANTED_BOOKS_FINDER_ENABLED && LearnModdingClient.STATE == ENCHANTED_BOOK_FINDER_STATE.READY) {

                if (block.equals(Blocks.LECTERN)) {//toString().equals("Block{minecraft:lectern}")
                    //count all lectern in the inventory of the player
                    int nbOfLecterns = 0;
                    for (int i = 0; i < player.getInventory().size(); i++) {
                        if (player.getInventory().getStack(i).getItem().equals(Items.LECTERN)) {
                            nbOfLecterns += player.getInventory().getStack(i).getCount();
                        }
                    }
                    //mc.player.sendMessage(Text.of("§2Starting searching for " + LearnModdingClient.ENCHANTED_BOOKS_FINDER_TYPE + " books..."), true);
                    mc.player.networkHandler.sendChatMessage("#mine " + (nbOfLecterns + 1) + " lectern");
                    LearnModdingClient.STATE = ENCHANTED_BOOK_FINDER_STATE.MINING;
                    return ActionResult.PASS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
