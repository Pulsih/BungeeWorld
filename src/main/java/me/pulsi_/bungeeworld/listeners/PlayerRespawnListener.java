package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWMethods;
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
        Bukkit.getScheduler().runTaskLater(BungeeWorld.getInstance(), () -> ActionProcessor.executeActions(p, WorldManager.getActionsOnRespawn(p)), 5L);

        Location spawn = BWMethods.getLocation(WorldManager.getSpawn(p));
        if (spawn != null) e.setRespawnLocation(spawn);
    }
}