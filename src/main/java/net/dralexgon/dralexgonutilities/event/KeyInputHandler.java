package net.dralexgon.dralexgonutilities.event;

import net.dralexgon.dralexgonutilities.EnchantedBookFinderManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "key.category.dralexgonutilities.cheats";
    public static final String ENCHANTED_BOOKS_FINDER = "key.dralexgonutilities.enchanted_books_finder";
    public static KeyBinding enchantedBooksFinderKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (enchantedBooksFinderKey.wasPressed()) {
                if (EnchantedBookFinderManager.DEBUG != -1) {
                    EnchantedBookFinderManager.DEBUG = (EnchantedBookFinderManager.DEBUG + 1) % 10;
                    client.player.sendMessage(Text.of("§4Debug :" + EnchantedBookFinderManager.DEBUG));
                    if (EnchantedBookFinderManager.DEBUG == 4) {
                        Packet packetStartDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                        client.getNetworkHandler().sendPacket(packetStartDestroyBlock);
                        //Packet packetStopDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                        //client.getNetworkHandler().sendPacket(packetStopDestroyBlock);
                    }
                    return;
                }
                if (EnchantedBookFinderManager.ENABLED) {
                    EnchantedBookFinderManager.ENABLED = false;
                    client.player.sendMessage(Text.of("§4Enchanted books finder disabled"), true);
                } else {
                    if (EnchantedBookFinderManager.countLectern() == 0) {
                        client.player.sendMessage(Text.of("§4You need at least one lectern in your inventory"));
                        client.player.sendMessage(Text.of("§4You need at least one lectern in your inventory"), true);
                        return;
                    }
                    if (!client.player.getStackInHand(Hand.OFF_HAND).getItem().equals(Items.LECTERN)) {
                        int lecternSlot = -1;
                        for (int i = 0; i < client.player.getInventory().size(); i++) {
                            if (client.player.getInventory().getStack(i).getItem().equals(Items.LECTERN)) {
                                lecternSlot = i;
                                break;
                            }
                        }
                        client.player.getInventory().swapSlotWithHotbar(lecternSlot);
                        client.player.swingHand(Hand.OFF_HAND);
                    }
                    EnchantedBookFinderManager.ENABLED = true;
                    client.player.sendMessage(Text.of("§2Enchanted books finder enabled"), true);
                    EnchantedBookFinderManager.reset();
                }
            }
        });
    }

    public static void registerKeyBindings() {
        enchantedBooksFinderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                ENCHANTED_BOOKS_FINDER,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_0,
                CATEGORY
        ));

        registerKeyInputs();
    }
}
