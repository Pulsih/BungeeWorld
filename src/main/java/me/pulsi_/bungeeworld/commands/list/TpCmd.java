package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.utils.BWArgs;
import me.pulsi_.bungeeworld.utils.BWMessages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class TpCmd extends BWCommand {

    public TpCmd(String... aliases) {
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
        return "/bw go/tp [worldName]";
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
        return true;
    }

    @Override
    public boolean skipUsageWarn() {
        return false;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        Player p = (Player) s;

        World world = Bukkit.getWorld(args[1]);
        if (world == null) {
            BWMessages.send(s, "invalid_world");
            return false;
        }

        Location spawn = new WorldReader(world.getName()).getWorld().getSpawn();
        if (spawn != null) p.teleport(spawn);
        else p.teleport(world.getSpawnLocation());

        BWMessages.send(s, "player_tp", "%world%$" + world.getName());
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        if (args.length == 1)
            return BWArgs.getWorlds(args);
        return null;
    }
}