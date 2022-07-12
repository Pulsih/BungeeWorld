package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.managers.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (!WorldManager.canDrop(e.getPlayer())) e.setCancelled(true);
    }
}