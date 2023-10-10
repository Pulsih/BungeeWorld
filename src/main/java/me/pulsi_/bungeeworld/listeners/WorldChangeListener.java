package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;


public class WorldChangeListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        String fromWorld = e.getFrom().getName(), newWorld = p.getWorld().getName();
        WorldReader prevReader = new WorldReader(fromWorld);
        boolean isLinked = prevReader.isLinkedWorld(newWorld);

        if (Values.CONFIG.isClearChat()) BWUtils.clearChat(p);

        if (!isLinked) {
            PlayerUtils playerUtils = new PlayerUtils(p);
            playerUtils.storeStatistics(fromWorld);
            playerUtils.applyStatistics(newWorld);

            if (Values.CONFIG.isIsolateChat()) {
                playerUtils.quitMessage(fromWorld);
                playerUtils.joinMessage(newWorld);
            }
        }

        ActionProcessor.executeActions(p, prevReader.getWorld().getActionsOnQuit());

        WorldReader reader = new WorldReader(newWorld);
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, reader.getWorld().getActionsOnJoin()), 5L);
    }

    @EventHandler
    public void onWorldChange(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();

        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> {
            World fromW = from.getWorld();
            if (fromW == null) return;

            String fromN = fromW.getName();
            if (!fromN.equals(p.getWorld().getName())) new PlayerUtils(p).storeLastLocation(from, fromN);
        }, 1L);
    }
}