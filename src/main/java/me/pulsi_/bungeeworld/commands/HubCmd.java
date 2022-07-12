package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.managers.MessagesManager;
import me.pulsi_.bungeeworld.utils.BWMethods;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!BWMethods.isPlayer(s)) return false;
        Player p = (Player) s;

        if (!BWMethods.hasPermissions(p, "bungeeworld.hub")) return false;

        if (p.getWorld().getName().equals(Values.CONFIG.getHubName())) {
            MessagesManager.send(p, "already_at_hub");
            return false;
        }

        Location hub = BWMethods.getLocation(Values.CONFIG.getHubSpawn());
        if (hub == null) {
            MessagesManager.send(p, "cannot_find_hub");
            return false;
        }

        p.teleport(hub);
        BWMethods.playSound(p, Values.CONFIG.getHubTeleportSound());
        MessagesManager.send(p, "teleported_hub");
        return true;
    }
}