package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (!Values.CONFIG.isIsolateChat()) return;

        Player p = e.getPlayer();
        World world = p.getWorld();
        List<Player> newRecipientPlayers = new ArrayList<>(world.getPlayers());
        List<String> linkedWorldsNames = new WorldReader(world.getName()).getLinkedWorlds();

        if (!linkedWorldsNames.isEmpty()) {
            for (String linkedWorldName : linkedWorldsNames) {
                if (linkedWorldName.equals(world.getName())) continue;

                World linkedWorld = Bukkit.getWorld(linkedWorldName);
                if (linkedWorld != null)
                    newRecipientPlayers.addAll(linkedWorld.getPlayers());
            }
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!newRecipientPlayers.contains(player))
                e.getRecipients().remove(player);
        });
    }
}