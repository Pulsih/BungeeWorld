package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage().toLowerCase();

        if (command.contains(":") && Values.CONFIG.isDenyDotsCommands() && !p.isOp()) {
            BWMessages.send(p, "invalid_format");
            e.setCancelled(true);
            return;
        }

        String worldName = p.getWorld().getName();
        BWWorld world = new WorldReader(worldName).getWorld();

        List<String> deniedCommandsStartsWith = world.getDenyCommandsStartsWith(), deniedCommand = world.getDenyCommandsSingle();

        for (String cmd : deniedCommandsStartsWith) {
            if (!command.startsWith(cmd.toLowerCase())) continue;

            BWMessages.send(p, world.getSecurity().denyMessage, true);
            e.setCancelled(true);
            return;
        }

        for (String cmd : deniedCommand) {
            if (!command.equals(cmd.toLowerCase())) continue;

            BWMessages.send(p, world.getSecurity().denyMessage, true);
            e.setCancelled(true);
            return;
        }
    }
}