package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.prisonenchants.PrisonEnchants;
import me.pulsi_.prisonenchants.commands.PECommand;
import me.pulsi_.prisonenchants.enchantSystem.PEEnchantRegistry;
import me.pulsi_.prisonenchants.enchantSystem.enchanter.PEEnchanter;
import me.pulsi_.prisonenchants.enchantSystem.enchants.PEEnchant;
import me.pulsi_.prisonenchants.utils.PEArgs;
import me.pulsi_.prisonenchants.utils.PEMessages;
import me.pulsi_.prisonenchants.utils.PEUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EnchantOthersCmd extends PECommand {

    public EnchantOthersCmd(String... aliases) {
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
        return "/pe forceEnchant [playerName] [add/remove/set] [enchantment] <level> <bypass_cost=true/false> <silent=true/false>";
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
        String playerName = args[1];
        Player p = Bukkit.getPlayer(playerName);

        if (p == null) {
            PEMessages.send(s, "invalid_player");
            return false;
        }

        if (args.length == 2) {
            PEMessages.send(s, "specify_mode");
            return false;
        }
        String mode = args[2].toLowerCase();

        if (!mode.equals("add") && !mode.equals("remove") && !mode.equals("set")) {
            PEMessages.send(s, "invalid_mode");
            return false;
        }

        if (args.length == 3) {
            PEMessages.send(s, "specify_enchant");
            return false;
        }

        String identifier = args[3].toLowerCase();
        PEEnchantRegistry registry = PrisonEnchants.INSTANCE.getPeEnchantRegistry();
        if (!registry.contains(identifier)) {
            PEMessages.send(s, "invalid_enchant");
            return false;
        }

        PEEnchant enchant = registry.get(identifier);
        int level = 1;

        if (args.length > 4) {
            String n = args[4];
            if (PEUtils.isInvalidNumber(n, s)) return false;
            level = Integer.parseInt(n);
        }
        boolean bypass = args.length > 5 && args[5].toLowerCase().contains("true"), silent = args.length > 6 && args[6].toLowerCase().contains("true");

        switch (mode) {
            case "add":
                if (!silent) PEMessages.send(s, "%prefix% &3Successfully forced &b" + p.getName() + " &3to add &b" + level + " &3level of &b" + identifier + "&3.", true);
                PEEnchanter.addEnchantLevels(p, enchant, level, bypass);
                break;
            case "remove":
                if (!silent) PEMessages.send(s, "%prefix% &3Successfully forced &b" + p.getName() + " &3to remove &b" + level + " &3level of &b" + identifier + "&3.", true);
                PEEnchanter.removeEnchantLevels(p, enchant, level);
                break;
            case "set":
                if (!silent) PEMessages.send(s, "%prefix% &3Successfully forced &b" + p.getName() + " &3to set &b" + identifier + " &3to level &b" + level + "&3.", true);
                PEEnchanter.setEnchantLevels(p, enchant, level);
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        if (args.length == 2)
            return PEArgs.getPlayerArgs(args);

        if (args.length == 3)
            return PEArgs.getArgs(args, "add", "remove", "set");

        if (args.length == 4)
            return PEArgs.getEnchantmentArgs(args);

        if (args.length == 5)
            return PEArgs.getArgs(args, "1", "2", "3");

        if (args.length == 6)
            return PEArgs.getArgs(args, "bypass_cost=true", "bypass_cost=false");

        if (args.length == 7)
            return PEArgs.getArgs(args, "silent=true", "silent=false");

        return null;
    }
}