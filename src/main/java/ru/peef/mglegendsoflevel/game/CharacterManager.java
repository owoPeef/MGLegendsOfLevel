package ru.peef.mglegendsoflevel.game;

import ru.peef.mglegendsoflevel.game.characters.AssassinCharacter;
import ru.peef.mglegendsoflevel.game.characters.CollectorCharacter;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager {
    private static final List<GameCharacter> characters = new ArrayList<>();

    public static void init() {
        characters.add(new AssassinCharacter());
        characters.add(new CollectorCharacter());
    }

    public static GameCharacter getCharacter(String name) {
        for (GameCharacter character: getCharacters()) {
            if (character.getName().equalsIgnoreCase(name))
                return character;
        }
        return null;
    }

    public static List<GameCharacter> getCharacters() { return characters; }
}
