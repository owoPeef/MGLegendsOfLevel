package ru.peef.mglegendsoflevel.game;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArenaManager {
    private static final List<Arena> arenas = new ArrayList<>();

    public static void init() {
        arenas.add(new Arena("-243.0,-58,8.0", new String[]{"-255,-58,8", "-230,-58,8", "-243,-58,15", "-241,-58,0"}));
        arenas.add(new Arena(5000, "-294.0,-58,8.0", new String[]{"-279.0,-55.5,7.0", "-300.5,-58,18.5", "-300.5,-58,-3.5", "-288,-58,-7.5"}));
    }

    public static List<Arena> getArenas() { return arenas; }

    @Nullable
    public static Arena getFreeArena() {
        List<Arena> freeArenas = new ArrayList<>();
        for (Arena arena: getArenas()) {
            if (arena.isFree()) {
                freeArenas.add(arena);
            }
        }

        if (freeArenas.isEmpty()) return null;
        else {
            Random rand = new Random();
            return freeArenas.get(rand.nextInt(freeArenas.size()));
        }
    }
}
