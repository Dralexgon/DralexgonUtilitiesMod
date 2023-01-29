package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.LearnModdingClient;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class InteractHandler {

    public static void registerInteractHandler() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            MinecraftClient.getInstance().player.sendMessage(Text.of("test1 passed !"), false);
            if (LearnModdingClient.ENCHANTED_BOOKS_FINDER_ENABLED) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("test2 passed !"), false);
                if (block.toString().equals("Block{minecraft:lectern}")) {
                    MinecraftClient.getInstance().player.sendMessage(Text.of("#mine lectern 1"));
                    MinecraftClient.getInstance().player.sendMessage(Text.of("test3 passed !"), false);
                    MinecraftClient.getInstance().player.sendMessage(Text.of("§2Starting searching for " + LearnModdingClient.ENCHANTED_BOOKS_FINDER_TYPE + " books..."), true);
                    MinecraftClient.getInstance().interactionManager.breakBlock(hitResult.getBlockPos());
                    return ActionResult.PASS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
