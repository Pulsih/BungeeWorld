package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.managers.WorldManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (!WorldManager.canPickup(e.getPlayer())) e.setCancelled(true);
    }
}