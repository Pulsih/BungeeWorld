package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.players.BWPlayer;
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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

        Player p = e.getPlayer();
        BWPlayer bwP = BungeeWorld.INSTANCE.getPlayerRegistry().registerPlayer(p);

        if (Values.CONFIG.isClearChat()) BWUtils.clearChat(p);

        String worldName = p.getWorld().getName();
        boolean load = true, tpSpawn = true;
        if (Values.CONFIG.isTeleportHubWhenJoin() && !worldName.equals(Values.CONFIG.getHubName())) {
            Location hub = BWUtils.getLocation(Values.CONFIG.getHubSpawn());
            if (hub != null) {
                p.teleport(hub);
                worldName = p.getWorld().getName();

                load = false;
                tpSpawn = false;
            }
        }

        if (load) Storage.updateAllStatistic(p);

        if (!bwP.config.getConfigurationSection("").contains(worldName))
            Storage.saveAllPlayerStatistics(p);

        WorldReader reader = new WorldReader(worldName);
        Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> ActionProcessor.executeActions(p, reader.getActionsOnJoin()), 4L);

        if (reader.teleportToSpawnOnJoin() && tpSpawn) {
            Location spawn = BWUtils.getLocation(reader.getSpawn());
            if (spawn != null) p.teleport(spawn);
        }

        if (Values.CONFIG.isJoinSendTitle()) BWUtils.sendTitle(p, Values.CONFIG.getJoinTitle());
        if (Values.CONFIG.isJoinPlaySound()) BWUtils.playSound(p, Values.CONFIG.getJoinSound());

        String joinMessage = reader.getJoinMessage();
        if (joinMessage == "") return;

        String message = BWChat.color(joinMessage.replace("%player%", p.getName()));
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
                    players.sendMessage(joinMessage);
            }
        }
    }
}