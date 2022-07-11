package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.managers.MessagesManager;
import me.pulsi_.bungeeworld.utils.BWMethods;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SpawnCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (!(s instanceof Player)) {
            MessagesManager.send(s, "not_player");
            return false;
        }
        Player p = (Player) s;

        FileConfiguration worlds = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.WORLDS);
        String location = worlds.getString(p.getWorld().getName() + ".spawn");
        if (location == null) {
            MessagesManager.send(p, "spawn_not_set");
            return false;
        }

        p.teleport(BWMethods.getLocation(location));
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 5, 1);
        MessagesManager.send(p, "teleported_spawn");
        return true;
    }
}
