package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.registry.WorldReader;
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
            PlayerUtils playerUtils = new PlayerUtils(p);
            playerUtils.storeStatistics(fromWorldName);
            playerUtils.loadStatistics(newWorldName);

            if (Values.CONFIG.isIsolateChat()) {
                quitMessage(p, fromWorld);
                joinMessage(p, newWorld);
            }
        }

        ActionProcessor.executeActions(p, prevReader.getWorld().getActionsOnQuit());

        WorldReader reader = new WorldReader(newWorldName);
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, reader.getWorld().getActionsOnJoin()), 5L);
    }

    @EventHandler
    public void onWorldChange(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();

        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> {
            World fromW = from.getWorld();
            if (fromW == null) return;

            String fromN = fromW.getName();
            if (!fromN.equals(p.getWorld().getName())) new PlayerUtils(p).storeLastLocation(from, fromN);
        }, 1L);
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