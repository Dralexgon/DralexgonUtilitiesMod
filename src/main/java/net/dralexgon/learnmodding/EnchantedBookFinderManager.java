package net.dralexgon.learnmodding;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;

public class EnchantedBookFinderManager {
    public static final String ENCHANTED_BOOKS_FINDER_TYPE = "Mending";




    public static boolean ENABLED;
    public static int DEBUG;
    public static ENCHANTED_BOOK_FINDER_STATE STATE;
    public static long waitingTimestamp;
    public static int nbLecterns;
    public static BlockPos startingPlayerPos;
    public static BlockPos lecternPos;

    public static void init() {
        ENABLED = true;
        DEBUG = -1;
        reset();
    }

    public static void reset() {
        STATE = ENCHANTED_BOOK_FINDER_STATE.READY;
        waitingTimestamp = 0;
        nbLecterns = 0;
        startingPlayerPos = null;
        lecternPos = null;
    }
}
