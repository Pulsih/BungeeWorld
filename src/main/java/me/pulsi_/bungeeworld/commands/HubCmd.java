package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.managers.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!BWUtils.isPlayer(s)) return false;
        Player p = (Player) s;

        if (!BWUtils.hasPermissions(p, "bungeeworld.hub")) return false;

        if (p.getWorld().getName().equals(Values.CONFIG.getHubName())) {
            BWMessages.send(p, "already_at_hub");
            return false;
        }

        Location hub = BWUtils.getLocation(Values.CONFIG.getHubSpawn());
        if (hub == null) {
            BWMessages.send(p, "cannot_find_hub");
            return false;
        }

        p.teleport(hub);
        BWUtils.playSound(p, Values.CONFIG.getHubTeleportSound());
        BWMessages.send(p, "teleported_hub");
        return true;
    }
}