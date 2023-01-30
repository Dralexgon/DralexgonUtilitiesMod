package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.learnmodding.EnchantedBookFinderManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.C2SPlayChannelEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;

public class TickEventHandler {

    public static void registerTickEventHandler() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            //client.player.sendMessage(Text.of("§2 Tick !"), false);
            if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.WAITING_FOR_TRADE_OFFER) {
                if (System.currentTimeMillis() - EnchantedBookFinderManager.waitingTimestamp > 3000) {
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER;
                }
            }
            if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER) {
                searchingVillager(client);
            }else if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.MINING) {
                PlayerEntity player = client.player;
                int nbOfLecterns = 0;
                for (int i = 0; i < player.getInventory().size(); i++) {
                    if (player.getInventory().getStack(i).getItem().equals(Items.LECTERN)) {
                        nbOfLecterns += player.getInventory().getStack(i).getCount();
                    }
                }
                if (nbOfLecterns == EnchantedBookFinderManager.nbLecterns + 1) {
                    /*
                    BlockPos playerPos = EnchantedBookFinderManager.startingPlayerPos;
                    EnchantedBookFinderManager.waitingTimestamp = System.currentTimeMillis();
                    client.player.networkHandler.sendChatMessage("#goto " + playerPos.getX() + " " + playerPos.getY() + " " + playerPos.getZ());
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.WAITING_BARITONE_GOTO;*/
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER;
                }
            }/*
            else if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.WAITING_BARITONE_GOTO) {
                if (client.player.getBlockPos().equals(EnchantedBookFinderManager.startingPlayerPos)) {
                    client.player.sendMessage(Text.of("§2 try placing "), false);
                    BlockPos lp = EnchantedBookFinderManager.lecternPos;
                    BlockHitResult hitResult = new BlockHitResult(new Vec3d(lp.getX(), lp.getY(), lp.getZ()), Direction.UP, lp, false);
                    Vec3d pos = new Vec3d(lp.getX(), lp.getY(), lp.getZ());
                    client.interactionManager.interactBlock(
                            client.player, Hand.OFF_HAND, hitResult);
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER;
                }
            }*/
        });
    }

    public static void searchingVillager(MinecraftClient client) {
        if (client.world == null) {
            EnchantedBookFinderManager.ENABLED = false;
            return;
        }

        double minDistance = Double.MAX_VALUE;
        Entity closestVillager = null;

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof VillagerEntity) {
                Vec3d pos = entity.getPos();
                if ((pos.distanceTo(client.player.getPos()) < minDistance)) {
                    minDistance = pos.distanceTo(client.player.getPos());
                    closestVillager = entity;
                }
            }
        }
        if (closestVillager != null) {
            client.interactionManager.interactEntity(client.player, closestVillager, client.player.getActiveHand());
            EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.WAITING_FOR_TRADE_OFFER;
        }
    }
}
