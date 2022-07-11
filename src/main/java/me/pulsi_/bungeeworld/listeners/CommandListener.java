package me.pulsi_.bungeeworld.listeners;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        FileConfiguration config = BungeeWorld.getInstance().getConfig();

        Player p = e.getPlayer();
        String world = p.getWorld().getName();
        String command = e.getMessage().toLowerCase();

        if (command.contains(":") && !p.isOp()) {
            e.setCancelled(true);
            p.sendMessage(BWChat.color("&cNon puoi usare questo tipo di formato!"));
            return;
        }

        String denyMessage = config.getString("worlds." + world + ".deny-message");
        List<String> deniedCommandsStartsWith = config.getStringList("worlds." + world + ".denied-commands.starts-with");
        List<String> deniedCommand = config.getStringList("worlds." + world + ".denied-commands.single-command");

        for (String cmd : deniedCommandsStartsWith) {
            if (!command.startsWith(cmd)) continue;
            e.setCancelled(true);
            p.sendMessage(BWChat.color(denyMessage));
            return;
        }

        for (String cmd : deniedCommand) {
            if (!command.equals(cmd)) continue;
            e.setCancelled(true);
            p.sendMessage(BWChat.color(denyMessage));
            return;
        }
    }
}