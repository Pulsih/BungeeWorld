package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final WorldsRegistry registry;

    public PlayerQuitListener(BungeeWorld plugin) {
        this.registry = plugin.getWorldsRegistry();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        
        Player p = e.getPlayer();
        String worldName = p.getWorld().getName();
        Location quitLocation = p.getLocation();

        PlayerUtils playerUtils = new PlayerUtils(p);
        playerUtils.storeStatistics(worldName);
        playerUtils.storeLastLocation(quitLocation, worldName);

        registry.saveAllPlayerStatistics(p);
        registry.unloadPlayerFromWorlds(p);

        ActionProcessor.executeActions(p, registry.getWorlds().get(worldName).getActionsOnQuit());
        playerUtils.quitMessage(worldName);
    }
}