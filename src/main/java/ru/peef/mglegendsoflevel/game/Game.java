package ru.peef.mglegendsoflevel.game;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import ru.peef.chilove.Utils;
import ru.peef.mglegendsoflevel.LegendsOfLevelMain;

import java.util.*;

public class Game {
    private final List<GamePlayer> players = new ArrayList<>();
    private final World world;

    public Game() {
        world = LegendsOfLevelMain.getInstance().getServer().getWorlds().get(0);
    }

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
    public void addPlayer(GamePlayer player) { players.add(player); }
    public Player getPlayer(Player player) { return getGamePlayer(player).getPlayer(); }
    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer pl: getPlayers()) {
            if (pl.getPlayer().equals(player)) return pl;
        }
        return null;
    }
    public List<GamePlayer> getPlayers() { return players; }
    public World getWorld() { return this.world; }
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

        entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 2);
    }
}
