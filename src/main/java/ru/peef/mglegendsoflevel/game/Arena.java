package ru.peef.mglegendsoflevel.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.peef.chilove.ConsoleColor;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;
import ru.peef.mglegendsoflevel.Utils;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private final Location playerSpawnLocation;
    private final List<Location> mobSpawnLocation = new ArrayList<>();
    private final List<GamePlayer> gamePlayers = new ArrayList<>();
    private final List<Entity> spawnedEntities = new ArrayList<>();
    private long time = 15000;
    private boolean waveStarted = false;

    public Arena(String playerSpawnLocation, String[] mobSpawnLocation) {
        World world = Bukkit.getServer().getWorlds().get(0);
        String[] playerSpawnSplit = playerSpawnLocation.split(",");
        double pX = Double.parseDouble(playerSpawnSplit[0]);
        double pY = Double.parseDouble(playerSpawnSplit[1]);
        double pZ = Double.parseDouble(playerSpawnSplit[2]);
        this.playerSpawnLocation = new Location(world, pX, pY, pZ);
        for (String mobSpawn: mobSpawnLocation) {
            String[] cords = mobSpawn.split(",");
            double sX = Double.parseDouble(cords[0]);
            double sY = Double.parseDouble(cords[1]);
            double sZ = Double.parseDouble(cords[2]);
            this.mobSpawnLocation.add(new Location(world, sX, sY, sZ));
        }
    }

    public Arena(long time, String playerSpawnLocation, String[] mobSpawnLocation) {
        this.time = time;
        World world = Bukkit.getServer().getWorlds().get(0);
        String[] playerSpawnSplit = playerSpawnLocation.split(",");
        double pX = Double.parseDouble(playerSpawnSplit[0]);
        double pY = Double.parseDouble(playerSpawnSplit[1]);
        double pZ = Double.parseDouble(playerSpawnSplit[2]);
        this.playerSpawnLocation = new Location(world, pX, pY, pZ);
        for (String mobSpawn: mobSpawnLocation) {
            String[] cords = mobSpawn.split(",");
            double sX = Double.parseDouble(cords[0]);
            double sY = Double.parseDouble(cords[1]);
            double sZ = Double.parseDouble(cords[2]);
            this.mobSpawnLocation.add(new Location(world, sX, sY, sZ));
        }
    }

    public void startWave() {
        Bukkit.getLogger().info("Arena time is " + time);
        for (GamePlayer gamePlayer: gamePlayers) {
            gamePlayer.sendTitle("&a&lВОЛНА НАЧАЛАСЬ", "&bУбей как можно больше мобов!", 2, 4, 2);
            gamePlayer.setOnWave(true);
            gamePlayer.getPlayer().setHealth(gamePlayer.getPlayer().getHealthScale());
            gamePlayer.getPlayer().teleport(playerSpawnLocation);
            gamePlayer.getPlayer().setPlayerTime(time, false);

            waveStarted = true;

            spawn();
        }
    }

    public void stopWave() {
        if (gamePlayers.isEmpty() || livedMobsCount <= 0) {
            for (GamePlayer gamePlayer: gamePlayers) {
                gamePlayer.sendMessage(LegendsOfLevelMain.getWavePrefix() + " &r&aВолна была закончена!");
                Player player = gamePlayer.getPlayer();
                player.teleport(LegendsOfLevelMain.spawnLocation);
                for (PotionEffect effect: player.getActivePotionEffects()) {
                    player.removePotionEffect(effect.getType());
                }
            }

            despawn();
            waveStarted = false;
        }
    }

    int livedMobsCount = 0;

    public void spawn() {
        double avgLevel = 0;
        double avgXP = 0;
        double avgMaxXP = 0;
        for (GamePlayer gamePlayer: gamePlayers) {
            avgLevel += gamePlayer.getLevel();
            avgXP += gamePlayer.getProgress();
            avgMaxXP += LevelManager.getLevel(gamePlayer.getLevel()).getXpNeed();
        }

        avgLevel /= gamePlayers.size();
        avgXP /= gamePlayers.size();
        avgMaxXP /= gamePlayers.size();

        for (int i = 0; i < gamePlayers.size(); i++) {
            int mobsCount = 0;
            for (int j = 0; j < 30; j++) {
                double final_hp;
                if (j <= 14) {
                    Zombie zombie_warrior = (Zombie) LegendsOfLevelMain.getMainWorld().spawnEntity(mobSpawnLocation.get(0), EntityType.ZOMBIE);
                    spawnedEntities.add(zombie_warrior);
                    for (PotionEffect potionEffect: zombie_warrior.getActivePotionEffects()) {
                        zombie_warrior.removePotionEffect(potionEffect.getType());
                    }
                    if (zombie_warrior.getEquipment() != null) {
                        zombie_warrior.getEquipment().clear();
                    }

                    int base_hp = 50;
                    final_hp = Utils.calculateHealth(base_hp, avgLevel, avgXP, avgMaxXP);

                    zombie_warrior.setMaxHealth(final_hp);
                    zombie_warrior.setHealth(final_hp);
                    zombie_warrior.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                    zombie_warrior.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                    zombie_warrior.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    zombie_warrior.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    zombie_warrior.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1, (short) (final_hp / 2)));
                } else if (j <= 27) {
                    Zombie baby_zombie = (Zombie) LegendsOfLevelMain.getMainWorld().spawnEntity(mobSpawnLocation.get(1), EntityType.ZOMBIE);
                    spawnedEntities.add(baby_zombie);
                    for (PotionEffect potionEffect: baby_zombie.getActivePotionEffects()) { baby_zombie.removePotionEffect(potionEffect.getType()); }

                    if (baby_zombie.getEquipment() != null) {
                        baby_zombie.getEquipment().clear();
                    }

                    int base_hp = 40;

                    final_hp = Utils.calculateHealth(base_hp, avgLevel, avgXP, avgMaxXP);

                    baby_zombie.setBaby();
                    baby_zombie.setMaxHealth(final_hp);
                    baby_zombie.setHealth(final_hp);
                    baby_zombie.getEquipment().setItemInMainHand(new ItemStack(Material.AIR, 1, (short) (final_hp / 3)));
                } else {
                    Witch witch = (Witch) LegendsOfLevelMain.getMainWorld().spawnEntity(mobSpawnLocation.get(2), EntityType.WITCH);
                    spawnedEntities.add(witch);
                    for (PotionEffect potionEffect: witch.getActivePotionEffects()) { witch.removePotionEffect(potionEffect.getType()); }
                    witch.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 3));
                    if (witch.getEquipment() != null) {
                        witch.getEquipment().clear();
                    }

                    int base_hp = 75;

                    final_hp = Utils.calculateHealth(base_hp, avgLevel, avgXP, avgMaxXP);

                    witch.setMaxHealth(final_hp);
                    witch.setHealth(final_hp);
                }
                Bukkit.getLogger().info("Final mob HP => " + Math.round(final_hp));
                livedMobsCount++;
                mobsCount++;
            }
            Bukkit.getLogger().info(ConsoleColor.ANSI_GREEN + "Spawned " + mobsCount + " mobs " + ConsoleColor.ANSI_BLUE + "(" + LegendsOfLevelMain.getMainWorld().getName() + ")" + ConsoleColor.ANSI_RESET);
        }
    }

    public void despawn() {
        for (Entity entity: spawnedEntities) {
            entity.remove();
        }
        spawnedEntities.clear();
    }
    public boolean isFree() { return (gamePlayers.isEmpty() && !waveStarted); }
    public void addPlayer(GamePlayer player) { gamePlayers.add(player); }
    public void removePlayers() { gamePlayers.clear(); }
    public void removePlayer(GamePlayer gamePlayer) { gamePlayers.removeIf(player -> player.equals(gamePlayer)); }
    public long getTime() { return time; }
    public void killMob() { livedMobsCount--; }
}
