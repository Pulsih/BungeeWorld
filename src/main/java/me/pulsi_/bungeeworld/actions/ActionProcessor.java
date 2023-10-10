package me.pulsi_.bungeeworld.actions;

import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ActionProcessor {

    public static void executeActions(Player p, List<String> actions) {
        for (String action : actions) {
            action = action.replace("%player%", p.getName());
            if (playerCommand(p, action)) continue;
            if (consoleCommand(action)) continue;
            if (giveEffects(p, action)) continue;
            if (broadcast(p, action)) continue;
        }
    }

    private static boolean giveEffects(Player p, String action) {
        if (!action.startsWith("[EFFECT]")) return false;
        action = action.replace("[EFFECT] ", "");

        String[] paths = action.split(" ");
        if (paths.length < 3) {
            BWLogger.error("Invalid potion action for \"" + action + "\"");
            return true;
        }

        PotionEffectType effectType = PotionEffectType.getByName(paths[0]);
        if (effectType == null) {
            BWLogger.error("Invalid potion effect for \"" + action + "\"");
            return true;
        }

        try {
            int duration = Integer.parseInt(paths[1]), amplifier = Integer.parseInt(paths[2]);
            PotionEffect effect = new PotionEffect(effectType, duration, amplifier);
            p.addPotionEffect(effect, true);
        } catch (NumberFormatException e) {
            BWLogger.error("Invalid number for the action \"" + action + "\"");
        }
        return true;
    }

    private static boolean consoleCommand(String action) {
        if (!action.startsWith("[CONSOLE]")) return false;
        action = action.replace("[CONSOLE] ", "");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action);
        return true;
    }

    private static boolean playerCommand(Player p, String action) {
        if (!action.startsWith("[PLAYER]")) return false;
        action = action.replace("[PLAYER] ", "");
        p.chat(action);
        return true;
    }

    private static boolean broadcast(Player p, String action) {
        if (!action.startsWith("[BROADCAST]")) return false;
        action = action.replace("[BROADCAST] ", "");

        if (Values.CONFIG.isIsolateChat()) Bukkit.broadcastMessage(BWChat.color(action));
        else for (Player player : p.getWorld().getPlayers()) player.sendMessage(BWChat.color(action));

        return true;
    }
}