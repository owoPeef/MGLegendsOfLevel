package ru.peef.mglegendsoflevel;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.peef.chilove.ScoreboardManager;
import ru.peef.chilove.Utils;
import ru.peef.chilove.network.SocketServer;
import ru.peef.chilove.sounds.SoundManager;
import ru.peef.chilove.structures.BuildManager;
import ru.peef.mglegendsoflevel.commands.LegendsOfLevelCommand;
import ru.peef.mglegendsoflevel.database.Addon;
import ru.peef.mglegendsoflevel.game.*;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;
import ru.peef.mglegendsoflevel.listener.Events;

import java.util.*;
import java.util.logging.Logger;

public final class LegendsOfLevelMain extends JavaPlugin {
    public static final List<String> messages = Arrays.stream((new String[] {"&7www.chilove.pw", "  ", "&eБаланс: &6%legendsoflevel_coins% ⛀", "&eПрогресс: &a%legendsoflevel_full_progress%", "&eУровень: &b%legendsoflevel_level%", "   ", "&eПерсонаж: %legendsoflevel_character%", " "})).toList();
    private static final String wavePrefix = "§c§l[ВОЛНЫ]";
    private static ScoreboardManager manager;

    public static World world;
    public static final String PACKETS_CHANNEL = "chilove:main";
    public static Game game;
    public static Location spawnLocation;

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, PACKETS_CHANNEL);

        SocketServer.init();
        SoundManager.init();
        ArenaManager.init();
        CharacterManager.init();

//        Utils.enableDebug();
        Utils.setFlyEnabled(false);
        Utils.setDisplayJoinMessage(false);

        game = new Game();
        LevelManager.loadLevels();

        spawnLocation = game.getWorld().getSpawnLocation();

        getServer().getPluginManager().registerEvents(new Events(), this);

        for (Map.Entry<String, Map<String, Object>> cmd : getDescription().getCommands().entrySet()) {
            String command = cmd.getKey();
            try {
                Objects.requireNonNull(getCommand(command)).setExecutor(new LegendsOfLevelCommand());
            } catch (Exception exc) {
                this.getLogger().warning("CommandInit:Error(\"" + command + "\") => " + exc.getMessage());
            }
        }

        if (game.getWorld().getDifficulty().equals(Difficulty.PEACEFUL)) {
            this.getLogger().warning("World difficulty is peaceful! Mob spawning will not work.");
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new LegendsOfLevelPlaceholder().register();
        }

        manager = new ScoreboardManager("legendsoflevel", "&aLegends Of Level");
        manager.displayScoreboard();

        world = getServer().getWorlds().get(0);

        new Thread(() -> {
            while (true) {
                int d = 4;
                // 30 0 124
//                world.spawnParticle(Particle.CLOUD, new Location(world, 111, -48, 11), 30, 81, 48, 133, 1f);

                for (int x = 34; x < 89; x += d) {
                    for (int y = -30; y > -45; y -= d) {
                        for (int z = 33; z < 103; z += d) {
                            // TODO
                            // 124 52 -28; 13 -48 161
                            world.spawnParticle(Particle.WHITE_ASH, new Location(world, x, y, z), 15, 15, 15, 15, 0.05f);
                        }
                    }
                }
                try {
                    Thread.sleep(700);
                } catch (Exception ignored) {}
            }
        }).start();

//        getLog().info(BuildManager.getBuildByName("arena1").toString());
    }

    public static JavaPlugin getInstance() { return getPlugin(LegendsOfLevelMain.class); }
    public static Logger getLog() { return getInstance().getLogger(); }
    public static Game getGame() { return game; }
    public static World getMainWorld() { return world; }
    public static String getWavePrefix() { return wavePrefix; }
    public static ScoreboardManager getScoreboardManager() { return manager; }
    public static void updateScoreboard(Player player) { getScoreboardManager().displayPlayer(player, LegendsOfLevelMain.messages); }

    @Override
    public void onDisable() {
        // Push all data to database
        for (GamePlayer player: game.getPlayers()) { Addon.push(player); }
        // Remove all entities (from arena)
        for (Arena arena: ArenaManager.getArenas()) {
            arena.despawn();
        }

        if (SocketServer.isRunning()) {
            SocketServer.disableSocketServer();
        }

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, PACKETS_CHANNEL);
    }
}
