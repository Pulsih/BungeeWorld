package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.BWLogger;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class BWMessages {

    private static final Map<String, List<String>> messages = new HashMap<>();

    public static void send(Player p, String identifier) {
        if (!messages.containsKey(identifier)) return;

        List<String> listOfMessages = messages.get(identifier);
        if (listOfMessages.isEmpty()) {
            BWLogger.error("The message \"" + identifier + "\" is missing in the messages file!");
            return;
        }

        for (String message : listOfMessages) p.sendMessage(BWChat.color(message.replace("%prefix%", getPrefix())));
    }

    public static void send(Player p, String identifier, String... stringsToReplace) {
        if (!messages.containsKey(identifier)) return;

        List<String> listOfMessages = messages.get(identifier);
        if (listOfMessages.isEmpty()) {
            BWLogger.error("The message \"" + identifier + "\" is missing in the messages file!");
            return;
        }

        for (String message : listOfMessages) {
            for (String stringToReplace : stringsToReplace) {
                if (!stringToReplace.contains("$")) continue;
                String oldChar = stringToReplace.split("\\$")[0];
                String replacement = stringToReplace.split("\\$")[1];
                message = message.replace(oldChar, replacement);
            }
            p.sendMessage(BWChat.color(message.replace("%prefix%", getPrefix())));
        }
    }

    public static void send(CommandSender s, String identifier) {
        if (!messages.containsKey(identifier)) return;

        List<String> listOfMessages = messages.get(identifier);
        if (listOfMessages.isEmpty()) {
            BWLogger.error("The message \"" + identifier + "\" is missing in the messages file!");
            return;
        }

        for (String message : listOfMessages) s.sendMessage(BWChat.color(message.replace("%prefix%", getPrefix())));
    }

    public static void send(CommandSender s, String identifier, String... stringsToReplace) {
        if (!messages.containsKey(identifier)) return;

        List<String> listOfMessages = messages.get(identifier);
        if (listOfMessages.isEmpty()) {
            BWLogger.error("The message \"" + identifier + "\" is missing in the messages file!");
            return;
        }

        for (String message : listOfMessages) {
            for (String stringToReplace : stringsToReplace) {
                if (!stringToReplace.contains("$")) continue;
                String oldChar = stringToReplace.split("\\$")[0];
                String replacement = stringToReplace.split("\\$")[1];
                message = message.replace(oldChar, replacement);
            }
            s.sendMessage(BWChat.color(message.replace("%prefix%", getPrefix())));
        }
    }

    public static void loadMessages() {
        messages.clear();
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(ConfigManager.Type.MESSAGES);
        Set<String> paths = config.getConfigurationSection("").getKeys(false);
        for (String path : paths) {
            List<String> listOfMessages = config.getStringList(path);
            if (listOfMessages.isEmpty()) {
                String message = config.getString(path);
                messages.put(path, Collections.singletonList(message));
                continue;
            }
            messages.put(path, config.getStringList(path));
        }
    }

    private static String getPrefix() {
        if (!messages.containsKey("prefix")) return BWChat.prefix;
        List<String> prefix = messages.get("prefix");
        if (prefix.isEmpty()) return BWChat.prefix;
        return prefix.get(0);
    }
}