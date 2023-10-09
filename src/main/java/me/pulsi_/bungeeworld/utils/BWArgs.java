package me.pulsi_.bungeeworld.utils;

import me.pulsi_.bungeeworld.BungeeWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BWArgs {

    public static List<String> getOnlinePlayers(String[] args) {
        List<String> returningArgs = new ArrayList<>();

        List<String> onlinePlayers = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) onlinePlayers.add(p.getName());

        for (String arg : onlinePlayers)
            if (arg.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) returningArgs.add(arg);
        return returningArgs;
    }

    public static List<String> getWorlds(String[] args) {
        List<String> returningArgs = new ArrayList<>();
        for (String arg : BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().keySet())
            if (arg.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) returningArgs.add(arg);
        return returningArgs;
    }

    public static List<String> getArgs(String[] args, String... options) {
        List<String> returningArgs = new ArrayList<>();
        for (String arg : options)
            if (arg.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) returningArgs.add(arg);
        return returningArgs;
    }

    public static List<String> getArgs(String[] args, List<String> options) {
        List<String> returningArgs = new ArrayList<>();
        for (String arg : options)
            if (arg.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) returningArgs.add(arg);
        return returningArgs;
    }
}