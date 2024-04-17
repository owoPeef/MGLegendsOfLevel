package ru.peef.mglegendsoflevel.game.characters;

import ru.peef.mglegendsoflevel.game.GameCharacter;

public class CollectorCharacter extends GameCharacter {
    public CollectorCharacter() {
        super("collector", "&bКоллектор", "&3Собирательный",
                "&bЛюбитель пособирать барахла. Ради этого готов пойти на крайние меры!\n\nПолучаемые &e&nэффекты&r&b данного класса:\n&e* &aПолучаемый опыт увеличен на 18%\n&e* &aПолучаемые монетки имеют множитель 1.23x\n&e* &cУрон от мобов увеличен на 34%\n&e* &cУрон мобам уменьшен в 1.5x\n\n&8[!] &2С шансом в 2% опыт будет умножен в 4 раза",
                0.5f,
                1.34f);
    }

    @Override
    public double getMobDamageMultiplier() { return getOriginalMobDamageMultiplier(); }
    @Override
    public double getMobReceivedDamageMultiplier() { return getOriginalMobReceivedDamageMultiplier(); }
}
