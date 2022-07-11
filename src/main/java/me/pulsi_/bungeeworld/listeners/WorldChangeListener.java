package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldChangeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Storage.givePlayerStatistics(p);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World fromWorld = e.getFrom();
        Storage.savePlayerStatistics(p, fromWorld);
        Storage.givePlayerStatistics(p);

        String quitMessage = WorldManager.getQuitMessage(p);
        if (quitMessage != null && !quitMessage.equals("null")) {
            String message = BWChat.color(quitMessage.replace("%player%", p.getName()));
            if (Values.CONFIG.isIsolateChat())
                for (Player player : fromWorld.getPlayers()) player.sendMessage(message);
        }

        String joinMessage = WorldManager.getJoinMessage(p);
        if (joinMessage != null && !joinMessage.equals("null")) {
            String message = BWChat.color(joinMessage.replace("%player%", p.getName()));
            if (Values.CONFIG.isIsolateChat())
                for (Player player : p.getWorld().getPlayers()) player.sendMessage(message);
        }
    }
}