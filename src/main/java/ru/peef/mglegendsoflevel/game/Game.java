package ru.peef.mglegendsoflevel.game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import ru.peef.chilove.ConsoleColor;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.Database;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;

import java.util.*;

public class Game {
    private final List<GamePlayer> players = new ArrayList<>();
    private final List<Location> wavePlayerSpawnLocationList;
    private final World world;
    private World arenaWorld;
    private boolean isWaveCollectActive = false;
    private boolean waveActive = false;

    public Game() {
        world = LegendsOfLevelMain.getInstance().getServer().getWorlds().get(0);
        Location[] tempLocations = {
                new Location(world, 174.0f, 71f, 253f, -180, 0),
                new Location(world, 189.0f, 71f, 238.0f, 90f, 0),
                new Location(world, 174.0f, 71f, 223.0f, 0, 0),
                new Location(world, 159.0f, 71f, 238.0f, -90f, 0),
        };
        wavePlayerSpawnLocationList = List.of(tempLocations);
        updateThread();

        // checker
        new Thread(() -> {
            while (true) {
                if (waveActive) {
                    for (GamePlayer gamePlayer : getWavePlayers()) {
                        String message = "";
                        double maxHP = gamePlayer.getPlayer().getMaxHealth();
                        double HP = gamePlayer.getPlayer().getHealth();

                        int split_count = 15;

                        double percent = ((HP / maxHP) * 100) / 100;
                        percent = percent * split_count;

                        char symbol = '❚';
                        for (int i = 0; i < split_count; i++) {
                            if (i <= percent) {
                                String colorChar = "c";
                                for (PotionEffect effect : gamePlayer.getPlayer().getActivePotionEffects()) {
                                    if (effect.getType().equals(PotionEffectType.POISON)) {
                                        colorChar = "2";
                                        break;
                                    } else if (effect.getType().equals(PotionEffectType.WITHER)) {
                                        colorChar = "0";
                                        break;
                                    } else if (effect.getType().equals(PotionEffectType.ABSORPTION)) {
                                        colorChar = "e";
                                        break;
                                    }
                                }
                                message += "&" + colorChar + symbol;
                            } else message += "&7" + symbol;
                        }

                        ItemStack helmet = gamePlayer.getPlayer().getEquipment().getHelmet();
                        ItemStack chestplate = gamePlayer.getPlayer().getEquipment().getChestplate();
                        ItemStack leggings = gamePlayer.getPlayer().getEquipment().getLeggings();
                        ItemStack boots = gamePlayer.getPlayer().getEquipment().getBoots();

                        int scale = (int) Math.pow(10, 1);
                        String armorPercent = String.valueOf((double) Math.round(Utils.calculateArmorPercent(helmet, chestplate, leggings, boots) * scale) / scale);

                        message += " &a(" + armorPercent + "% \uD83D\uDEE1) &e| &6Монет: " + gamePlayer.getRewardCoins() + " ⛀";

                        gamePlayer.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.format(message)));
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (Exception exc) {
                }
            }
        }).start();
    }

    Thread mobWave;
    private int livedMobCount = 0;
    boolean isTimerStarted = true;
    int secondsToMobWave = 1800;

    public void joinPlayer(GamePlayer player) {
        player.loadItems();
        player.getPlayer().setResourcePack("http://chilove.pw/assets/packs/legendsoflevel.zip");
        player.getPlayer().teleport(LegendsOfLevelMain.spawnLocation);
        addPlayer(player);
    }

    public void sendTitle(String title, String subtitle, int fadeIn, int stay_seconds, int fadeOut) { players.forEach((player) -> player.sendTitle(title, subtitle, fadeIn, stay_seconds * 20, fadeOut)); }
    public void sendActionBar(String message) { players.forEach((player) -> player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.format(message)))); }
    public void sendMessage(String message) { players.forEach((player) -> player.sendMessage(message)); }
    public void playSound(Sound sound, float volume, float pitch) { players.forEach((player) -> player.getPlayer().playSound(player.getPlayer().getLocation(), sound, volume, pitch)); }
    public void addEffect(PotionEffect potionEffect) { players.forEach((player) -> player.getPlayer().addPotionEffect(potionEffect)); }

    // TODO: Сделать логику начала волн
    public void startWave() {
        List<GamePlayer> waveAcceptedList = getWavePlayers();
        if (!waveAcceptedList.isEmpty()) {
            sendMessage(LegendsOfLevelMain.getWavePrefix() + " &r&aНачалась волна! Участников: &e" + waveAcceptedList.size() + " &aигроков");
            for (GamePlayer acceptedPlayer: waveAcceptedList) {
                acceptedPlayer.sendTitle("&a&lВОЛНА НАЧАЛАСЬ", "&bУбей как можно больше мобов!", 2, 4, 2);
                acceptedPlayer.setOnWave(true);
                acceptedPlayer.getPlayer().setHealth(acceptedPlayer.getPlayer().getHealthScale());
                acceptedPlayer.getPlayer().teleport(getRandomWaveLocation());
            }
            waveActive = true;
            isTimerStarted = false;

            loadArena();
        } else {
            updateThread();
            sendMessage(LegendsOfLevelMain.getWavePrefix() + " &r&cВолна была отменена, так как никто не принял приглашение!");
        }
    }

    public void stopWave() {
        int surviveCount = getWavePlayers().size();
        for (GamePlayer gamePlayer: getWavePlayers()) {
            if (!gamePlayer.onWave()) surviveCount--;
        }

        if (surviveCount <= 0 || getLivedMobCount() <= 0) {
            sendMessage(LegendsOfLevelMain.getWavePrefix() + " &r&aВолна была закончена!");
            for (GamePlayer gamePlayer: getWavePlayers()) {
                if (gamePlayer.onWave()) {
                    Player player = gamePlayer.getPlayer();
                    player.teleport(LegendsOfLevelMain.spawnLocation);
                    for (PotionEffect effect: player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                }
            }

            waveActive = false;
            isTimerStarted = true;

            removeAllEntities();
            updateThread();
        }
    }

    public void updateThread() {
        mobWave = new Thread(() -> {
            while (isTimerStarted) {
                try {
                    Thread.sleep(secondsToMobWave * 1000L);
                    sendMessage(LegendsOfLevelMain.getWavePrefix() + " &r&aИдёт набор на волну! Для присоединения нажмите на это сообщение, либо напишите &e/lol wave join &7(для выхода &e/lol wave leave&7)&a. Время набора: &e1 минута");
                } catch (InterruptedException ignored) {}
                Bukkit.getScheduler().scheduleSyncDelayedTask(LegendsOfLevelMain.getInstance(), this::startWave, 20L * 10);
            }
        });

        mobWave.start();
    }

    List<Entity> spawnedEntities = new ArrayList<>();

    public void loadArena() {
        List<GamePlayer> wavePlayers = getWavePlayers();

        int allArmorPoints = 0;
        for (GamePlayer gamePlayer: wavePlayers) {
            allArmorPoints += Utils.calculateArmor(gamePlayer.getPlayer().getEquipment().getHelmet(), gamePlayer.getPlayer().getEquipment().getChestplate(), gamePlayer.getPlayer().getEquipment().getLeggings(), gamePlayer.getPlayer().getEquipment().getBoots());
        }
        float ADP = (float) allArmorPoints / wavePlayers.size();

        for (int i = 0; i < wavePlayers.size(); i++) {
            int mobsCount = 0;
            for (int j = 0; j < 30; j++) {
                Location mobSpawnLocation = new Location(getArenaWorld(), 174.0f, 71f, 238.0);
                if (j <= 14) {
                    Zombie zombie_warrior = (Zombie) getArenaWorld().spawnEntity(mobSpawnLocation, EntityType.ZOMBIE);
                    spawnedEntities.add(zombie_warrior);
                    for (PotionEffect potionEffect: zombie_warrior.getActivePotionEffects()) {
                        zombie_warrior.removePotionEffect(potionEffect.getType());
                    }
                    if (zombie_warrior.getEquipment() != null) {
                        zombie_warrior.getEquipment().clear();
                    }

                    int mob_health = 50;

                    if (ADP < 18 && ADP > 12) {
                        mob_health = 44;
                    } else if (ADP <= 12 && ADP > 8) {
                        mob_health = 40;
                    } else if (ADP <= 8 && ADP > 4) {
                        mob_health = 34;
                    } else if (ADP <= 4) {
                        mob_health = 25;
                    }

                    zombie_warrior.setMaxHealth(mob_health);
                    zombie_warrior.setHealth(mob_health);
                    zombie_warrior.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                    zombie_warrior.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                    zombie_warrior.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    zombie_warrior.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
                    zombie_warrior.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD, 1, (short) (mob_health/2)));
                } else if (j <= 27) {
                    Zombie baby_zombie = (Zombie) getArenaWorld().spawnEntity(mobSpawnLocation, EntityType.ZOMBIE);
                    spawnedEntities.add(baby_zombie);
                    for (PotionEffect potionEffect: baby_zombie.getActivePotionEffects()) { baby_zombie.removePotionEffect(potionEffect.getType()); }

                    if (baby_zombie.getEquipment() != null) {
                        baby_zombie.getEquipment().clear();
                    }

                    int mob_health = 40;

                    if (ADP < 18 && ADP > 12) {
                        mob_health = 37;
                    } else if (ADP <= 12 && ADP > 8) {
                        mob_health = 32;
                    } else if (ADP <= 8 && ADP > 4) {
                        mob_health = 28;
                    } else if (ADP <= 4) {
                        mob_health = 22;
                    }

                    baby_zombie.setBaby();
                    baby_zombie.setMaxHealth(mob_health);
                    baby_zombie.setHealth(mob_health);
                    baby_zombie.getEquipment().setItemInMainHand(new ItemStack(Material.AIR, 1, (short) (mob_health/3)));
                } else {
                    Witch witch = (Witch) getArenaWorld().spawnEntity(mobSpawnLocation, EntityType.WITCH);
                    spawnedEntities.add(witch);
                    for (PotionEffect potionEffect: witch.getActivePotionEffects()) { witch.removePotionEffect(potionEffect.getType()); }
                    witch.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 3));
                    if (witch.getEquipment() != null) {
                        witch.getEquipment().clear();
                    }

                    int mob_health = 75;

                    if (ADP < 18 && ADP > 12) {
                        mob_health = 68;
                    } else if (ADP <= 12 && ADP > 8) {
                        mob_health = 54;
                    } else if (ADP <= 8 && ADP > 4) {
                        mob_health = 45;
                    } else if (ADP <= 4) {
                        mob_health = 42;
                    }

                    witch.setMaxHealth(mob_health);
                    witch.setHealth(mob_health);
                }
                livedMobCount++;
                mobsCount++;
            }
            Bukkit.getLogger().info(ConsoleColor.ANSI_GREEN + "Spawned " + mobsCount + " mobs " + ConsoleColor.ANSI_BLUE + "(" + getArenaWorld().getName() + ")" + ConsoleColor.ANSI_RESET);
        }
    }

    public boolean isWaveStarted() { return this.waveActive; }
    public void addPlayer(GamePlayer player) { players.add(player); }
    public Player getPlayer(Player player) { return getGamePlayer(player).getPlayer(); }
    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer pl: getPlayers()) {
            if (pl.getPlayer().equals(player)) return pl;
        }
        return null;
    }
    public List<GamePlayer> getPlayers() { return players; }

    public boolean isPlayerAcceptedWave(Player player) { return getWavePlayer(player) != null; }
    public boolean isWaveCollectActive() { return isWaveCollectActive; }
    public void setWaveCollectActive(boolean active) { this.isWaveCollectActive = active; }

    public GamePlayer getWavePlayer(Player player) {
        List<GamePlayer> wavePlayers = getWavePlayers();
        for (GamePlayer gamePlayer: wavePlayers) {
            if (gamePlayer.isWaveAccepted() && gamePlayer.getPlayer().equals(player))
                return gamePlayer;
        }

        return null;
    }

    public List<GamePlayer> getWavePlayers() {
        List<GamePlayer> wavePlayers = new ArrayList<>();
        for (GamePlayer gamePlayer: players) {
            if (gamePlayer.isWaveAccepted()) {
                wavePlayers.add(gamePlayer);
            }
        }
        return wavePlayers;
    }

    public int getWaveLocationCount() { return getWaveLocations().size(); }
    public List<Location> getWaveLocations() {
        List<Location> temp = wavePlayerSpawnLocationList;
        for (Location location: temp) {
            location.setWorld(getArenaWorld());
        }
        return temp;
    }
    public Location getRandomWaveLocation() {
        Random rand = new Random();
        return getWaveLocations().get(rand.nextInt(getWaveLocationCount()));
    }

    public Location getMobLocation() { return new Location(arenaWorld, 174.0f, 71f, 238.0); }
    public World getWorld() { return this.world; }
    public void setArenaWorld(World arena_world) { this.arenaWorld = arena_world; }
    public World getArenaWorld() { return this.arenaWorld; }
    public int getLivedMobCount() { return this.livedMobCount; }
    public void killMob() {
        this.livedMobCount--;
        stopWave();
    }
    public void spawnParticle(GamePlayer killer, LivingEntity entity) {
        /*ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);
        // Particle ID
        packet.getMinecraftKeys().write(0, 19);
        // Particle Count
        packet.getIntegers().write(0, 5);

        // Long Distance
        packet.getBooleans().write(0, false);

        // X Y Z
        packet.getDoubles().write(0, entity.getLocation().getX());
        packet.getDoubles().write(1, entity.getLocation().getY());
        packet.getDoubles().write(2, entity.getLocation().getZ());

        // Offset X, Y, Z
        packet.getFloat().write(0, 2f);
        packet.getFloat().write(1, 2f);
        packet.getFloat().write(2, 2f);
        // Speed
        packet.getFloat().write(3, 1f);

        manager.sendServerPacket(killer.getPlayer(), packet);*/

        entity.getWorld().spawnParticle(Particle.HEART, entity.getLocation(), 15);
    }
    public void removeAllEntities() {
        this.livedMobCount = 0;
        for (Entity entity: spawnedEntities) {
            entity.remove();
        }
    }
}
