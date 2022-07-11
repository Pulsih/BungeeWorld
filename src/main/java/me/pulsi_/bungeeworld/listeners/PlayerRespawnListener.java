package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        ActionProcessor.executeActions(p, WorldManager.getActionsOnRespawn(p));

        String spawn = WorldManager.getSpawn(p);
        if (spawn != null) e.setRespawnLocation(BWMethods.getLocation(spawn));
    }
}