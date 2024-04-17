package ru.peef.mglegendsoflevel.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.peef.chilove.database.Database;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;
import ru.peef.mglegendsoflevel.game.GamePlayer;
import ru.peef.mglegendsoflevel.game.LevelManager;

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

                    Bukkit.getLogger().info("Item => " + appendedItem);
                }
            }
        }

        if (!items.isEmpty()) { Database.updateUser(player, "legendsoflevel", "items", items.toString()); }

        Location location;
        if (gamePlayer.getPlayer().getWorld().equals(LegendsOfLevelMain.getGame().getWorld())) {
            location = gamePlayer.getPlayer().getLocation();
        } else {
            location = gamePlayer.getLastLocation();
        }
        String last_loc = Math.round(location.getX()) + "," + Math.round(location.getY()) + "," + Math.round(location.getZ()) + "," + Math.round(location.getYaw()) + "," + Math.round(location.getPitch());
        Database.updateUser(player, "legendsoflevel", "last_location", last_loc);
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

                String[] loc_split = currentObject.get("last_location").getAsString().split(",");
                int x = Integer.parseInt(loc_split[0]);
                int y = Integer.parseInt(loc_split[1]);
                int z = Integer.parseInt(loc_split[2]);
                int yaw = Integer.parseInt(loc_split[3]);
                int pitch = Integer.parseInt(loc_split[4]);
                Location last_location = new Location(LegendsOfLevelMain.getGame().getWorld(), x, y, z, yaw, pitch);

                gamePlayer.setLastLocation(last_location);
                return gamePlayer;
            }
        }
        gamePlayer.setBeginner();
        return gamePlayer;
    }
}
