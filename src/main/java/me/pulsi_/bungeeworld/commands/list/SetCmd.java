package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import me.pulsi_.bungeeworld.utils.BWArgs;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetCmd extends BWCommand {

    private final WorldsRegistry registry;

    public SetCmd(String... aliases) {
        super(aliases);
        registry = BungeeWorld.INSTANCE.getWorldsRegistry();
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
        return "/bw set [worldName] [setting] [value]";
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

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        if (args.length == 1)
            return BWArgs.getWorlds(args);
        return null;
    }
}