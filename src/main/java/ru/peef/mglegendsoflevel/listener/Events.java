package ru.peef.mglegendsoflevel.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.network.SocketServer;
import ru.peef.chilove.sounds.SoundManager;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;
import ru.peef.mglegendsoflevel.database.Addon;
import ru.peef.mglegendsoflevel.game.GameCharacter;
import ru.peef.mglegendsoflevel.game.GamePlayer;
import ru.peef.mglegendsoflevel.network.sockets.MessageType;

import java.net.Socket;

public class Events implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = Addon.get(player);
        if (gamePlayer.isBeginner() || gamePlayer.getCharacter() == null) {
            gamePlayer.sendSocketMessage(MessageType.SHOW_SCREEN, "legendsoflevel:character_choose");
        }
        ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);

        gamePlayer.removeAllPotions();

        player.setHealth(player.getHealthScale());
        player.setFoodLevel(20);

        LegendsOfLevelMain.getGame().joinPlayer(gamePlayer);

        LegendsOfLevelMain.updateScoreboard(player);

        Utils.setTabFormat("%legendsoflevel_full_character%");

        chilovePlayer.updateTab();

        // Send damage packet
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);
        Utils.setTabFormat("");

        Addon.push(gamePlayer);
        if (gamePlayer.onWave()) {
            gamePlayer.death();
        }

        for (Socket socket: SocketServer.listeners) {
            if (socket.getInetAddress().equals(player.getAddress().getAddress())) SocketServer.removeListener(socket);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        event.setDroppedExp(0);
        event.getDrops().clear();
        GamePlayer gamePlayer;
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);

            if (gamePlayer.onWave()) gamePlayer.death();
        } else {
            gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(event.getEntity().getKiller());

            if (gamePlayer.onWave()) {
                int coins = (int) (58 + Math.random() * 20);
//                TextPacket.sendPacket(gamePlayer.getPlayer(), "&e+" + coins);
//                gamePlayer.sendTitle("", "&6+" + coins, 2, 1, 2);
                gamePlayer.getGame().killMob();
                gamePlayer.getGame().spawnParticle(gamePlayer, event.getEntity());
                gamePlayer.addProgress(5);
                gamePlayer.addRewardCoins(coins);
                gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getLocation(), SoundManager.getSound("coin"), 1, 1);
            }
        }
        LegendsOfLevelMain.updateScoreboard(gamePlayer.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage("");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(event.getPlayer());
        event.setRespawnLocation(LegendsOfLevelMain.spawnLocation);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause.equals(EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        double damage = event.getDamage(EntityDamageEvent.DamageModifier.ARMOR);

        if (damager instanceof Player player) {
            if (!(entity instanceof Player)) {
                GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);

                if (gamePlayer != null) {
                    double multiplier = 1f;
                    GameCharacter character = gamePlayer.getCharacter();
                    if (character != null) {
                        multiplier = character.getMobDamageMultiplier();
                        event.setDamage(damage * character.getMobDamageMultiplier());
                    }

                    if (Math.round(event.getFinalDamage()) > 0) {
//                        gamePlayer.sendMessage("&aНанесено урона: " + Math.round(event.getFinalDamage()) + " (изначальный урон - " + Math.round(damage) + ", " + multiplier + "x)");
                        gamePlayer.sendSocketMessage("ShowText(cREDcBOLD-" + Math.round(event.getFinalDamage()) + " :heart_symbol:)");
                    }
                }
            } else {
                event.setCancelled(true);
            }
        } else if (entity instanceof Player player) {
            GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);

            if (gamePlayer != null) {
                double multiplier = 1f;
                GameCharacter character = gamePlayer.getCharacter();
                if (character != null) {
                    multiplier = character.getMobReceivedDamageMultiplier();
                    event.setDamage(damage * character.getMobReceivedDamageMultiplier());
                }

//                gamePlayer.sendMessage("&cПолучено урона: " + Math.round(event.getFinalDamage()) + " (изначальный урон - " + Math.round(damage) + ", " + multiplier + "x)");
            }
        }
    }

    @EventHandler
    public void onPlayerChangeSlot(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        int newSlot = event.getNewSlot();
        int prevSlot = event.getPreviousSlot();
        // 0* 1* 2 3 4 5 6 7* 8*
        if (newSlot > 1 && newSlot < 7) {
            if (prevSlot == 1) {
                player.getInventory().setHeldItemSlot(7);
            } else if (prevSlot == 7) {
                player.getInventory().setHeldItemSlot(1);
            } else {
                player.getInventory().setHeldItemSlot(1);
            }
        }
//        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.getString("&eНовая: " + newSlot + " &7| &eСтарая: " + prevSlot, player, player.getWorld())));
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PLAYER)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.getWorld().setStorm(false);
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerFoodChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}
