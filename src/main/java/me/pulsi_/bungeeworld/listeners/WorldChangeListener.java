package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import me.pulsi_.bungeeworld.worldSeparator.managers.LastLocationManager;
import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class WorldChangeListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        World fromWorld = e.getFrom(), newWorld = p.getWorld();
        String fromWorldName = fromWorld.getName(), newWorldName = newWorld.getName();

        WorldReader prevReader = new WorldReader(fromWorldName);
        boolean isLinked = prevReader.isLinkedWorld(newWorldName);

        if (Values.CONFIG.isClearChat()) BWUtils.clearChat(p);

        if (!isLinked) {
            Storage.switchStatistics(p, fromWorldName);

            if (Values.CONFIG.isIsolateChat()) {
                quitMessage(p, fromWorld);
                joinMessage(p, newWorld);
            }
        }

        ActionProcessor.executeActions(p, prevReader.getActionsOnQuit());

        WorldReader reader = new WorldReader(newWorldName);
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, reader.getActionsOnJoin()), 4L);
    }

    @EventHandler
    public void onWorldChange(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();

        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> {
            if (from.getWorld().getName().equals(p.getWorld().getName())) return;

            LastLocationManager lastLocationManager = new LastLocationManager(p);
            lastLocationManager.saveLastLocationToFile(from);
            lastLocationManager.loadLastLocationsToHashMap();
        }, 0l);
    }

    private void joinMessage(Player p, World world) {
        WorldReader reader = new WorldReader(world.getName());
        String joinMessage = reader.getJoinMessage();
        if (joinMessage == "") return;

        String message = BWChat.color(joinMessage.replace("%player%", p.getName()));
        for (Player player : world.getPlayers()) {
            if (!player.equals(p)) player.sendMessage(message);
        }

        List<String> linkedWorldsNames = reader.getLinkedWorlds();
        if (linkedWorldsNames.isEmpty()) return;

        for (String linkedWorldName : linkedWorldsNames) {
            if (linkedWorldName.equals(world.getName())) continue;

            World linkedWorld = Bukkit.getWorld(linkedWorldName);
            if (linkedWorld == null) continue;

            for (Player players : linkedWorld.getPlayers())
                players.sendMessage(joinMessage);
        }
    }

    private void quitMessage(Player p, World world) {
        WorldReader reader = new WorldReader(world.getName());
        String quitMessage = reader.getQuitMessage();
        if (quitMessage == "") return;

        String message = BWChat.color(quitMessage.replace("%player%", p.getName()));
        for (Player player : world.getPlayers()) {
            if (!player.equals(p)) player.sendMessage(message);
        }

        List<String> linkedWorldsNames = reader.getLinkedWorlds();
        if (linkedWorldsNames.isEmpty()) return;

        for (String linkedWorldName : linkedWorldsNames) {
            if (linkedWorldName.equals(world.getName())) continue;

            World linkedWorld = Bukkit.getWorld(linkedWorldName);
            if (linkedWorld == null) continue;

            for (Player players : linkedWorld.getPlayers())
                players.sendMessage(quitMessage);
        }
    }
}