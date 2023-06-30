package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.Storage;
import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        
        Player p = e.getPlayer();

        Storage.updateCurrentStatistic(p);
        Storage.saveAllPlayerStatistics(p);

        if (Values.CONFIG.isTeleportHubWhenJoin()) {
            Location hub = BWUtils.getLocation(Values.CONFIG.getHubSpawn());
            if (hub != null) p.teleport(hub);
        }
        BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().remove(p.getUniqueId());

        String worldName = p.getWorld().getName();
        WorldReader reader = new WorldReader(worldName);
        ActionProcessor.executeActions(p, reader.getActionsOnQuit());

        String quitMessage = reader.getQuitMessage();
        if (quitMessage == "") return;

        String message = BWChat.color(quitMessage.replace("%player%", p.getName()));
        if (!Values.CONFIG.isIsolateChat()) Bukkit.broadcastMessage(message);
        else {
            for (Player player : p.getWorld().getPlayers()) {
                if (!player.equals(p)) player.sendMessage(message);
            }

            List<String> linkedWorldsNames = reader.getLinkedWorlds();
            if (linkedWorldsNames.isEmpty()) return;

            for (String linkedWorldName : linkedWorldsNames) {
                if (linkedWorldName.equals(worldName)) continue;

                World linkedWorld = Bukkit.getWorld(linkedWorldName);
                if (linkedWorld == null) continue;

                for (Player players : linkedWorld.getPlayers())
                    players.sendMessage(quitMessage);
            }
        }
    }
}