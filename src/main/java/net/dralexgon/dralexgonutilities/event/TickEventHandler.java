package net.dralexgon.dralexgonutilities.event;

import net.dralexgon.dralexgonutilities.ENCHANTED_BOOK_FINDER_STATE;
import net.dralexgon.dralexgonutilities.EnchantedBookFinderManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TickEventHandler {

    public static void registerTickEventHandler() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client == null || client.world == null || client.player == null) {
                return;
            }
            if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.WAITING_FOR_TRADE_OFFER) {
                if (System.currentTimeMillis() - EnchantedBookFinderManager.waitingTimestamp > 3000) {
                    EnchantedBookFinderManager.STATE = ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER;
                }
            }
            if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.SEARCHING_VILLAGER) {
                EnchantedBookFinderManager.searchingVillager();
            }else if (EnchantedBookFinderManager.STATE == ENCHANTED_BOOK_FINDER_STATE.MINING) {
                if (EnchantedBookFinderManager.countLectern() == EnchantedBookFinderManager.nbLecterns + 1) {
                    EnchantedBookFinderManager.placeLectern();
                }
            }
        });
    }


}
