package net.dralexgon.learnmodding.mixin;

import net.dralexgon.learnmodding.EnchantedBookFinderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(at = @At("HEAD"), method = "handlePacket")
    private static <T extends PacketListener> void handlePacketMixin(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 3) {
            mc.player.sendMessage(Text.of("S2C Packet: " + packet.getClass().getSimpleName()), false);
        }
        if (packet instanceof SetTradeOffersS2CPacket) {
            SetTradeOffersS2CPacket setTradeOffersS2CPacket = (SetTradeOffersS2CPacket) packet;
            setTradeOffersS2CPacket.getOffers().forEach(tradeOffer -> {
                ItemStack item = tradeOffer.getSellItem();
                item.getEnchantments().forEach(enchantment -> {
                    mc.player.sendMessage(Text.of("enchant :" + enchantment.asString()));
                });
                mc.player.sendMessage(Text.of(item.getName().getString()));
            });
            mc.getNetworkHandler().onSetTradeOffers(null);
            //ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "send")
    private <T extends PacketListener> void sendPacketMixin(Packet<T> packet, CallbackInfo ci) {
        if (!(EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 2)) return;
        MinecraftClient.getInstance().player.sendMessage(Text.of("C2S Packet: " + packet.getClass().getSimpleName()), false);
    }
}
