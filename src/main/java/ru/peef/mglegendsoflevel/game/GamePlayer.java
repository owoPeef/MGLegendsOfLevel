package ru.peef.mglegendsoflevel.game;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.network.SocketServer;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;
import ru.peef.mglegendsoflevel.game.levels.GameLevel;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;
import ru.peef.mglegendsoflevel.network.sockets.MessageType;

import java.util.List;

public class GamePlayer {
    private int level = 0;
    private int progress = 0;
    private int coins = 0;
    private int rewardCoins = 0;
    private Player player;
    private boolean onWave = false;
    private GameCharacter character = null;
    private Arena arena = null;
    private boolean isBeginner = false;

    public GamePlayer(Player player) { this.player = player; }
    public GamePlayer(Player player, boolean isBeginner) {
        this.isBeginner = isBeginner;
        this.player = player;
    }

    public GamePlayer(Player player, int level) {
        this.level = level;
        this.player = player;
    }

    public GamePlayer(Player player, int level, int progress) {
        this.level = level;
        this.progress = progress;
        this.player = player;
    }

    public void loadItems() {
        this.getPlayer().getInventory().clear();
        for (ItemStack item: LevelManager.getLevel(level).getItems()) {
            String name = item.getType().name().toLowerCase();
            LegendsOfLevelMain.getLog().info(item.getType().name());
            if (name.contains("helmet")) {
                this.getPlayer().getInventory().setHelmet(item);
            } else if (name.contains("chestplate")) {
                this.getPlayer().getInventory().setChestplate(item);
            } else if (name.contains("leggings")) {
                this.getPlayer().getInventory().setLeggings(item);
            } else if (name.contains("boots")) {
                this.getPlayer().getInventory().setBoots(item);
            } else {
                this.getPlayer().getInventory().addItem(item);
            }
        }
    }

    public void startWave() {
        arena = ArenaManager.getFreeArena();
        if (arena != null) {
            arena.addPlayer(this);
            arena.startWave();
        }
        else {
            sendMessage("&cНет свободных арен!");
        }
    }

    public void death() {
        sendTitle("&c&lТы умер!", "", 3, 6, 3);
        getPlayer().teleport(LegendsOfLevelMain.spawnLocation);
        addCoins(getRewardCoins() / 2);
        setRewardCoins(0);
        setOnWave(false);
        arena.removePlayer(this);
        player.setPlayerTime(LegendsOfLevelMain.getMainWorld().getTime(), false);
        arena.stopWave();
    }

    public void sendSocketMessage(MessageType type, String message) {
        SocketServer.sendMessage(this.getPlayer(), Utils.format(type.getPrefix() + "(" + message + ")"));
    }
    public void sendSocketMessage(String message) { SocketServer.sendMessage(this.getPlayer(), Utils.format(message)); }
    public boolean isBeginner() { return this.isBeginner; }
    public void setBeginner() { this.isBeginner = true; }
    public Game getGame() { return LegendsOfLevelMain.getGame(); }
    public void sendTitle(String title, String subtitle, int fadeIn, int staySeconds, int fadeOut) { player.sendTitle(Utils.format(title), Utils.format(subtitle), fadeIn, staySeconds * 20, fadeOut); }
    public void sendMessage(String message) { player.sendMessage(Utils.format(message)); }
    public World getWorld() { return this.player.getWorld(); }
    public boolean onWave() { return this.onWave; }
    public void setOnWave(boolean isAccepted) { this.onWave = isAccepted; }
    public int getLevel() { return this.level; }
    public void setLevel(int level, boolean notify) {
        GameLevel gameLevel = LevelManager.getLevel(level);
        if (notify) {
            if (gameLevel != null) {
                sendTitle("&b&lИзменение уровня", "&a" + this.level + "ур. &b-> &6" + level + "ур.", 2, 4, 2);
            }
        }
        this.level = level;
        loadItems();
    }
    public void addLevel() {
        GameLevel gLevel = LevelManager.getLevel(this.level+1);
        if (gLevel != null) {
            if (this.progress == gLevel.getXpNeed()) {
                ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
                this.progress = 0;
                this.level++;

                boolean isMax = this.level + 1 == LevelManager.getLevels().size();

                if (!isMax) {
                    sendTitle("&eНовый уровень!", "&a" + getLevel(), 2, 4, 2);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 1);
                } else {
                    sendTitle("&e&lНовый уровень!", "&aВы достигли максимального уровня", 2, 4, 2);
                    LegendsOfLevelMain.getGame().sendMessage("&aИгрок " + chilovePlayer.getGroup().getDisplayColor() + player.getName() + "&a достиг &6&lмаксимального &r&aуровня!");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1, 1);
                }

                loadItems();
            }
        }
    }
    public void addLevel(boolean f) {
        GameLevel gLevel = LevelManager.getLevel(this.level+1);
        if (gLevel != null) {
            if (this.progress == gLevel.getXpNeed() || f) {
                ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
                this.progress = 0;
                this.level++;

                boolean isMax = this.level + 1 == LevelManager.getLevels().size();

                if (!isMax) {
                    sendTitle("&eНовый уровень!", "&a" + getLevel(), 2, 8, 2);
                    player.getPlayer().playSound(player.getLocation(), "chilovemod:level_up_sound", 1, 1);
                } else {
                    sendTitle("&e&lНовый уровень!", "&aВы достигли максимального уровня", 2, 8, 2);
                    LegendsOfLevelMain.getGame().sendMessage("&aИгрок " + chilovePlayer.getGroup().getDisplayColor() + player.getName() + "&a достиг &6&lмаксимального &r&aуровня!");
                }
                player.getPlayer().playSound(player.getLocation(), "chilovemod:level_up_sound", 1, 1);

                loadItems();
            }
        }
    }
    public int getProgress() { return this.progress; }
    public void addProgress(int progress) {
        if (progress > 0) {
            sendSocketMessage("ShowText(cGREEN+" + progress + " :progress_symbol:)");
            sendMessage("&eОпыт: &a+" + progress + " ≈");
            this.progress += progress;
            addLevel();
        }
    }
    public void setProgress(int progress) { this.progress = progress; }
    public int getCoins() { return this.coins; }
    public void addCoins(int coins) {
        if (coins > 0) {
            sendMessage("&eБаланс: &a+" + coins + " &f⛀");
            this.coins += coins;
        }
    }
    public void setCoins(int coins) { this.coins = coins; }
    public int getRewardCoins() { return this.rewardCoins; }
    public void setRewardCoins(int coins) { this.rewardCoins = coins; }
    public void addRewardCoins(int coins) { this.rewardCoins += coins; }
    public Player getPlayer() { return this.player; }
    public void setPlayer(Player player) { this.player = player; }
    public void removeAllPotions() {
        for (PotionEffect effect: player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }

    public GameCharacter getCharacter() { return character; }
    public void setCharacter(GameCharacter character) {
        this.character = character;
        applyCharacterAbilities();
        LegendsOfLevelMain.updateScoreboard(this.getPlayer());
    }
    public void applyCharacterAbilities() {
        if (this.character != null) {
            List<PotionEffect> potionEffects = character.getEffects();
            if (!potionEffects.isEmpty()) {
                for (PotionEffect effect: potionEffects) {
                    player.addPotionEffect(effect);
                }
            }
        }
    }
    public Arena getArena() { return arena; }
}
