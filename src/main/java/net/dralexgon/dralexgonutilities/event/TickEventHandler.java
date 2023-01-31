package net.dralexgon.dralexgonutilities.event;

import net.dralexgon.dralexgonutilities.enchantedbookfinder.STATE;
import net.dralexgon.dralexgonutilities.enchantedbookfinder.EnchantedBookFinderManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class TickEventHandler {

    public static void registerTickEventHandler() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client == null || client.world == null || client.player == null) {
                return;
            }
            if (EnchantedBookFinderManager.STATE == STATE.WAITING_FOR_TRADE_OFFER) {
                if (System.currentTimeMillis() - EnchantedBookFinderManager.waitingTimestamp > 3000) {
                    EnchantedBookFinderManager.STATE = STATE.SEARCHING_VILLAGER;
                }
            }
            if (EnchantedBookFinderManager.STATE == STATE.SEARCHING_VILLAGER) {
                EnchantedBookFinderManager.searchingVillager();
            }else if (EnchantedBookFinderManager.STATE == STATE.MINING) {
                if (EnchantedBookFinderManager.countLectern() == EnchantedBookFinderManager.nbLecterns + 1) {
                    EnchantedBookFinderManager.placeLectern();
                }
            }
        });
    }


}
