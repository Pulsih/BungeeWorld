package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWMethods;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        String worldName = p.getWorld().getName();

        if (Values.CONFIG.isTeleportHubWhenJoin()) {
            if (!worldName.equals(Values.CONFIG.getHubName())) {
                Location hub = BWMethods.getLocation(Values.CONFIG.getHubSpawn());
                if (hub != null) p.teleport(BWMethods.getLocation(Values.CONFIG.getHubSpawn()));
            } else {
                if (Values.CONFIG.isIsolateInventories()) Storage.givePlayerStatistics(p, Values.CONFIG.getHubName());
            }
        } else {
            if (Values.CONFIG.isIsolateInventories()) Storage.givePlayerStatistics(p);
        }

        Bukkit.getScheduler().runTaskLater(BungeeWorld.getInstance(), () -> ActionProcessor.executeActions(p, WorldManager.getActionsOnJoin(p)), 10L);

        Location spawn = BWMethods.getLocation(WorldManager.getSpawn(p));
        if (spawn != null) p.teleport(spawn);

        String joinMessage = WorldManager.getJoinMessage(p);
        if (joinMessage != null && !joinMessage.equals("null")) {
            String message = BWChat.color(joinMessage.replace("%player%", p.getName()));
            if (!Values.CONFIG.isIsolateChat()) Bukkit.broadcastMessage(message);
        }

        if (Values.CONFIG.isJoinSendTitle()) BWMethods.sendTitle(p, Values.CONFIG.getJoinTitle());
        if (Values.CONFIG.isJoinPlaySound()) BWMethods.playSound(p, Values.CONFIG.getJoinSound());
    }
}