package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ActionProcessor.executeActions(p, WorldManager.getActionsOnDeath(p));
        e.setDeathMessage(null);

        String deathMessage = WorldManager.getDeathMessage(p);
        if (deathMessage == null || deathMessage.equals("null")) return;
        String message = BWChat.color(deathMessage.replace("%player%", p.getName()));
        if (Values.CONFIG.isIsolateChat()) {
            for (Player player : p.getWorld().getPlayers()) player.sendMessage(message);
        }
        else Bukkit.broadcastMessage(message);
    }
}