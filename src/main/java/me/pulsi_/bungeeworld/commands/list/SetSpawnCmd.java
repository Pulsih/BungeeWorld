package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.WorldReader;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import me.pulsi_.bungeeworld.utils.BWMessages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class SetSpawnCmd extends BWCommand {

    private WorldsRegistry registry;

    public SetSpawnCmd(String... aliases) {
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
        Player p = ((Player) s);
        String worldName = p.getWorld().getName();
        BWWorld world = new WorldReader(worldName).getWorld();

        if (world == null) {
            world = new BWWorld(worldName);
            BungeeWorld.INSTANCE.getWorldsRegistry().setupWorld(world);
        }
        world.setSpawn(p.getLocation());
        registry.saveWorldSettings(world);

        BWMessages.send(s, "spawn_set");
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}