package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.*;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWMethods;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (args.length == 0) {
            String v = BungeeWorld.getInstance().getDescription().getVersion();
            s.sendMessage(BWChat.color("&2&lBungee&a&lWorld &7Running on v" + v + ". Plugin made by Pulsi_"));
            return false;
        }

        switch (args[0]) {
            case "give": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.give")) return false;

                if (args.length == 1) {
                    MessagesManager.send(s, "specify_player");
                    return false;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    MessagesManager.send(s, "invalid_player");
                    return false;
                }

                if (args.length == 2) {
                    MessagesManager.send(s, "specify_item");
                    return false;
                }
                ItemStack item = ItemManager.itemsList.get(args[2]);
                if (item == null) {
                    MessagesManager.send(s, "invalid_item");
                    return false;
                }

                Inventory inv = target.getInventory();
                if (args.length == 3) {
                    inv.addItem(item);
                    MessagesManager.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
                    return true;
                }

                String number = args[3];
                if (!BWMethods.isValidNumber(number)) {
                    MessagesManager.send(s, "invalid_number");
                    return false;
                }
                int slot = Integer.parseInt(number) - 1;

                if (args.length == 4) {
                    if (inv.getItem(slot) == null) inv.setItem(slot, item);
                    else inv.addItem(item);
                    MessagesManager.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
                    return true;
                }

                boolean force = Boolean.parseBoolean(args[4]);
                if (force) inv.setItem(slot, item);
                else {
                    if (inv.getItem(slot) == null) inv.setItem(slot, item);
                    else inv.addItem(item);
                }
                MessagesManager.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
            }
            break;

            case "send": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.send")) return false;

                if (args.length == 1) {
                    MessagesManager.send(s, "specify_player");
                    return false;
                }
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    MessagesManager.send(s, "invalid_player");
                    return false;
                }

                if (args.length == 2) {
                    MessagesManager.send(s, "specify_world");
                    return false;
                }
                World world = Bukkit.getWorld(args[2]);
                if (world == null) {
                    MessagesManager.send(s, "invalid_world");
                    return false;
                }

                Location spawn = BWMethods.getLocation(WorldManager.getSpawn(world));
                if (spawn != null) target.teleport(spawn);
                else target.teleport(world.getSpawnLocation());

                MessagesManager.send(s, "player_sent", "%player%$" + target.getName(), "%world%$" + world.getName());
            }
            break;

            case "tp": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.tp") || !BWMethods.isPlayer(s)) return false;
                Player p = (Player) s;

                if (args.length == 1) {
                    MessagesManager.send(s, "specify_world");
                    return false;
                }
                World world = Bukkit.getWorld(args[1]);
                if (world == null) {
                    MessagesManager.send(s, "invalid_world");
                    return false;
                }

                Location spawn = BWMethods.getLocation(WorldManager.getSpawn(world));
                if (spawn != null) p.teleport(spawn);
                else p.teleport(world.getSpawnLocation());

                MessagesManager.send(s, "player_tp", "%world%$" + world.getName());
            }
            break;

            case "sethub": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.sethub") || !BWMethods.isPlayer(s)) return false;
                Player p = (Player) s;

                ConfigManager configManager = BungeeWorld.getInstance().getConfigs();
                FileConfiguration config = configManager.getConfig(ConfigManager.Type.CONFIG);

                config.set("hub.spawn", BWMethods.getStringLocation(p.getLocation()));
                config.set("hub.name", p.getWorld().getName());
                configManager.saveConfig(ConfigManager.Type.CONFIG, false);
                configManager.reloadConfig(ConfigManager.Type.CONFIG);
                configManager.buildConfig();
                Values.CONFIG.loadValues();

                MessagesManager.send(p, "hub_set");
            }
            break;

            case "setspawn": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.setspawn") || !BWMethods.isPlayer(s)) return false;
                Player p = (Player) s;

                ConfigManager configManager = BungeeWorld.getInstance().getConfigs();
                FileConfiguration worlds = configManager.getConfig(ConfigManager.Type.WORLDS);

                worlds.set(p.getWorld().getName() + ".spawn", BWMethods.getStringLocation(p.getLocation()));
                configManager.saveConfig(ConfigManager.Type.WORLDS, true);

                MessagesManager.send(p, "spawn_set");
            }
            break;

            case "reload": {
                if (!BWMethods.hasPermissions(s, "bungeeworld.setspawn")) return false;
                DataManager.reloadConfigs();
                MessagesManager.send(s, "plugin_reloaded");
            }
            break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {

        switch (args.length) {
            case 1: {
                List<String> args1 = new ArrayList<>();
                List<String> listOfArgs = new ArrayList<>();
                if (s.hasPermission("bungeeworld.give")) listOfArgs.add("give");
                if (s.hasPermission("bungeeworld.reload")) listOfArgs.add("reload");
                if (s.hasPermission("bungeeworld.send")) listOfArgs.add("send");
                if (s.hasPermission("bungeeworld.sethub")) listOfArgs.add("sethub");
                if (s.hasPermission("bungeeworld.setspawn")) listOfArgs.add("setspawn");
                if (s.hasPermission("bungeeworld.tp")) listOfArgs.add("tp");

                for (String arg : listOfArgs) if (arg.startsWith(args[0].toLowerCase())) args1.add(arg);
                return args1;
            }

            case 2: {
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;
                        List<String> args2 = new ArrayList<>();
                        List<String> listOfArgs = new ArrayList<>();
                        Bukkit.getOnlinePlayers().forEach(p -> listOfArgs.add(p.getName()));

                        for (String arg : listOfArgs)
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase())) args2.add(arg);
                        return args2;
                    }
                    case "send": {
                        if (!s.hasPermission("bungeeworld.send")) return null;
                        List<String> args2 = new ArrayList<>();
                        List<String> listOfArgs = new ArrayList<>();
                        Bukkit.getOnlinePlayers().forEach(p -> listOfArgs.add(p.getName()));

                        for (String arg : listOfArgs)
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase())) args2.add(arg);
                        return args2;
                    }
                    case "tp": {
                        if (!s.hasPermission("bungeeworld.tp")) return null;
                        List<String> args2 = new ArrayList<>();
                        List<String> listOfArgs = new ArrayList<>();
                        Bukkit.getWorlds().forEach(world -> listOfArgs.add(world.getName()));

                        for (String arg : listOfArgs)
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase())) args2.add(arg);
                        return args2;
                    }
                }
            }

            case 3: {
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;
                        List<String> args3 = new ArrayList<>();
                        for (String arg : ItemManager.itemsName)
                            if (arg.toLowerCase().startsWith(args[2].toLowerCase())) args3.add(arg);
                        return args3;
                    }
                    case "send": {
                        if (!s.hasPermission("bungeeworld.send")) return null;
                        List<String> args3 = new ArrayList<>();
                        List<String> listOfArgs = new ArrayList<>();
                        Bukkit.getWorlds().forEach(world -> listOfArgs.add(world.getName()));

                        for (String arg : listOfArgs)
                            if (arg.toLowerCase().startsWith(args[2].toLowerCase())) args3.add(arg);
                        return args3;
                    }
                }
            }

            case 4: {
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;
                        List<String> args4 = new ArrayList<>();
                        List<String> listOfArgs = new ArrayList<>();
                        for (int i = 1; i <= 9; i++) listOfArgs.add(i + "");

                        for (String arg : listOfArgs) if (arg.startsWith(args[3])) args4.add(arg);
                        return args4;
                    }
                }
            }

            case 5: {
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;
                        List<String> args5 = new ArrayList<>();
                        for (String arg : Arrays.asList("true", "false"))
                            if (arg.startsWith(args[4].toLowerCase())) args5.add(arg);
                        return args5;
                    }
                }
            }
        }
        return null;
    }
}