package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWMessages;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.managers.ItemManager;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worlds.WorldReader;
import me.pulsi_.bungeeworld.worlds.WorldsRegistry;
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
import java.util.Collections;
import java.util.List;

public class MainCmd implements CommandExecutor, TabCompleter {

    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if (args.length == 0) {
            String v = BungeeWorld.INSTANCE.getDescription().getVersion();
            s.sendMessage(BWChat.color("&2&lBungee&a&lWorld &7Running on v" + v + ". Plugin made by Pulsi_ with &c<3&7."));
            return true;
        }

        switch (args[0]) {
            case "give": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.give")) return false;

                if (args.length == 1) {
                    BWMessages.send(s, "specify_player");
                    return false;
                }
                if (args.length == 2) {
                    BWMessages.send(s, "specify_item");
                    return false;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    BWMessages.send(s, "invalid_player");
                    return false;
                }

                ItemStack item = ItemManager.itemsList.get(args[2]);
                if (item == null) {
                    BWMessages.send(s, "invalid_item");
                    return false;
                }

                Inventory playerInventory = target.getInventory();
                if (args.length == 3) {
                    BWMessages.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
                    playerInventory.addItem(item);
                    return true;
                }

                String number = args[3];
                if (!BWUtils.isValidNumber(number)) {
                    BWMessages.send(s, "invalid_number");
                    return false;
                }

                int slot = Integer.parseInt(number) - 1;
                if (args.length == 4) {
                    if (playerInventory.getItem(slot) == null) {
                        playerInventory.setItem(slot, item);
                    } else {
                        playerInventory.addItem(item);
                    }
                    BWMessages.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
                    return true;
                }

                boolean force = Boolean.parseBoolean(args[4]);
                if (force) playerInventory.setItem(slot, item);
                else if (playerInventory.getItem(slot) == null) playerInventory.setItem(slot, item);
                else playerInventory.addItem(item);

                BWMessages.send(s, "item_given", "%item%$" + args[2], "%player%$" + target.getName());
                return true;
            }

            case "send": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.send")) return false;

                if (args.length == 1) {
                    BWMessages.send(s, "specify_player");
                    return false;
                }
                if (args.length == 2) {
                    BWMessages.send(s, "specify_world");
                    return false;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null || !BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().containsKey(target.getUniqueId())) {
                    BWMessages.send(s, "invalid_player");
                    return false;
                }

                World destination = Bukkit.getWorld(args[2]);
                if (destination == null) {
                    BWMessages.send(s, "invalid_world");
                    return false;
                }

                WorldReader reader = new WorldReader(destination.getName());

                Location loc;
                if (reader.teleportToLastLocation()) {
                    loc = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(target.getUniqueId()).locations.get(destination.getName());
                    if (loc == null) loc = BWUtils.getLocation(reader.getSpawn());
                } else loc = BWUtils.getLocation(reader.getSpawn());

                if (loc != null) target.teleport(loc);
                else target.teleport(destination.getSpawnLocation());

                BWMessages.send(s, "player_sent", "%player%$" + target.getName(), "%world%$" + destination.getName());
                return true;
            }

            case "set": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.set")) return false;

                if (args.length == 1) {
                    BWMessages.send(s, "specify_world");
                    return false;
                }
                if (args.length == 2) {
                    BWMessages.send(s, "specify_setting");
                    return false;
                }

                World world = Bukkit.getWorld(args[1]);
                if (world == null) {
                    BWMessages.send(s, "invalid_world");
                    return false;
                }

                String input = BWUtils.getStringAfterArgs(args, 3);
                if (input == null) {
                    BWMessages.send(s, "specify_value");
                    return false;
                }

                WorldsRegistry registry = BungeeWorld.INSTANCE.getWorldsRegistry();

                switch (args[2].toLowerCase()) {
                    case "denied-commands.deny-message":
                    case "security.deny-message":
                    case "death-message":
                    case "join-message":
                    case "quit-message":

                        registry.setValue(world, args[2], input);
                        break;

                    case "security.disable-block-place":
                    case "security.disable-block-break":
                    case "security.disable-mob-spawning":
                    case "security.disable-explosions":
                    case "security.disable-player-actions":
                    case "security.disable-players-drops":
                    case "security.disable-players-pickup":
                    case "security.disable-fall-damage":
                    case "security.disable-pvp":

                        registry.setValue(world, args[2], Boolean.valueOf(input));
                        break;

                    case "denied-commands.starts-with":
                    case "denied-commands.single-command":
                    case "actions-on-join":
                    case "actions-on-quit":
                    case "actions-on-death":
                    case "linked-worlds":

                        List<String> list;
                        if (input.contains(",")) list = new ArrayList<>(Arrays.asList(input.split(",")));
                        else list = new ArrayList<>(Collections.singletonList(input));
                        registry.setValue(world, args[2], list);
                        break;

                    default:
                        BWMessages.send(s, "invalid_setting");
                        return false;
                }
                BWMessages.send(s, "setting_updated");
                return true;
            }

            case "tp": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.tp") || !BWUtils.isPlayer(s)) return false;

                Player p = (Player) s;
                if (args.length == 1) {
                    BWMessages.send(s, "specify_world");
                    return false;
                }

                World world1 = Bukkit.getWorld(args[1]);
                if (world1 == null) {
                    BWMessages.send(s, "invalid_world");
                    return false;
                }

                Location spawn = BWUtils.getLocation(new WorldReader(world1.getName()).getSpawn());
                if (spawn != null) p.teleport(spawn);
                else p.teleport(world1.getSpawnLocation());

                BWMessages.send(s, "player_tp", "%world%$" + world1.getName());
                return true;
            }

            case "sethub": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.sethub") || !BWUtils.isPlayer(s)) return false;

                Player p = (Player)s;
                ConfigManager configManager = BungeeWorld.INSTANCE.getConfigs();
                FileConfiguration config = configManager.getConfig(ConfigManager.Type.CONFIG);
                config.set("hub.spawn", BWUtils.getStringLocation(p.getLocation()));
                config.set("hub.name", p.getWorld().getName());

                configManager.saveConfig(ConfigManager.Type.CONFIG, false);
                configManager.reloadConfig(ConfigManager.Type.CONFIG);
                configManager.buildConfig();
                Values.CONFIG.loadValues();

                BWMessages.send(p, "hub_set");
                return true;
            }

            case "setspawn": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.setspawn") || !BWUtils.isPlayer(s)) return false;

                Player p = (Player) s;
                ConfigManager configManager = BungeeWorld.INSTANCE.getConfigs();
                FileConfiguration worlds = configManager.getConfig(ConfigManager.Type.WORLDS);
                worlds.set(p.getWorld().getName() + ".spawn", BWUtils.getStringLocation(p.getLocation()));
                configManager.saveConfig(ConfigManager.Type.WORLDS, true);

                BWMessages.send(p, "spawn_set");
                return true;
            }

            case "reload": {
                if (!BWUtils.hasPermissions(s, "bungeeworld.setspawn")) return false;

                BungeeWorld.INSTANCE.getDataManager().reloadPlugin();
                BWMessages.send(s, "plugin_reloaded");
                return true;
            }
        }
        BWMessages.send(s, "unknown_command");
        return true;
    }

    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1: {
                List<String> args1 = new ArrayList<>();
                List<String> listOfArgs = new ArrayList<>();
                if (s.hasPermission("bungeeworld.give"))
                    listOfArgs.add("give");

                if (s.hasPermission("bungeeworld.reload"))
                    listOfArgs.add("reload");

                if (s.hasPermission("bungeeworld.send"))
                    listOfArgs.add("send");

                if (s.hasPermission("bungeeworld.set"))
                    listOfArgs.add("set");

                if (s.hasPermission("bungeeworld.sethub"))
                    listOfArgs.add("sethub");

                if (s.hasPermission("bungeeworld.setspawn"))
                    listOfArgs.add("setspawn");

                if (s.hasPermission("bungeeworld.tp"))
                    listOfArgs.add("tp");

                for (String arg : listOfArgs) {
                    if (arg.startsWith(args[0].toLowerCase()))
                        args1.add(arg);
                }
                return args1;
            }

            case 2:
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;

                        List<String> args2 = new ArrayList<>(), list = new ArrayList<>();
                        Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
                        for (String arg : list) {
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase()))
                                args2.add(arg);
                        }
                        return args2;
                    }

                    case "set": {
                        if (!s.hasPermission("bungeeworld.set")) return null;

                        List<String> args2 = new ArrayList<>();
                        for (String arg : BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().keySet()) {
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase()))
                                args2.add(arg);
                        }
                        return args2;
                    }

                    case "send": {
                        if (!s.hasPermission("bungeeworld.send")) return null;

                        List<String> args2 = new ArrayList<>(), list = new ArrayList<>();
                        Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
                        for (String arg : list) {
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase()))
                                args2.add(arg);
                        }
                        return args2;
                    }

                    case "tp": {
                        if (!s.hasPermission("bungeeworld.tp"))
                            return null;
                        List<String> args2 = new ArrayList<>(), list = new ArrayList<>();
                        Bukkit.getWorlds().forEach(world -> list.add(world.getName()));
                        for (String arg : list) {
                            if (arg.toLowerCase().startsWith(args[1].toLowerCase()))
                                args2.add(arg);
                        }
                        return args2;
                    }
                }
                break;

            case 3:
                switch (args[0]) {
                    case "give": {
                        if (!s.hasPermission("bungeeworld.give")) return null;

                        List<String> args3 = new ArrayList<>();
                        for (String arg : ItemManager.itemsName) {
                            if (arg.toLowerCase().startsWith(args[2].toLowerCase()))
                                args3.add(arg);
                        }
                        return args3;
                    }

                    case "set": {
                        if (!s.hasPermission("bungeeworld.set")) return null;

                        List<String> args3 = new ArrayList<>(), list = new ArrayList<>(Arrays.asList(
                                "security.deny-message", "security.disable-block-place", "security.disable-block-break",
                                "security.disable-mob-spawning", "security.disable-explosions", "security.disable-player-actions",
                                "security.disable-players-drops", "security.disable-players-pickup", "security.disable-fall-damage",
                                "security.disable-pvp", "denied-commands.deny-message", "denied-commands.starts-with",
                                "denied-commands.single-command", "actions-on-join", "actions-on-quit",
                                "actions-on-death", "death-message", "join-message", "quit-message",
                                "linked-worlds")
                        );
                        for (String arg : list) {
                            if (arg.startsWith(args[2].toLowerCase()))
                                args3.add(arg);
                        }
                        return args3;
                    }

                    case "send": {
                        if (!s.hasPermission("bungeeworld.send")) return null;

                        List<String> args3 = new ArrayList<>(), list = new ArrayList<>();
                        Bukkit.getWorlds().forEach(world -> list.add(world.getName()));
                        for (String arg : list) {
                            if (arg.toLowerCase().startsWith(args[2].toLowerCase()))
                                args3.add(arg);
                        }
                        return args3;
                    }
                }
                break;

            case 4:
                switch (args[0]) {
                    case "give":
                        if (!s.hasPermission("bungeeworld.give")) return null;

                        List<String> args4 = new ArrayList<>(), list = new ArrayList<>();
                        for (int i = 1; i <= 9; i++) list.add(i + "");

                        for (String arg : list) {
                            if (arg.startsWith(args[3])) args4.add(arg);
                        }
                        return args4;
                }
                break;

            case 5:
                switch (args[0]) {
                    case "give":
                        if (!s.hasPermission("bungeeworld.give")) return null;

                        List<String> args5 = new ArrayList<>();
                        for (String arg : Arrays.asList("true", "false")) {
                            if (arg.startsWith(args[4].toLowerCase()))
                                args5.add(arg);
                        }
                        return args5;
                }
        }
        return null;
    }
}