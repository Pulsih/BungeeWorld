package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWMessages;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worlds.BWWorld;
import me.pulsi_.bungeeworld.worlds.WorldReader;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        FileConfiguration config = BungeeWorld.INSTANCE.getConfig();

        Player p = e.getPlayer();
        String command = e.getMessage().toLowerCase();

        if (command.contains(":") && Values.CONFIG.isDenyDotsCommands() && !p.isOp()) {
            BWMessages.send(p, "invalid_format");
            e.setCancelled(true);
            return;
        }

        String worldName = p.getWorld().getName();
        BWWorld world = new WorldReader(worldName).getWorld();

        String denyMessage = world.security.denyMessage;
        List<String> deniedCommandsStartsWith = world.denyCommandsStartsWith, deniedCommand = world.denyCommandsSingle;

        for (String cmd : deniedCommandsStartsWith) {
            if (!command.startsWith(cmd.toLowerCase())) continue;

            p.sendMessage(BWChat.color(denyMessage));
            e.setCancelled(true);
            return;
        }

        for (String cmd : deniedCommand) {
            if (!command.equals(cmd.toLowerCase())) continue;

            p.sendMessage(BWChat.color(denyMessage));
            e.setCancelled(true);
            return;
        }
    }
}