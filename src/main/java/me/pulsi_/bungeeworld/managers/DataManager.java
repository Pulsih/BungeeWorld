package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.HubCmd;
import me.pulsi_.bungeeworld.commands.MainCmd;
import me.pulsi_.bungeeworld.commands.SpawnCmd;
import me.pulsi_.bungeeworld.external.UpdateChecker;
import me.pulsi_.bungeeworld.external.bStats;
import me.pulsi_.bungeeworld.listeners.*;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;

public class DataManager {

    public static void setupPlugin() {
        long startTime = System.currentTimeMillis();
        long time;

        BWLogger.log("");
        BWLogger.log("    &2&lBungee&a&lWorld &2Enabling plugin...");
        BWLogger.log("    &aRunning on version &f" + BungeeWorld.getInstance().getDescription().getVersion() + "&a!");

        time = System.currentTimeMillis();
        new bStats(BungeeWorld.getInstance());
        BungeeWorld.getInstance().getConfigs().createConfigs();
        BWLogger.log("    &aLoaded config files! &8(&3" + (System.currentTimeMillis() - time) + "ms&8)");

        time = System.currentTimeMillis();
        registerEvents();
        BWLogger.log("    &aRegistered events! &8(&3" + (System.currentTimeMillis() - time) + "ms&8)");

        time = System.currentTimeMillis();
        registerCommands();
        BWLogger.log("    &aLoaded plugin commands! &8(&3" + (System.currentTimeMillis() - time) + "ms&8)");
        BWLogger.log("    &aDone! &8(&3" + (System.currentTimeMillis() - startTime) + " total ms&8)");
        BWLogger.log("");
    }

    public static void reloadConfigs() {
        ConfigManager configs = BungeeWorld.getInstance().getConfigs();

        configs.reloadConfig(ConfigManager.Type.CONFIG);
        configs.reloadConfig(ConfigManager.Type.GUIS);
        configs.reloadConfig(ConfigManager.Type.ITEMS);
        configs.reloadConfig(ConfigManager.Type.MESSAGES);
        configs.reloadConfig(ConfigManager.Type.WORLDS);
        Values.CONFIG.loadValues();
        MessagesManager.loadMessages();
        ItemManager.loadItems();
        WorldManager.loadWorldsValues();
        new GuiManager().loadGuis();
        for (World world : Bukkit.getWorlds()) WorldManager.registerWorld(world);
    }

    private static void registerCommands() {
        BungeeWorld plugin = BungeeWorld.getInstance();

        plugin.getCommand("bungeeworld").setExecutor(new MainCmd());
        plugin.getCommand("hub").setExecutor(new HubCmd());
        plugin.getCommand("spawn").setExecutor(new SpawnCmd());
    }

    private static void registerEvents() {
        BungeeWorld plugin = BungeeWorld.getInstance();
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new BlockPlaceListener(), plugin);
        pluginManager.registerEvents(new CommandListener(), plugin);
        pluginManager.registerEvents(new DamageListener(), plugin);
        pluginManager.registerEvents(new EntityDamageListener(), plugin);
        pluginManager.registerEvents(new EntitySpawnListener(), plugin);
        pluginManager.registerEvents(new ExplosionListener(), plugin);
        pluginManager.registerEvents(new GuiListener(), plugin);
        pluginManager.registerEvents(new PlayerChatListener(), plugin);
        pluginManager.registerEvents(new PlayerDropListener(), plugin);
        pluginManager.registerEvents(new PlayerInteractListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerPickupListener(), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(), plugin);
        pluginManager.registerEvents(new PlayerRespawnListener(), plugin);
        pluginManager.registerEvents(new WorldChangeListener(), plugin);
        pluginManager.registerEvents(new UpdateChecker(plugin), plugin);
    }
}