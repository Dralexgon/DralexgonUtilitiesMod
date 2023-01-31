package net.dralexgon.dralexgonutilities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class EnchantedBookFinderManager {
    public static final String ENCHANTED_BOOKS_FINDER_TYPE = "mending";




    public static boolean ENABLED;
    public static int DEBUG;
    public static ENCHANTED_BOOK_FINDER_STATE STATE;
    public static long waitingTimestamp;
    public static int nbLecterns;
    public static BlockPos lecternPos;


    public static String lastEnchantedBook;
    public static MinecraftClient client;

    public static void init() {
        ENABLED = true;
        DEBUG = -1;

        client = MinecraftClient.getInstance();
        reset();
    }

    public static void littleReset() {
        waitingTimestamp = 0;
        nbLecterns = 0;
    }

    public static void reset() {
        STATE = ENCHANTED_BOOK_FINDER_STATE.READY;
        waitingTimestamp = 0;
        nbLecterns = 0;
        lecternPos = null;
    }

    public static int countLectern() {
        PlayerEntity player = client.player;
        int nbOfLecterns = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).getItem().equals(Items.LECTERN)) {
                nbOfLecterns += player.getInventory().getStack(i).getCount();
            }
        }
        return nbOfLecterns;
    }

    public static void breakLectern() {
        EnchantedBookFinderManager.nbLecterns = EnchantedBookFinderManager.countLectern();
        Packet packetStartDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
        client.getNetworkHandler().sendPacket(packetStartDestroyBlock);
        Packet packetStopDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
        client.getNetworkHandler().sendPacket(packetStopDestroyBlock);
        EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.MINING;
    }

    public static void placeLectern() {//use packet instead
        BlockPos lp = EnchantedBookFinderManager.lecternPos;
        BlockHitResult hitResult = new BlockHitResult(new Vec3d(lp.getX(), lp.getY(), lp.getZ()), Direction.UP, lp, false);
        client.interactionManager.interactBlock(
                client.player, Hand.OFF_HAND, hitResult);
        EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER;
    }

    public static void searchingVillager() {
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
