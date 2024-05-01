package ru.peef.mglegendsoflevel.commands;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.peef.chilove.Utils;
import ru.peef.chilove.database.ChilovePlayer;
import ru.peef.chilove.database.ChilovePlayerManager;
import ru.peef.chilove.network.SocketServer;
import ru.peef.chilove.sounds.SoundManager;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;
import ru.peef.mglegendsoflevel.game.*;
import ru.peef.mglegendsoflevel.game.levels.GameLevel;
import ru.peef.mglegendsoflevel.game.levels.LevelManager;
import ru.peef.mglegendsoflevel.network.packets.TextPacket;

import java.util.UUID;

public class LegendsOfLevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {
            UUID uuid = player.getUniqueId();
            ChilovePlayer chilovePlayer = ChilovePlayerManager.getChilovePlayer(player);
            GamePlayer gamePlayer = LegendsOfLevelMain.getGame().getGamePlayer(player);

            // test sound coin
            if (args.length == 3) {
                if (args[0].equals("test")) {
                    if (args[1].equals("sound")) {
                        String sound = SoundManager.getSound(args[2]);
                        if (sound != null) {
                            gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getLocation(), sound, 1, 1);
                            gamePlayer.sendMessage("Звук был проигран.");
                        } else {
                            gamePlayer.sendMessage("&cЗвук не найден!");
                        }
                    }
                }
            }

            if (args.length == 2) {
                if (args[0].equals("test")) {
                    if (args[1].equals("socket")) {
                        if (SocketServer.sendMessage(gamePlayer.getPlayer(), "TEST MESSAGE")) {
                            gamePlayer.sendMessage("&aСообщение отправлено!");
                        } else {
                            gamePlayer.sendMessage("&cСообщение не было отправлено.");
                        }
                    }
                    if (args[1].equals("packet")) {
                        if (TextPacket.sendPacket(player, "+" + Math.round(10000 + Math.random() * 10000000))) {
                            gamePlayer.sendMessage("&aПакет был отправлен!");
                        } else {
                            gamePlayer.sendMessage("&cОшибка была выведена в консоль.");
                        }
                    }
                }
            }

            if (args.length == 3) {
                if (args[0].equals("set")) {
                    if (args[1].equals("level") || args[1].equals("lvl")) {
                        gamePlayer.setLevel(Integer.parseInt(args[2]), true);
                    }
                }
            }

            if (args.length == 2) {
                if (args[0].equals("add")) {
                    if (args[1].equals("level") || args[1].equals("lvl")) {
                        gamePlayer.addLevel(true);
                    }
                }

                if (args[0].equals("character")) {
                    if (gamePlayer.getCharacter() != null) {
                        gamePlayer.sendMessage("&cУ вас уже выбран персонаж!");
                    } else {
                        GameCharacter character = CharacterManager.getCharacter(args[1]);
                        if (character == null) {
                            gamePlayer.sendMessage("&cПерсонаж не найден!");
                        } else {
                            gamePlayer.setCharacter(character);
                            TextComponent message = new TextComponent(Utils.format("&aВыбран персонаж: " + character.getTitle()));
                            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(new TextComponent(Utils.format(character.getTitle() + "\n&aТип: " + character.getType() + "\n" + character.getDescription()))).create())));
                            gamePlayer.getPlayer().spigot().sendMessage(message);
                            chilovePlayer.updateTab();
                        }
                    }
                }
            }

            if (args.length == 1) {
                if (args[0].equals("play")) {
                    gamePlayer.startWave();
                }

                if (args[0].equals("info")) {
                    if (gamePlayer != null) {
                        gamePlayer.sendMessage("&bИгрок " + chilovePlayer.getGroup().getDisplayColor() + player.getName() + " &bимеет " + gamePlayer.getLevel() + "ур.");
                        if (LevelManager.getLevel(gamePlayer.getLevel()).isMaxLevel()) {
                            gamePlayer.sendMessage("&bОпыт: &aВы достигли максимально доступного уровня!");
                        } else {
                            gamePlayer.sendMessage("&bОпыт: &a" + Math.round(gamePlayer.getProgress()) + "&b/&a" + Math.round(LevelManager.getLevel(gamePlayer.getLevel()+1).getXpNeed()) + " &f≈");
                        }
                        gamePlayer.sendMessage("&bБаланс: &6" + Math.round(gamePlayer.getCoins()) + " ⛀");
                    }
                }

                if (args[0].equals("hp")) {
                    gamePlayer.sendMessage("&c" + Math.round(gamePlayer.getPlayer().getHealth()) + " ♥");
                }

                if (args[0].equals("lvls") || args[0].equals("levels")) {
                    for (GameLevel level: LevelManager.getLevels()) {
                        gamePlayer.sendMessage("&bУровень " + level.getLevel() + " &a(необходимо опыта: &e" + Math.round(level.getXpNeed()) + "&b)");
                    }
                }
            }
        }
        return true;
    }
}
