package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.managers.MessagesManager;
import me.pulsi_.bungeeworld.managers.WorldManager;
import me.pulsi_.bungeeworld.utils.BWMethods;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!BWMethods.isPlayer(s)) return false;
        Player p = (Player) s;
        if (!BWMethods.hasPermissions(p, "bungeeworld.hub")) return false;

        Location location = BWMethods.getLocation(WorldManager.getSpawn(p));
        if (location == null) {
            MessagesManager.send(p, "spawn_not_set");
            return false;
        }

        p.teleport(location);
        MessagesManager.send(p, "teleported_spawn");
        return true;
    }
}
