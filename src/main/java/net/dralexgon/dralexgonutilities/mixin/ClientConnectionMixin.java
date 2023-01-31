package net.dralexgon.dralexgonutilities.mixin;

import net.dralexgon.dralexgonutilities.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.dralexgonutilities.EnchantedBookFinderManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(at = @At("HEAD"), method = "handlePacket", cancellable = true)
    private static <T extends PacketListener> void handlePacketMixin(Packet<T> packet, PacketListener listener, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 3) {
            client.player.sendMessage(Text.of("S2C Packet: " + packet.getClass().getSimpleName()));
        }
        if (!EnchantedBookFinderManager.ENABLED) return;

        if (packet instanceof OpenScreenS2CPacket) {
            if (!(EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.WAITING_FOR_TRADE_OFFER)) return;
            OpenScreenS2CPacket openScreenS2CPacket = (OpenScreenS2CPacket) packet;
            if (openScreenS2CPacket.getScreenHandlerType() == ScreenHandlerType.MERCHANT) ci.cancel();
        }
        if (packet instanceof SetTradeOffersS2CPacket) {
            if (!(EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.WAITING_FOR_TRADE_OFFER)) return;
            EnchantedBookFinderManager.lastEnchantedBook = null;
            SetTradeOffersS2CPacket setTradeOffersS2CPacket = (SetTradeOffersS2CPacket) packet;
            setTradeOffersS2CPacket.getOffers().forEach(tradeOffer -> {
                ItemStack item = tradeOffer.getSellItem();
                if (item.hasNbt()) {
                    item.getNbt().getKeys().forEach(key -> {
                        if (key.equals("StoredEnchantments")) {
                            String id = item.getNbt().get("StoredEnchantments").asString().split("\"")[1];
                            String lvl = item.getNbt().get("StoredEnchantments").asString().split("lvl:")[1].split("s}")[0];
                            if ((id).equals("minecraft:" + EnchantedBookFinderManager.ENCHANTED_BOOKS_FINDER_TYPE)) {
                                Text text = Text.of("§2Found " + EnchantedBookFinderManager.ENCHANTED_BOOKS_FINDER_TYPE + " book!");
                                client.player.sendMessage(text);
                                client.player.sendMessage(text, true);
                                client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));
                                EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.FOUND_ENCHANTED_BOOK;
                                EnchantedBookFinderManager.ENABLED = false;
                            } else {
                                client.player.sendMessage(Text.of("§4Found " + id + " book."), true);
                                EnchantedBookFinderManager.lastEnchantedBook = lvl;
                            }
                        }
                    });
                }
            });
            if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.FOUND_ENCHANTED_BOOK) {
                return;
            }
            if (EnchantedBookFinderManager.lastEnchantedBook == null) client.player.sendMessage(Text.of("§4No enchanted book this trade."), true);
            ci.cancel();
            EnchantedBookFinderManager.littleReset();
            EnchantedBookFinderManager.breakLectern();
            //The loop of breaking lectern is closed
        }
    }

    @Inject(at = @At("HEAD"), method = "send")
    private <T extends PacketListener> void sendPacketMixin(Packet<T> packet, CallbackInfo ci) {
        if (!(EnchantedBookFinderManager.DEBUG == 1 || EnchantedBookFinderManager.DEBUG == 2)) return;
        MinecraftClient.getInstance().player.sendMessage(Text.of("C2S Packet: " + packet.getClass().getSimpleName()), false);
    }
}
