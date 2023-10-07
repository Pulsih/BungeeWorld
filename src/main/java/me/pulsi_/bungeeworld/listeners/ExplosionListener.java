package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.registry.WorldReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        if (!new WorldReader(e.getEntity().getWorld().getName()).canExplode()) e.setCancelled(true);
    }
}