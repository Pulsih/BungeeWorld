package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.WorldReader;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !new WorldReader(p.getWorld().getName()).canUse(p)) e.setCancelled(true);
    }
}