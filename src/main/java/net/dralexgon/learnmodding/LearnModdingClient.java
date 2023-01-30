package net.dralexgon.learnmodding;

import net.dralexgon.learnmodding.event.InteractHandler;
import net.dralexgon.learnmodding.event.KeyInputHandler;
import net.dralexgon.learnmodding.event.TickEventHandler;
import net.fabricmc.api.ClientModInitializer;

public class LearnModdingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // This code runs when Minecraft is in the client.
        EnchantedBookFinderManager.init();

        KeyInputHandler.registerKeyBindings();
        InteractHandler.registerInteractHandler();
        TickEventHandler.registerTickEventHandler();
    }
}
