package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.EnchantedBookFinderManager;
import net.dralexgon.learnmodding.LearnModdingClient;
import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "key.category.learnmodding.cheats";
    public static final String ENCHANTED_BOOKS_FINDER = "key.learnmodding.enchanted_books_finder";
    public static KeyBinding enchantedBooksFinderKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (enchantedBooksFinderKey.wasPressed()) {
                if (EnchantedBookFinderManager.DEBUG != -1) {
                    EnchantedBookFinderManager.DEBUG = (EnchantedBookFinderManager.DEBUG + 1) % 10;
                    client.player.sendMessage(Text.of("§4Debug :" + EnchantedBookFinderManager.DEBUG), true);
                    if (EnchantedBookFinderManager.DEBUG == 4) {
                        Packet packetStartDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                        client.getNetworkHandler().sendPacket(packetStartDestroyBlock);
                        Packet packetStopDestroyBlock = new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, EnchantedBookFinderManager.lecternPos, Direction.UP);
                        client.getNetworkHandler().sendPacket(packetStopDestroyBlock);
                    }
                    return;
                }
                if (EnchantedBookFinderManager.ENABLED) {
                    EnchantedBookFinderManager.ENABLED = false;
                    client.player.sendMessage(Text.of("§4Enchanted books finder disabled"), true);
                } else {
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
