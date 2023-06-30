package me.pulsi_.bungeeworld.actions;

import me.pulsi_.bungeeworld.managers.GuiManager;
import me.pulsi_.bungeeworld.managers.ItemManager;
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
            if (giveCustomItem(action)) continue;
            if (giveEffects(p, action)) continue;
            if (broadcast(p, action)) continue;
            openGui(p, action);
        }
    }

    private static boolean openGui(Player p, String action) {
        if (!action.startsWith("[OPEN_GUI]")) return false;
        new GuiManager().openGui(p, action.replace("[OPEN_GUI] ", ""));
        return true;
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

    private static boolean giveCustomItem(String action) {
        if (!action.startsWith("[GIVE_CUSTOM_ITEM]")) return false;
        action = action.replace("[GIVE_CUSTOM_ITEM] ", "");

        String[] paths = action.split(" ");
        int length = paths.length;

        if (length < 2) {
            BWLogger.error("\"" + action + "\" Is an invalid action!");
            return true;
        }

        Player target = Bukkit.getPlayerExact(paths[0]);
        if (target == null) {
            BWLogger.error("Cannot find the player for the action \"" + action + "\"");
            return true;
        }

        ItemStack item = ItemManager.itemsList.get(paths[1]);
        if (item == null) {
            BWLogger.error("Cannot find the custom item for the action \"" + action + "\"");
            return true;
        }

        Inventory inv = target.getInventory();
        if (length == 2) {
            inv.addItem(item);
            return true;
        }

        String number = paths[2];
        if (!BWUtils.isValidNumber(number)) {
            BWLogger.error("Invalid number for the action \"" + action + "\"");
            return true;
        }
        int slot = Integer.parseInt(number) - 1;

        if (length == 3) {
            if (inv.getItem(slot) == null) inv.setItem(slot, item);
            else inv.addItem(item);
            return true;
        }

        boolean force = Boolean.parseBoolean(paths[3]);
        if (force) inv.setItem(slot, item);
        else {
            if (inv.getItem(slot) == null) inv.setItem(slot, item);
            else inv.addItem(item);
        }
        return true;
    }
}