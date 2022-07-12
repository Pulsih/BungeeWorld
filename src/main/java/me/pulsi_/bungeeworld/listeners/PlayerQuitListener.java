package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWMethods;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player p = e.getPlayer();
        if (Values.CONFIG.isTeleportHubWhenJoin()) p.teleport(BWMethods.getLocation(Values.CONFIG.getHubSpawn()));

        String quitMessage = WorldManager.getQuitMessage(p);
        if (quitMessage == null || quitMessage.equals("null")) return;
        String message = BWChat.color(quitMessage.replace("%player%", p.getName()));
        if (!Values.CONFIG.isIsolateChat()) Bukkit.broadcastMessage(message);
    }
}