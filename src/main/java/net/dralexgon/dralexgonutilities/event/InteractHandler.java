package net.dralexgon.dralexgonutilities.event;

import net.dralexgon.dralexgonutilities.enchantedbookfinder.STATE;
import net.dralexgon.dralexgonutilities.enchantedbookfinder.EnchantedBookFinderManager;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class InteractHandler {

    public static void registerInteractHandler() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            MinecraftClient mc = MinecraftClient.getInstance();
            if (EnchantedBookFinderManager.ENABLED && EnchantedBookFinderManager.STATE == STATE.READY) {
                if (block.equals(Blocks.LECTERN)) {
                    EnchantedBookFinderManager.lecternPos = hitResult.getBlockPos();
                    mc.player.sendMessage(Text.of("§2Lectern register"), true);
                    EnchantedBookFinderManager.breakLectern();
                    return ActionResult.PASS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
