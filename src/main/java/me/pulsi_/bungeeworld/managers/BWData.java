package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.commands.HubCmd;
import me.pulsi_.bungeeworld.commands.MainCmd;
import me.pulsi_.bungeeworld.commands.SpawnCmd;
import me.pulsi_.bungeeworld.external.UpdateChecker;
import me.pulsi_.bungeeworld.external.bStats;
import me.pulsi_.bungeeworld.listeners.*;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.utils.BWMessages;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.plugin.PluginManager;

public class BWData {

    private final BungeeWorld plugin;
    
    public BWData(BungeeWorld plugin) {
        this.plugin = plugin;
    }
    
    public void setupPlugin() {
        long startTime = System.currentTimeMillis();
        long time;

        BWLogger.log("");
        BWLogger.log("    %prefix% &2Enabling plugin...");
        BWLogger.log("    &aRunning on version &f" + plugin.getDescription().getVersion() + "&a!");

        time = System.currentTimeMillis();
        new bStats(plugin);
        reloadPlugin();
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

    public void shutdownPlugin() {
        BWLogger.log("");
        BWLogger.log("    %prefix% &cDisabling plugin...");
        BWLogger.log("");
    }

    public boolean reloadPlugin() {
        try {
            plugin.getConfigs().setupConfigs();
            Values.CONFIG.loadValues();
            BWMessages.loadMessages();
            plugin.getWorldsRegistry().loadWorlds();

            ItemManager.loadItems();
            new GuiManager().loadGuis();
            return true;
        } catch (Exception e) {
            BWLogger.warn(e, "Something went wrong while reloading the plugin.");
            return false;
        }
    }

    private void registerCommands() {
        plugin.getCommand("bungeeworld").setExecutor(new MainCmd());
        plugin.getCommand("hub").setExecutor(new HubCmd());
        plugin.getCommand("spawn").setExecutor(new SpawnCmd());
    }

    private void registerEvents() {
        PluginManager plManager = plugin.getServer().getPluginManager();

        plManager.registerEvents(new BlockBreakListener(), plugin);
        plManager.registerEvents(new BlockPlaceListener(), plugin);
        plManager.registerEvents(new CommandListener(), plugin);
        plManager.registerEvents(new DamageListener(), plugin);
        plManager.registerEvents(new EntityDamageListener(), plugin);
        plManager.registerEvents(new EntitySpawnListener(), plugin);
        plManager.registerEvents(new ExplosionListener(), plugin);
        plManager.registerEvents(new GuiListener(), plugin);
        plManager.registerEvents(new PlayerChatListener(), plugin);
        plManager.registerEvents(new PlayerDeathListener(), plugin);
        plManager.registerEvents(new PlayerDropListener(), plugin);
        plManager.registerEvents(new PlayerInteractListener(), plugin);
        plManager.registerEvents(new PlayerJoinListener(plugin), plugin);
        plManager.registerEvents(new PlayerPickupListener(), plugin);
        plManager.registerEvents(new PlayerQuitListener(plugin), plugin);
        plManager.registerEvents(new PlayerRespawnListener(), plugin);
        plManager.registerEvents(new WorldChangeListener(), plugin);
        plManager.registerEvents(new UpdateChecker(plugin), plugin);
    }
}