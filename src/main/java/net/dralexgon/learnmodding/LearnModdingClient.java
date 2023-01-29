package net.dralexgon.learnmodding;

import net.dralexgon.learnmodding.event.InteractHandler;
import net.dralexgon.learnmodding.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;

public class LearnModdingClient implements ClientModInitializer {

    public static boolean ENCHANTED_BOOKS_FINDER_ENABLED = true;
    public static final String ENCHANTED_BOOKS_FINDER_TYPE = "Mending";
    public static ENCHANTED_BOOK_FINDER_STATE STATE = ENCHANTED_BOOK_FINDER_STATE.READY;

    @Override
    public void onInitializeClient() {
        // This code runs when Minecraft is in the client.
        KeyInputHandler.registerKeyBindings();
        InteractHandler.registerInteractHandler();
    }
}
