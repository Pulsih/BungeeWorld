package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigManager {

    private int commentsCount = 0;
    private int spacesCount = 0;
    private final String commentIdentifier = "bungeeworld_comment";
    private final String spaceIdentifier = "bungeeworld_space";
    private File configFile, guisFile, itemsFile, messagesFile, worldsFile;
    private FileConfiguration config, guisConfig, itemsConfig, messagesConfig, worldsConfig;

    public enum Type {
        CONFIG,
        GUIS,
        ITEMS,
        MESSAGES,
        WORLDS
    }

    private final BungeeWorld plugin;

    public ConfigManager(BungeeWorld plugin) {
        this.plugin = plugin;
    }

    public void createConfigs() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        guisFile = new File(plugin.getDataFolder(), "guis.yml");
        itemsFile = new File(plugin.getDataFolder(), "items.yml");
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        worldsFile = new File(plugin.getDataFolder(), "worlds.yml");

        if (!configFile.exists()) plugin.saveResource("config.yml", false);
        if (!itemsFile.exists()) plugin.saveResource("items.yml", false);
        if (!messagesFile.exists()) plugin.saveResource("messages.yml", false);
        if (!guisFile.exists()) plugin.saveResource("guis.yml", false);
        if (!worldsFile.exists()) plugin.saveResource("worlds.yml", false);

        config = new YamlConfiguration();
        guisConfig = new YamlConfiguration();
        itemsConfig = new YamlConfiguration();
        messagesConfig = new YamlConfiguration();
        worldsConfig = new YamlConfiguration();

        buildConfig();
        buildMessages();
    }

    public FileConfiguration getConfig(Type type) {
        switch (type) {
            case CONFIG:
                return config;
            case ITEMS:
                return itemsConfig;
            case MESSAGES:
                return messagesConfig;
            case GUIS:
                return guisConfig;
            case WORLDS:
                return worldsConfig;
            default:
                return null;
        }
    }

    public void reloadConfig(Type type) {
        switch (type) {
            case CONFIG:
                try {
                    config.load(configFile);
                } catch (InvalidConfigurationException | IOException e) {
                    BWLogger.error(e.getMessage());
                }
                break;

            case ITEMS:
                try {
                    itemsConfig.load(itemsFile);
                } catch (InvalidConfigurationException | IOException e) {
                    BWLogger.error(e.getMessage());
                }
                break;

            case MESSAGES:
                try {
                    messagesConfig.load(messagesFile);
                } catch (InvalidConfigurationException | IOException e) {
                    BWLogger.error(e.getMessage());
                }
                break;

            case GUIS:
                try {
                    guisConfig.load(guisFile);
                } catch (InvalidConfigurationException | IOException e) {
                    BWLogger.error(e.getMessage());
                }
                break;

            case WORLDS:
                try {
                    worldsConfig.load(worldsFile);
                } catch (InvalidConfigurationException | IOException e) {
                    BWLogger.error(e.getMessage());
                }
                break;
        }
    }

    public void saveConfig(Type type, boolean async) {
        switch (type) {
            case CONFIG:
                saveConfig(config, configFile, async);
                break;

            case GUIS:
                saveConfig(guisConfig, guisFile, async);
                break;

            case ITEMS:
                saveConfig(itemsConfig, itemsFile, async);
                break;

            case MESSAGES:
                saveConfig(messagesConfig, messagesFile, async);
                break;

            case WORLDS:
                saveConfig(worldsConfig, worldsFile, async);
                break;
        }
    }

    private void saveConfig(FileConfiguration c, File f, boolean async) {
        if (async) {
            try {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    try {
                        c.save(f);
                    } catch (IOException e) {
                        BWLogger.error(e.getMessage());
                    }
                });
            } catch (Exception ex) {
                try {
                    c.save(f);
                } catch (IOException e) {
                    BWLogger.error(e.getMessage());
                }
            }
        } else {
            try {
                c.save(f);
            } catch (IOException e) {
                BWLogger.error(e.getMessage());
            }
        }
    }

    public void buildConfig() {
        reloadConfig(Type.CONFIG);
        File newConfigFile = new File(plugin.getDataFolder(), "config.yml");
        YamlConfiguration newConfig = new YamlConfiguration();
        addComments(newConfig,
                "Configuration File of BungeeWorld",
                "Made by Pulsi_, Version v" + plugin.getDescription().getVersion());
        addSpace(newConfig);

        addComments(newConfig, "Check for new updates of the plugin.");
        validatePath(config, newConfig, "update-checker", true);
        addSpace(newConfig);

        addComments(newConfig, "The hub settings.");
        addCommentsUnder(newConfig, "hub", "Here you can define the hub spawn and world");
        validatePath(config, newConfig, "hub.name", "SetName Using /bw sethub");
        validatePath(config, newConfig, "hub.spawn", "SetSpawn Using /bw sethub");
        addSpace(newConfig, "hub");
        
        addCommentsUnder(newConfig, "hub", "Choose if a player will be teleported", "to the hub spawn when joining.");
        validatePath(config, newConfig, "hub.teleport-when-join", true);
        addSpace(newConfig, "hub");

        addCommentsUnder(newConfig, "hub", "The sound played when teleporting to the hub.", "( SOUND_TYPE VOLUME PITCH )");
        validatePath(config, newConfig, "hub.teleport-sound", "ENTITY_ENDERMAN_TELEPORT 5 1");
        addSpace(newConfig);

        addComments(newConfig, "The actions that will be executed when a player join.");
        validatePath(config, newConfig, "join-settings.title.send", true);
        addCommentsUnder(newConfig, "join-settings.title", "Use the , to separate title from subtitle.");
        validatePath(config, newConfig, "join-settings.title.text", "&2&lWelcome!,&7Have fun &a&n%player%&7!");
        addSpace(newConfig, "join-settings");

        addCommentsUnder(newConfig, "join-settings", "Play a sound then the player joins.");
        validatePath(config, newConfig, "join-settings.sound.play", true);
        validatePath(config, newConfig, "join-settings.sound.sound-type", "ENTITY_PLAYER_LEVELUP 5 1");
        addSpace(newConfig);

        addCommentsUnder(newConfig, "server-settings", "Enable to deny to players commands", "like \"/plugin:command\"");
        validatePath(config, newConfig, "server-settings.deny-dots-commands", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "Choose if the chat is divided world-by-world", "or just use the normal server chat.");
        validatePath(config, newConfig, "server-settings.isolate-chat", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "This will separate potion effects between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-effects", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "This will separate the enderchests between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-enderchests", true);
        addSpace(newConfig, "server-settings");
        
        addCommentsUnder(newConfig, "server-settings", "This will separate the gamemode between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-gamemode", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "This will separate the health between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-health", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "This will separate the hunger between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-hunger", true);
        addSpace(newConfig, "server-settings");
        
        addCommentsUnder(newConfig, "server-settings", "This will separate inventory between worlds.");
        validatePath(config, newConfig, "server-settings.isolate-inventories", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "Enable this option to save the player", "stats every time he switch world.", "This is used to avoid any data lost in", "case the server crash. (Not Recommended)");
        validatePath(config, newConfig, "server-settings.save-statistics-on-world-change", false);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings", "Clear the player chat when switching world.");
        validatePath(config, newConfig, "server-settings.clear-chat", true);
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings",
                "Warning! Edit this value in worlds.yml,",
                "this is used to register a new world with",
                "that messages instead of a null one.",
                "- The message showed when a player die.");
        validatePath(config, newConfig, "server-settings.death-message", "&a%player% &cdied.");
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings",
                "Warning! Edit this value in worlds.yml,",
                "this is used to register a new world with",
                "that messages instead of a null one.",
                "- The message showed when a player gets",
                "  killed from another one.");
        validatePath(config, newConfig, "server-settings.killer-death-message", "&a%player% &chas been killed by &a%killer%");
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings",
                "Warning! Edit this value in worlds.yml,",
                "this is used to register a new world with",
                "that messages instead of a null one.",
                "- The message showed when a player gets",
                "  killed from another one with a weapon.");
        validatePath(config, newConfig, "server-settings.killer-weapon-death-message", "&a%player% &chas been killed by &a%killer% &cwith %item%");
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings",
                "Warning! Edit this value in worlds.yml,",
                "this is used to register a new world with",
                "that messages instead of a null one.",
                "- Message showed when a player join",
                "the server / another world.");
        validatePath(config, newConfig, "server-settings.join-message", "&8[&a+&8] &a%player%");
        addSpace(newConfig, "server-settings");

        addCommentsUnder(newConfig, "server-settings",
                "Warning! Edit this value in worlds.yml,",
                "this is used to register a new world with",
                " that messages instead of a null one.",
                "- Message showed when a player quit",
                "the server / another world.");
        validatePath(config, newConfig, "server-settings.quit-message", "&8[&c-&8] &a%player%");
        addSpace(newConfig);

        saveConfig(newConfig, newConfigFile, false);
        recreateFile(newConfigFile);
    }

    public void buildMessages() {
        reloadConfig(Type.MESSAGES);
        File newMessagesFile = new File(plugin.getDataFolder(), "messages.yml");
        FileConfiguration newMessages = new YamlConfiguration();

        addComments(newMessages,
                "Messages File of BungeeWorld",
                "Made by Pulsi_, Version v" + plugin.getDescription().getVersion());
        addSpace(newMessages);

        addComments(newMessages, "The main prefix.");
        validatePath(messagesConfig, newMessages, "prefix", "&2&lBungee&a&lWorld");
        addSpace(newMessages);

        addComments(newMessages,
                "You can use a message as single or multiple:",
                "MessageIdentifier: \"A single message\"",
                "MessageIdentifier:",
                "  - \"Multiple messages\"",
                "  - \"in just one! :)\"");
        addSpace(newMessages);

        addComments(newMessages, "Plugin Messages.");
        validatePath(messagesConfig, newMessages, "plugin_reloaded", "%prefix% &aPlugin reloaded!");
        validatePath(messagesConfig, newMessages, "hub_set", "%prefix% &aSuccessfully set the hub!");
        validatePath(messagesConfig, newMessages, "spawn_set", "%prefix% &aSuccessfully set the world spawn!");
        validatePath(messagesConfig, newMessages, "teleported_hub", "%prefix% &aYou have been teleported to the hub!");
        validatePath(messagesConfig, newMessages, "teleported_spawn", "%prefix% &aYou have been teleported to the spawn!");
        validatePath(messagesConfig, newMessages, "item_given", "%prefix% &aSuccessfully given %item% to %player%!");
        validatePath(messagesConfig, newMessages, "player_sent", "%prefix% &aSuccessfully sent %player% to %world%!");
        validatePath(messagesConfig, newMessages, "player_tp", "%prefix% &aTeleported to %world%!");
        validatePath(messagesConfig, newMessages, "setting_updated", "%prefix% &aSuccessfully updated the setting!");
        addSpace(newMessages);

        addComments(newMessages, "Plugin Errors.");
        validatePath(messagesConfig, newMessages, "unknown_command", "%prefix% &cUnknown command!");
        validatePath(messagesConfig, newMessages, "no_permission", "%prefix% &cYou don' have the permission!");
        validatePath(messagesConfig, newMessages, "not_player", "%prefix% &cYou are not a player!");
        validatePath(messagesConfig, newMessages, "spawn_not_set", "%prefix% &cThe spawn has not been set yet!");
        validatePath(messagesConfig, newMessages, "cannot_find_hub", "%prefix% &cCannot find the hub spawn!");
        validatePath(messagesConfig, newMessages, "already_at_hub", "%prefix% &cYou are already at the hub!");
        validatePath(messagesConfig, newMessages, "specify_player", "%prefix% &cPlease specify a player!");
        validatePath(messagesConfig, newMessages, "invalid_player", "%prefix% &cPlease choose a valid player!");
        validatePath(messagesConfig, newMessages, "specify_item", "%prefix% &cPlease specify an item!");
        validatePath(messagesConfig, newMessages, "invalid_item", "%prefix% &cPlease choose a valid item!");
        validatePath(messagesConfig, newMessages, "invalid_number", "%prefix% &cPlease choose a valid number!");
        validatePath(messagesConfig, newMessages, "specify_world", "%prefix% &cPlease specify a world!");
        validatePath(messagesConfig, newMessages, "invalid_world", "%prefix% &cPlease choose a valid world!");
        validatePath(messagesConfig, newMessages, "invalid_gui", "%prefix% &cPlease choose a valid gui!");
        validatePath(messagesConfig, newMessages, "specify_setting", "%prefix% &cPlease specify a setting!");
        validatePath(messagesConfig, newMessages, "invalid_setting", "%prefix% &cPlease choose a valid setting!");
        validatePath(messagesConfig, newMessages, "specify_value", "%prefix% &cPlease specify a value!");
        validatePath(messagesConfig, newMessages, "invalid_format", "%prefix% &cYou can't use this command format!");
        addSpace(newMessages);

        saveConfig(newMessages, newMessagesFile, false);
        recreateFile(newMessagesFile);
    }

    private void recreateFile(File file) {
        String configuration = getFileAsString(file);
        if (configuration == null) return;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(configuration);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            BWLogger.error(e.getMessage());
        }
    }

    private String getFileAsString(File file) {
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) lines.add(scanner.nextLine());
        } catch (FileNotFoundException e) {
            BWLogger.error(e.getMessage());
            return null;
        }

        StringBuilder config = new StringBuilder();
        for (String line : lines) {
            if (line.contains(commentIdentifier)) {
                int from = line.indexOf(commentIdentifier);
                int to = from + commentIdentifier.length();

                int pointsPosition = line.indexOf(":");
                int numbersLength = pointsPosition - to;
                String identifier = line.substring(from, to + numbersLength);

                line = line.replace(identifier, "").replaceFirst(":", "#");

                String comment = line.split("# ")[1];
                if (comment.equals("''")) {
                    line = line.split("# ")[0] + "#";
                } else if (comment.startsWith("'") && comment.endsWith("'")) {
                    int firstAccent = comment.indexOf("'");
                    int lastAccent = comment.lastIndexOf("'");

                    String newComment = comment.substring(firstAccent + 1, lastAccent);
                    line = line.split("# ")[0] + "# " + newComment;
                }
            }
            if (line.contains(spaceIdentifier)) line = "";

            config.append(line).append("\n");
        }

        return config.toString();
    }

    private void addSpace(FileConfiguration config) {
        config.set(spaceIdentifier + spacesCount, "");
        spacesCount++;
    }

    private void addSpace(FileConfiguration config, String path) {
        config.set(path + "." + spaceIdentifier + spacesCount, "");
        spacesCount++;
    }

    private void addComments(FileConfiguration config, String... comments) {
        for (String comment : comments) {
            config.set(commentIdentifier + commentsCount, comment);
            commentsCount++;
        }
    }

    private void addCommentsUnder(FileConfiguration config, String path, String... comments) {
        for (String comment : comments) {
            config.set(path + "." + commentIdentifier + commentsCount, comment);
            commentsCount++;
        }
    }

    private void validatePath(FileConfiguration from, FileConfiguration to, String path, Object valuePath) {
        Object value = from.get(path);
        if (value == null) to.set(path, valuePath);
        else to.set(path, value);
    }
}