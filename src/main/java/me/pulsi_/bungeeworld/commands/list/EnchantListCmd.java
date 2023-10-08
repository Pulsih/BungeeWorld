package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.prisonenchants.PrisonEnchants;
import me.pulsi_.prisonenchants.commands.PECommand;
import me.pulsi_.prisonenchants.enchantSystem.PEEnchantRegistry;
import me.pulsi_.prisonenchants.utils.PEMessages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class EnchantListCmd extends PECommand {

    public EnchantListCmd(String... aliases) {
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
        PEEnchantRegistry registry = PrisonEnchants.INSTANCE.getPeEnchantRegistry();
        PEMessages.send(s, "%prefix% &3Currently registered enchantments:", true);
        PEMessages.send(s, "&f" + registry.getServerEnchants().size() + " &3Server &fenchantments &8(&fdefault&8)&f:", true);
        PEMessages.send(s, " &8&l* &3" + format(registry.getServerEnchants(), "&3"), true);
        PEMessages.send(s, "", true);
        PEMessages.send(s, "&f" + registry.getPotionsEnchants().size() + " &dPotion &fenchantments &8(&bcustom&8)&f:", true);
        PEMessages.send(s, " &8&l* &d" + format(registry.getPotionsEnchants(), "&d"), true);
        PEMessages.send(s, "", true);
        PEMessages.send(s, "&f" + registry.getCustomEnchants().size() + " &6Custom &fenchantments &8(&bcustom&8)&f:", true);
        PEMessages.send(s, " &8&l* &6" + format(registry.getCustomEnchants(), "&6"), true);
        return true;
    }

    private String format(List<String> enchants, String enchantColor) {
        return enchants.toString().replace("[", "").replace("]", "&8.").replace(",", "&8," + enchantColor);
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}