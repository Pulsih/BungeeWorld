package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (!BWUtils.isPlayer(s)) return false;
        Player p = (Player) s;

        if (!BWUtils.hasPermission(p, "bungeeworld.hub")) return false;

        String hubName = Values.GLOBAL.getHub();
        World world = Bukkit.getWorld(hubName);

        if (world == null) {
            BWMessages.send(p, "hub_not_set");
            return false;
        }

        if (p.getWorld().getName().equals(hubName)) {
            BWMessages.send(p, "already_at_hub");
            return false;
        }

        Location hub = new WorldReader(hubName).getWorld().getSpawn();
        if (hub != null) p.teleport(hub);
        else p.teleport(world.getSpawnLocation());

        BWUtils.playSound(p, Values.CONFIG.getHubTeleportSound());
        BWMessages.send(p, "teleported_hub");
        return true;
    }
}