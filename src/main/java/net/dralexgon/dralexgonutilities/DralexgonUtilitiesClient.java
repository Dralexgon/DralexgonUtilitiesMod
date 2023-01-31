package net.dralexgon.dralexgonutilities;

import net.dralexgon.dralexgonutilities.enchantedbookfinder.EnchantedBookFinderManager;
import net.dralexgon.dralexgonutilities.event.InteractHandler;
import net.dralexgon.dralexgonutilities.event.KeyInputHandler;
import net.dralexgon.dralexgonutilities.event.TickEventHandler;
import net.fabricmc.api.ClientModInitializer;

public class DralexgonUtilitiesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // This code runs when Minecraft is in the client.
        EnchantedBookFinderManager.init();

        KeyInputHandler.registerKeyBindings();
        InteractHandler.registerInteractHandler();
        TickEventHandler.registerTickEventHandler();
    }
}
