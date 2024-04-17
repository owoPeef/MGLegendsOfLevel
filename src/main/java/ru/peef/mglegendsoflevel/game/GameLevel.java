package ru.peef.mglegendsoflevel.game;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {
    int level;
    List<ItemStack> items;
    int xpNeed;
    boolean maxLevel = false;

    float reduceFactor = 2.5f;

    public GameLevel(int level, List<ItemStack> items) {
        this.level = level;
        this.items = items;

        if (level == 0) {
            xpNeed = 0;
        } else {
            xpNeed = Math.round((1 + ((level * 3.4f) * ((level * 2 + 23) / 7.6f) * reduceFactor)) * 35f);
        }
    }

    public GameLevel(int level, List<ItemStack> items, boolean isMax) {
        this.level = level;
        this.items = items;
        this.maxLevel = isMax;

        if (level == 0) {
            xpNeed = 0;
        } else {
            xpNeed = Math.round((1 + ((level * 3.4f) * ((level * 2 + 23) / 7.6f) * reduceFactor)) * 35f);
        }
    }

    public int getLevel() { return level; }
    public int getXpNeed() { return xpNeed; }
    public List<ItemStack> getItems() {
        List<ItemStack> unbreakableItems = new ArrayList<>();
        for (ItemStack item: items) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                itemMeta.setUnbreakable(true);
                item.setItemMeta(itemMeta);
                unbreakableItems.add(item);
            }
        }
        return unbreakableItems;
    }
    public boolean isMaxLevel() { return this.maxLevel; }
}
