package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Storage.updateCurrentStatistic(p);

        WorldReader reader = new WorldReader(p.getWorld().getName());
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, reader.getActionsOnRespawn()), 1L);

        Location spawn = BWUtils.getLocation(reader.getSpawn());
        if (spawn != null) e.setRespawnLocation(spawn);
    }
}