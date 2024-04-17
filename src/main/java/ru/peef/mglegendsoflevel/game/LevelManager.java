package ru.peef.mglegendsoflevel.game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private static final List<GameLevel> levels = new ArrayList<>();

    public static void loadLevels() {
        ItemStack wooden_sword = new ItemStack(Material.WOODEN_SWORD);
        ItemStack stone_sword = new ItemStack(Material.STONE_SWORD);
        ItemStack iron_sword = new ItemStack(Material.IRON_SWORD);
        ItemStack diamond_sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemStack netherite_sword = new ItemStack(Material.NETHERITE_SWORD);

        ItemStack leather_helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack leather_chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leather_leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack leather_boots = new ItemStack(Material.LEATHER_BOOTS);

        ItemStack iron_helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack iron_chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack iron_leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack iron_boots = new ItemStack(Material.IRON_BOOTS);

        ItemStack diamond_helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemStack diamond_chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack diamond_leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemStack diamond_boots = new ItemStack(Material.DIAMOND_BOOTS);


        int lvl = 0;
        ItemStack[] global_stack;

        // lvl0 items
        global_stack = new ItemStack[]{ wooden_sword, leather_leggings };
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl1 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        global_stack = new ItemStack[]{wooden_sword, leather_chestplate, leather_leggings};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl2 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        global_stack = new ItemStack[]{wooden_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl3 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{wooden_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl4 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{wooden_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl5 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{wooden_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl6 items
        wooden_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        wooden_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        global_stack = new ItemStack[]{wooden_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl7 items
        stone_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        stone_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{stone_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl8 items
        stone_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        stone_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{stone_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl9 items
        stone_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        stone_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        global_stack = new ItemStack[]{stone_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl10 items
        stone_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        stone_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        global_stack = new ItemStack[]{stone_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl11 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        global_stack = new ItemStack[]{iron_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl12 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 7);
        global_stack = new ItemStack[]{iron_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl13 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        leather_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        leather_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        leather_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        leather_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        leather_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        leather_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 8);
        global_stack = new ItemStack[]{iron_sword, leather_helmet, leather_chestplate, leather_leggings, leather_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl14 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl15 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl16 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl17 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl18 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl19 items
        iron_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{iron_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl20 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        iron_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        iron_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
        iron_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        iron_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        iron_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        iron_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        global_stack = new ItemStack[]{diamond_sword, iron_helmet, iron_chestplate, iron_leggings, iron_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl21 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl22 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl23 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl24 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl25 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 7);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl26 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl27 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 9);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack)));

        // lvl28 items
        diamond_sword.removeEnchantment(Enchantment.DAMAGE_ALL);
        diamond_helmet.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_chestplate.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_leggings.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_boots.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
        diamond_sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 10);
        diamond_helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        diamond_boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        global_stack = new ItemStack[]{diamond_sword, diamond_helmet, diamond_chestplate, diamond_leggings, diamond_boots};
        levels.add(new GameLevel(lvl++, List.of(global_stack), true));
    }

    public static GameLevel getLevel(int level) {
        for (GameLevel gameLevel: levels)
            if (gameLevel.getLevel() == level) return gameLevel;
        return null;
    }

    public static List<GameLevel> getLevels() { return levels; }
}
