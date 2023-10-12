package me.pulsi_.bungeeworld.commands;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.utils.BWUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BWCommand {

    private final String identifier, permission;

    private final String[] aliases;

    public BWCommand(String... aliases) {
        this.identifier = aliases[0];
        this.permission = "bungeeworld." + identifier.toLowerCase();

        this.aliases = new String[aliases.length - 1];
        for (int i = 1; i < aliases.length; i++) this.aliases[i - 1] = aliases[i];
    }

    public String getIdentifier() {
        return identifier;
    }

    private final HashMap<String, Long> cooldownMap = new HashMap<>();

    private final List<String> confirm = new ArrayList<>();

    public abstract boolean needConfirm();

    public abstract boolean hasCooldown();

    public int getCooldown() {
        return 5;
    }

    public int getConfirmCooldown() {
        return 5;
    }

    public abstract String getUsage();

    public abstract String getConfirmMessage();

    public abstract String getCooldownMessage();

    public String getPermission() {
        return permission;
    }

    public void register() {
        MainCmd.commands.put(identifier.toLowerCase(), this);
        for (String alias : aliases)
            MainCmd.commands.put(alias.toLowerCase(), this);
    }

    public boolean confirm(CommandSender s) {
        if (needConfirm()) {
            if (!confirm.contains(s.getName())) {
                Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> confirm.remove(s.getName()), getConfirmCooldown() * 20L);
                if (getConfirmMessage() != null && !getConfirmMessage().equals("")) sendConfirm(s);
                confirm.add(s.getName());
                return true;
            }
            confirm.remove(s.getName());
        }
        return false;
    }

    public void execute(CommandSender s, String[] args) {
        if (!BWUtils.hasPermission(s, getPermission()) || (playerOnly() && !BWUtils.isPlayer(s))) return;

        if (!skipUsageWarn() && args.length == 1) {
            if (getUsage() != null && !getUsage().isEmpty()) sendUsage(s);
            return;
        }

        if (!onCommand(s, args)) return;

        if (hasCooldown() && getCooldown() > 0 && !(s instanceof ConsoleCommandSender)) {
            if (cooldownMap.containsKey(s.getName()) && cooldownMap.get(s.getName()) > System.currentTimeMillis()) {
                if (getCooldownMessage() != null && !getCooldownMessage().isEmpty())
                    BWMessages.send(s, getCooldownMessage(), true);
                return;
            }
            cooldownMap.put(s.getName(), System.currentTimeMillis() + (getCooldown() * 1000L));
            Bukkit.getScheduler().runTaskLater(BungeeWorld.INSTANCE, () -> cooldownMap.remove(s.getName()), getCooldown() * 20L);
        }
    }

    public abstract boolean playerOnly();

    public abstract boolean skipUsageWarn();

    public abstract boolean onCommand(CommandSender s, String[] args);

    public abstract List<String> tabCompletion(CommandSender s, String[] args);

    private void sendUsage(CommandSender s) {
        String preUsage = "%prefix% &7Command usage: &3", usage = getUsage()
                .replace("[", "&8[&b")
                .replace("]", "&8]")
                .replace("<", "&8<&3")
                .replace(">", "&8>");

        BWMessages.send(s, preUsage + usage, true);
    }

    private void sendConfirm(CommandSender s) {
        String preConfirm = "%prefix% &c", confirm = getConfirmMessage();
        BWMessages.send(s, preConfirm + confirm, true);
    }
}