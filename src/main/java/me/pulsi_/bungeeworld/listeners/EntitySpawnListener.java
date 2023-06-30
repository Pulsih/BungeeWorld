package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        String worldName = e.getLocation().getWorld().getName();
        if (!new WorldReader(worldName).canSpawnMob()) e.setCancelled(true);
    }
}