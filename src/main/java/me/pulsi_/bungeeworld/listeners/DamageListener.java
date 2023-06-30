package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && !new WorldReader(e.getEntity().getWorld().getName()).canFallDamage()) e.setCancelled(true);
    }
}