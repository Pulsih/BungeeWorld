package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.registry.WorldReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (!new WorldReader(e.getPlayer().getWorld().getName()).canPickup()) e.setCancelled(true);
    }
}