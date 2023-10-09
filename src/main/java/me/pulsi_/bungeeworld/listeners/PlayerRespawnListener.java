package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        BWWorld world = new WorldReader(p.getWorld().getName()).getWorld();
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> {
            ActionProcessor.executeActions(p, world.getActionsOnRespawn());
            new PlayerUtils(p).storeStatistics(world.getName());
        }, 1L);

        Location spawn = BWUtils.getLocation(world.getSpawn());
        if (spawn != null) e.setRespawnLocation(spawn);
    }
}