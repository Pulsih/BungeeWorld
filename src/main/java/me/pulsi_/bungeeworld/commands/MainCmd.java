package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    public static final LinkedHashMap<String, BWCommand> commands = new LinkedHashMap<>();

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (args.length == 0) {
            BWMessages.send(s, "%prefix%  &aRunning on &fv" + BungeeWorld.INSTANCE.getDescription().getVersion() + "&a!", true);
            return true;
        }

        String identifier = args[0].toLowerCase();

        if (!commands.containsKey(identifier)) {
            BWMessages.send(s, "unknown_command");
            return true;
        }

        BWCommand cmd = commands.get(identifier);
        cmd.execute(s, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> cmds = new ArrayList<>();
            for (BWCommand cmd : commands.values())
                if (s.hasPermission("bungeeworld." + cmd.getIdentifier().toLowerCase())) cmds.add(cmd.getIdentifier());

            List<String> args0 = new ArrayList<>();
            for (String arg : cmds)
                if (arg.toLowerCase().startsWith(args[0].toLowerCase())) args0.add(arg);

            return args0;
        }

        String identifier = args[0].toLowerCase();
        if (!commands.containsKey(identifier)) return null;

        BWCommand cmd = commands.get(identifier);
        if (cmd.playerOnly() && !(s instanceof Player)) return null;

        return s.hasPermission("bungeeworld." + identifier) ? cmd.tabCompletion(s, args) : null;
    }
}