package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.PlayerUtils;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWArgs;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SendCmd extends BWCommand {

    public SendCmd(String... aliases) {
        super(aliases);
    }

    @Override
    public boolean needConfirm() {
        return false;
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }

    @Override
    public String getUsage() {
        return "/bw send [playerName] [worldName]";
    }

    @Override
    public String getConfirmMessage() {
        return null;
    }

    @Override
    public String getCooldownMessage() {
        return null;
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public boolean skipUsageWarn() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        if (args.length == 2) {
            BWMessages.send(s, "specify_world");
            return false;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            BWMessages.send(s, "invalid_player");
            return false;
        }

        World destination = Bukkit.getWorld(args[2]);
        if (destination == null) {
            BWMessages.send(s, "invalid_world");
            return false;
        }

        BWWorld world = new WorldReader(destination.getName()).getWorld();
        Location loc = BWUtils.getLocation(world.getSpawn());

        if (world.isTeleportToLastLocation()) {
            Location lastLocation = new PlayerUtils(target).getBWPlayer(world.getName()).getLastLocation();
            if (lastLocation != null) loc = lastLocation;
        }

        if (loc != null) target.teleport(loc);
        else target.teleport(destination.getSpawnLocation());

        BWMessages.send(s, "player_sent", "%player%$" + target.getName(), "%world%$" + destination.getName());
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        if (args.length == 2)
            return BWArgs.getWorlds(args);
        return null;
    }
}