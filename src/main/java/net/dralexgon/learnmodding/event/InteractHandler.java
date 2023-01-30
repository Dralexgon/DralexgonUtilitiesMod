package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.learnmodding.EnchantedBookFinderManager;
import net.dralexgon.learnmodding.LearnModdingClient;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;

public class InteractHandler {

    public static void registerInteractHandler() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            MinecraftClient mc = MinecraftClient.getInstance();

            if (EnchantedBookFinderManager.ENABLED && EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.READY) {

                if (block.equals(Blocks.LECTERN)) {
                    int nbOfLecterns = 0;
                    for (int i = 0; i < player.getInventory().size(); i++) {
                        if (player.getInventory().getStack(i).getItem().equals(Items.LECTERN)) {
                            nbOfLecterns += player.getInventory().getStack(i).getCount();
                        }
                    }
                    EnchantedBookFinderManager.startingPlayerPos = player.getBlockPos();
                    EnchantedBookFinderManager.lecternPos = hitResult.getBlockPos();
                    EnchantedBookFinderManager.nbLecterns = nbOfLecterns;
                    mc.player.sendMessage(Text.of("§2Lectern register"));
                    Packet packetStartDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                    mc.getNetworkHandler().sendPacket(packetStartDestroyBlock);
                    Packet packetStopDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                    mc.getNetworkHandler().sendPacket(packetStopDestroyBlock);
                    /*
                    mc.player.networkHandler.sendChatMessage("#mine " + (nbOfLecterns + 1) + " lectern");*/
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.MINING;
                    return ActionResult.PASS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
