package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (!new WorldReader(p.getWorld().getName()).canDrop(p)) e.setCancelled(true);
    }
}