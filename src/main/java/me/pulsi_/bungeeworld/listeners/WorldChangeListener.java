package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World fromWorld = e.getFrom();

        if (Values.CONFIG.isIsolateInventories()) {
            Storage.savePlayerStatistics(p, fromWorld);
            if (!Storage.isRegistered(p)) {
                Storage.clearPlayer(p);
                Storage.savePlayerStatistics(p);
            } else Storage.givePlayerStatistics(p);
        }

        Bukkit.getScheduler().runTaskLater(BungeeWorld.getInstance(), () -> ActionProcessor.executeActions(p, WorldManager.getActionsOnJoin(p)), 10L);

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