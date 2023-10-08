package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.prisonenchants.PrisonEnchants;
import me.pulsi_.prisonenchants.commands.PECommand;
import me.pulsi_.prisonenchants.enchantSystem.PEEnchantRegistry;
import me.pulsi_.prisonenchants.enchantSystem.enchanter.PEEnchanter;
import me.pulsi_.prisonenchants.enchantSystem.enchants.PEEnchant;
import me.pulsi_.prisonenchants.utils.PEArgs;
import me.pulsi_.prisonenchants.utils.PEMessages;
import me.pulsi_.prisonenchants.utils.PEUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EnchantCmd extends PECommand {

    public EnchantCmd(String... aliases) {
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
        return "/pe enchant [add/remove/set] [enchantment] <level> <bypass_cost=true/false>";
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
        return false;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        String mode = args[1].toLowerCase();

        if (!mode.equals("add") && !mode.equals("remove") && !mode.equals("set")) {
            PEMessages.send(s, "invalid_mode");
            return false;
        }

        if (args.length == 2) {
            PEMessages.send(s, "specify_enchant");
            return false;
        }

        String identifier = args[2].toLowerCase();
        PEEnchantRegistry registry = PrisonEnchants.INSTANCE.getPeEnchantRegistry();
        if (!registry.contains(identifier)) {
            PEMessages.send(s, "invalid_enchant");
            return false;
        }

        Player p = (Player) s;
        PEEnchant enchant = registry.get(identifier);
        int level = 1;

        if (args.length > 3) {
            String n = args[3];
            if (PEUtils.isInvalidNumber(n, s)) return false;
            level = Integer.parseInt(n);
        }
        boolean bypass = args.length > 4 && args[4].toLowerCase().contains("true");

        switch (mode) {
            case "add":
                PEEnchanter.addEnchantLevels(p, enchant, level, bypass);
                break;
            case "remove":
                PEEnchanter.removeEnchantLevels(p, enchant, level);
                break;
            case "set":
                PEEnchanter.setEnchantLevels(p, enchant, level);
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        if (args.length == 2)
            return PEArgs.getArgs(args, "add", "remove", "set");

        if (args.length == 3)
            return PEArgs.getEnchantmentArgs(args);

        if (args.length == 4)
            return PEArgs.getArgs(args, "1", "2", "3");

        if (args.length == 5)
            return PEArgs.getArgs(args, "bypass_cost=true", "bypass_cost=false");

        return null;
    }
}