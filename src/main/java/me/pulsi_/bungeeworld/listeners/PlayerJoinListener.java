package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Storage.givePlayerStatistics(p, Values.CONFIG.getHubName());
        Bukkit.getScheduler().runTaskLater(BungeeWorld.getInstance(), () -> ActionProcessor.executeActions(p, WorldManager.getActionsOnJoin(p)), 10L);
        e.setJoinMessage(null);

        String joinMessage = WorldManager.getJoinMessage(p);
        if (joinMessage == null || joinMessage.equals("null")) return;
        String message = BWChat.color(joinMessage.replace("%player%", ""));
        if (!Values.CONFIG.isIsolateChat()) Bukkit.broadcastMessage(message);
    }
}