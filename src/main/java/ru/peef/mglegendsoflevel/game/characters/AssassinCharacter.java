package ru.peef.mglegendsoflevel.game.characters;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.peef.mglegendsoflevel.game.GameCharacter;

import java.util.List;

public class AssassinCharacter extends GameCharacter {
    public AssassinCharacter() {
        super("assassin",
                "&bАссасин",
                "&3Скрытный",
                "&bМастер скрытности и беспалевных убийств. Быстр и ловок – это про данный класс.\n\nИмеет &e&nпреимущества&r&b в виде:\n&e* &aСкорость II\n&e* &aУрон по мобам x1.25\n\n&8[!] &2Шанс 14% нанести двойной урон мобу",
                1.25f,
                1f,
                List.of(new PotionEffect[]{ new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false) }));
    }

    @Override
    public double getMobDamageMultiplier() {
        double finalDamage = getOriginalMobDamageMultiplier();
        if (0 + Math.random() * 100 <= 14) {
            finalDamage *= 2;
        }
        return finalDamage;
    }

    @Override
    public double getMobReceivedDamageMultiplier() { return getOriginalMobReceivedDamageMultiplier(); }
}
