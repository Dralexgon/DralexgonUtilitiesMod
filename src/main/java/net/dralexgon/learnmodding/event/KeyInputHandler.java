package net.dralexgon.learnmodding.event;

import net.dralexgon.learnmodding.LearnModdingClient;
import net.dralexgon.learnmodding.ENCHANTED_BOOK_FINDER_STATE;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {

    public static final String CATEGORY = "key.category.learnmodding.cheats";
    public static final String ENCHANTED_BOOKS_FINDER = "key.learnmodding.enchanted_books_finder";
    public static KeyBinding enchantedBooksFinderKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (enchantedBooksFinderKey.wasPressed()) {
                if (LearnModdingClient.ENCHANTED_BOOKS_FINDER_ENABLED) {
                    LearnModdingClient.ENCHANTED_BOOKS_FINDER_ENABLED = false;
                    client.player.sendMessage(Text.of("§4Enchanted books finder disabled"), true);
                } else {
                    LearnModdingClient.ENCHANTED_BOOKS_FINDER_ENABLED = true;
                    client.player.sendMessage(Text.of("§2Enchanted books finder enabled"), true);
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
