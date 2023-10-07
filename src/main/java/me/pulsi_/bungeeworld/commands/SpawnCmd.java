package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.registry.WorldReader;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!BWUtils.isPlayer(s)) return false;

        Player p = (Player) s;
        if (!BWUtils.hasPermissions(p, "bungeeworld.spawn")) return false;

        Location location = BWUtils.getLocation(new WorldReader(p.getWorld().getName()).getSpawn());
        if (location == null) {
            BWMessages.send(p, "spawn_not_set");
            return false;
        }

        p.teleport(location);
        BWMessages.send(p, "teleported_spawn");
        return true;
    }
}
