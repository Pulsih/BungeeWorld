package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (damager instanceof Player && e.getEntity() instanceof Player && e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) &&
                !new WorldReader(damager.getWorld().getName()).canPvP(damager)) e.setCancelled(true);
    }
}