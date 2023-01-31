package net.dralexgon.dralexgonutilities;

import net.dralexgon.dralexgonutilities.enchantedbookfinder.EnchantedBookFinderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import net.minecraft.text.Text;

public class Debug {

    public static void onPacketS2C(Packet<?> packet) {
        if (!(EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 3)) return;
        MinecraftClient client = MinecraftClient.getInstance();
        client.player.sendMessage(Text.of("S2C Packet: " + packet.getClass().getSimpleName()));
    }

    public static void onPacketC2S(Packet<?> packet) {
        if (!(EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 2)) return;
        MinecraftClient client = MinecraftClient.getInstance();
        client.player.sendMessage(Text.of("C2S Packet: " + packet.getClass().getSimpleName()), false);
    }
}
