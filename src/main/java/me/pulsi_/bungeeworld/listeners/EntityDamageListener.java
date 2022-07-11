package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.managers.WorldManager;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        Entity entity = e.getDamager();
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) && !WorldManager.canPvP(entity)) e.setCancelled(true);
    }
}