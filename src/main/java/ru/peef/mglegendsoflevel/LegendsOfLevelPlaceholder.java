package ru.peef.mglegendsoflevel;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.mglegendsoflevel.game.levels.GameLevel;
import ru.peef.mglegendsoflevel.game.GamePlayer;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;

public class LegendsOfLevelPlaceholder extends PlaceholderExpansion {
    public LegendsOfLevelPlaceholder() {}

    @Override
    public @NotNull String getAuthor() { return "owoPeef"; }

    @Override
    public @NotNull String getIdentifier() { return "legendsoflevel"; }

    @Override
    public @NotNull String getVersion() { return "1.0.0"; }

    public String onPlaceholderRequest(Player player, @NotNull String params) {
        GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);
        ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
        if (gamePlayer != null && chilovePlayer != null) {
            if (params.equalsIgnoreCase("coins")) {
                return String.valueOf(gamePlayer.getCoins());
            }
            if (params.equalsIgnoreCase("level")) {
                return String.valueOf(gamePlayer.getLevel());
            }
            if (params.equalsIgnoreCase("progress")) {
                return String.valueOf(gamePlayer.getProgress());
            }
            if (params.equalsIgnoreCase("character")) {
                if (gamePlayer.getCharacter() != null) {
                    return gamePlayer.getCharacter().getTitle();
                }
                return "&cне выбран";
            }
            if (params.equalsIgnoreCase("full_character")) {
                if (gamePlayer.getCharacter() != null) {
                    return Utils.format(gamePlayer.getCharacter().getTitle() + " &e| " + chilovePlayer.getGroup().getDisplayColor() + player.getName());
                }
                return chilovePlayer.getGroup().getDisplayColor() + player.getName();
            }
            if (params.equalsIgnoreCase("full_progress")) {
                GameLevel level = LevelManager.getLevel(gamePlayer.getLevel()+1);
                if (level != null) {
                    double onePercent = (double) level.getXpNeed() / 100;
                    return Math.round((gamePlayer.getProgress() / onePercent)) + "% &r≈";
                } else {
                    return "Макс. уровень";
                }
            }
        }

        return null;
    }
}
