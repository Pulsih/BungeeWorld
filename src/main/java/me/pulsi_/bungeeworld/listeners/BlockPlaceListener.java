package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!new WorldReader(p.getWorld().getName()).canPlace(p)) e.setCancelled(true);
    }
}
