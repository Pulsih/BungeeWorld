package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.actions.ActionProcessor;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);

        Player p = e.getEntity();
        World world = p.getWorld();

        WorldReader reader = new WorldReader(world.getName());
        ActionProcessor.executeActions(p, reader.getActionsOnDeath());

        String deathMessage;
        Player killer = e.getEntity().getKiller();
        if (killer == null) deathMessage = reader.getDeathMessage1();
        else {
            ItemStack item = killer.getItemInHand();
            if (item.getType().equals(Material.AIR))
                deathMessage = reader.getDeathMessage2().replace("%killer%", killer.getName());
            else {
                String name;
                deathMessage = reader.getDeathMessage3();

                ItemMeta meta = item.getItemMeta();
                if (meta.hasDisplayName()) name = meta.getDisplayName();
                else name = item.getType().toString();

                deathMessage = deathMessage.replace("%killer%", killer.getName()).replace("%item%", name);
            }
        }

        if (deathMessage == "") return;

        String message = BWChat.color(deathMessage.replace("%player%", p.getName()));
        if (!Values.CONFIG.isIsolateChat()) {
            Bukkit.broadcastMessage(message);
            return;
        }

        for (Player player : p.getWorld().getPlayers())
            player.sendMessage(message);

        List<String> linkedWorldsNames = reader.getLinkedWorlds();
        if (linkedWorldsNames.isEmpty()) return;

        for (String linkedWorldName : linkedWorldsNames) {
            if (linkedWorldName.equals(world.getName())) continue;

            World linkedWorld = Bukkit.getWorld(linkedWorldName);
            if (linkedWorld == null) continue;

            for (Player players : linkedWorld.getPlayers())
                players.sendMessage(message);
        }
    }
}