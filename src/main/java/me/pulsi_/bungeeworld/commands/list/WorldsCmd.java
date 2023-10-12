package me.pulsi_.bungeeworld.commands.list;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.BWCommand;
import me.pulsi_.bungeeworld.registry.BWWorld;
import me.pulsi_.bungeeworld.registry.WorldsRegistry;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWJson;
import me.pulsi_.bungeeworld.utils.BWMessages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldsCmd extends BWCommand {

    private final WorldsRegistry registry;

    public WorldsCmd(String... aliases) {
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
        return false;
    }

    @Override
    public boolean skipUsageWarn() {
        return true;
    }

    @Override
    public boolean onCommand(CommandSender s, String[] args) {
        BWMessages.send(s, "%prefix% &aCurrently registered worlds:", true);
        if (!(s instanceof Player)) for (BWWorld world : registry.getWorlds().values()) BWMessages.send(s, " &8* &a" + world.getName(), true);
        else {
            for (BWWorld world : registry.getWorlds().values()) {
                BWJson worldName = new BWJson("&8* &a" + world.getName());

                TextComponent tpButton = new BWJson(" &b[CLICK TO TP]")
                        .setClickAction(ClickEvent.Action.RUN_COMMAND, "/bungeeworld tp " + world.getName())
                        .setHoverAction(HoverEvent.Action.SHOW_TEXT, "Click here to teleport to that world!")
                        .getText();

                worldName.addText(tpButton).send((Player) s);
            }
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender s, String[] args) {
        return null;
    }
}