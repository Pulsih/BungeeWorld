package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.values.Values;
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

    private final BungeeWorld plugin;
    private File configFile, guisFile, itemsFile, messagesFile, worldsFile;
    private FileConfiguration config, guisConfig, itemsConfig, messagesConfig, worldsConfig;

    public enum Type {
        CONFIG,
        GUIS,
        ITEMS,
        MESSAGES,
        WORLDS
    }

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

        reloadConfig(Type.CONFIG);
        reloadConfig(Type.GUIS);
        reloadConfig(Type.ITEMS);
        reloadConfig(Type.MESSAGES);
        reloadConfig(Type.WORLDS);

        buildConfig();
        buildMessages();

        Values.CONFIG.loadValues();
        MessagesManager.loadMessages();
        ItemManager.loadItems();
        WorldManager.loadWorldsValues();
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
                Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> {
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
        File newConfigFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration newConfig = new YamlConfiguration();

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

        addCommentsUnder(newConfig, "hub",
                "Choose if a player will be teleported",
                "to the hub spawn when joining.");
        validatePath(config, newConfig, "hub.teleport-when-join", true);
        addSpace(newConfig, "hub");

        addCommentsUnder(newConfig, "hub",
                "The sound played when teleporting to the hub.",
                "( SOUND_TYPE VOLUME PITCH )");
        validatePath(config, newConfig, "hub.teleport-sound", "ENTITY_ENDERMAN_TELEPORT 5 1");
        addSpace(newConfig);

        addComments(newConfig, "The actions that will be executed when a player join.");
        validatePath(config, newConfig, "join-settings.title.send", true);
        addCommentsUnder(newConfig, "join-settings.title", "Use the , to separate title from subtitle.");
        validatePath(config, newConfig, "join-settings.title.text", "&2&lWelcome!,&7Have fun &a&n%player%&7!");
        addSpace(newConfig);

        addCommentsUnder(newConfig, "server-settings",
                "Choose if the chat is divided world-by-world",
                "or just use the normal server chat.");
        validatePath(config, newConfig, "server-settings.isolate-chat", true);
        addSpace(newConfig);

        addCommentsUnder(newConfig, "default-formats", "The default values that a new registered world will have");
        validatePath(config, newConfig, "default-formats.chat", "&7%player%&8: &7%message%");
        validatePath(config, newConfig, "default-formats.security-deny-message", "&c&lSorry! &7You can't do that here.");
        validatePath(config, newConfig, "default-formats.disable-block-place", false);
        validatePath(config, newConfig, "default-formats.disable-block-break", false);
        validatePath(config, newConfig, "default-formats.disable-mob-spawning", false);
        validatePath(config, newConfig, "default-formats.disable-explosions", false);
        validatePath(config, newConfig, "default-formats.disable-player-actions", false);
        validatePath(config, newConfig, "default-formats.disable-players-drops", false);
        validatePath(config, newConfig, "default-formats.disable-players-pickup", false);
        validatePath(config, newConfig, "default-formats.disable-fall-damage", false);
        validatePath(config, newConfig, "default-formats.disable-pvp", false);
        validatePath(config, newConfig, "default-formats.commands-deny-message", "Unknown command. Type \"/help\" for help.");
        validatePath(config, newConfig, "default-formats.death-message", "&a%player% &fDied.");
        validatePath(config, newConfig, "default-formats.join-message", "null");
        validatePath(config, newConfig, "default-formats.quit-message", "null");
        addSpace(newConfig);

        saveConfig(newConfig, newConfigFile, false);
        recreateFile(newConfigFile);
        reloadConfig(Type.CONFIG);
    }

    public void buildMessages() {
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
        addSpace(newMessages);

        saveConfig(newMessages, newMessagesFile, false);
        recreateFile(newMessagesFile);
        reloadConfig(Type.MESSAGES);
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