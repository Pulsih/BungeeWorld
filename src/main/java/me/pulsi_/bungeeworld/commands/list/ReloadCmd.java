package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.utils.BWMessages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCmd extends BWCommand {

    public ReloadCmd(String... aliases) {
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
        return null;
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
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        if (confirm(s)) return false;

        long start = System.currentTimeMillis();
        BWMessages.send(s, "%prefix% &7Initializing reload task...", true);

        if (BungeeWorld.INSTANCE.getData().reloadPlugin())
            BWMessages.send(s, "%prefix% &2Successfully reloaded the plugin! &8(&3Took " + (System.currentTimeMillis() - start) + "ms&8)", true);
        else
            BWMessages.send(s, "%prefix% &cSomething went wrong while reloading the plugin, check the console for more info.", true);

        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}