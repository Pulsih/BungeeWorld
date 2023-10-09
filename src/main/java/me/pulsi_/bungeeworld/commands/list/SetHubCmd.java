package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import me.pulsi_.bungeeworld.utils.BWMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class SetHubCmd extends BWCommand {

    public SetHubCmd(String... aliases) {
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
        return true;
    }

    @Override
    public boolean skipUsageWarn() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        BWConfigs configs = BungeeWorld.INSTANCE.getConfigs();
        File file = configs.getFile(BWConfigs.Type.SAVES);
        FileConfiguration config = configs.getConfig(file);

        config.set("hub", ((Player) s).getWorld().getName());
        configs.save(config, file, true);

        BWMessages.send(s, "hub_set");
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}