package ru.peef.mglegendsoflevel.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.peef.chilove.database.Database;
import ru.peef.mglegendsoflevel.game.GamePlayer;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;

public class Addon {
    public static void push(GamePlayer gamePlayer) {
        Player player = gamePlayer.getPlayer();
        if (!Database.isUserExist(player, "legendsoflevel")) {
            Database.addUser(player, "legendsoflevel", "level", String.valueOf(gamePlayer.getLevel()));
        } else {
            Database.updateUser(player, "legendsoflevel", "level", String.valueOf(gamePlayer.getLevel()));
        }
        Database.updateUser(player, "legendsoflevel", "progress", String.valueOf(gamePlayer.getProgress()));
        Database.updateUser(player, "legendsoflevel", "coins", String.valueOf(gamePlayer.getCoins()));

        StringBuilder items = new StringBuilder();
        for (ItemStack item: player.getInventory().getContents()) {
            if (item != null) {
                boolean found = false;
                for (ItemStack levelItem: LevelManager.getLevel(gamePlayer.getLevel()).getItems()) {
                    if (levelItem != null) {
                        if (item.getType() == levelItem.getType()) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    String appendedItem = item.getType().name().toLowerCase() + ";";
                    items.append(appendedItem);

//                    Bukkit.getLogger().info("Item => " + appendedItem);
                }
            }
        }

        if (!items.isEmpty()) { Database.updateUser(player, "legendsoflevel", "items", items.toString()); }
    }

    public static GamePlayer get(Player player) {
        JsonObject user = Database.getUser(player);
        JsonArray array = user.getAsJsonArray("games");

        GamePlayer gamePlayer = new GamePlayer(player);
        for (int i = 0; i < array.size(); i++) {
            JsonObject currentObject = array.get(i).getAsJsonObject();
            if (currentObject.get("name").getAsString().equals("legendsoflevel")) {
                gamePlayer.setLevel(currentObject.get("level").getAsInt(), false);
                gamePlayer.setProgress(currentObject.get("progress").getAsInt());
                gamePlayer.setCoins(currentObject.get("coins").getAsInt());

                return gamePlayer;
            }
        }
        gamePlayer.setBeginner();
        return gamePlayer;
    }
}
