package me.pulsi_.bungeeworld.worlds;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.ConfigManager;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldsRegistry {

    private final HashMap<String, BWWorld> worlds = new HashMap<>();

    public HashMap<String, BWWorld> getWorlds() {
        return worlds;
    }

    public void loadWorlds() {
        worlds.clear();

        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(ConfigManager.Type.WORLDS);
        ConfigurationSection section = config.getConfigurationSection("");
        if (section == null) return;

        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
            worldNames.add(world.getName());

        boolean save = false;
        for (String worldName : worldNames) {

            if (!section.getKeys(false).contains(worldName)) {
                registerWorld(worldName, config);
                save = true;
            }

            BWWorld world = new BWWorld(worldName);
            setupWorld(world);

            worlds.put(worldName, world);
        }
        if (save) BungeeWorld.INSTANCE.getConfigs().saveConfig(ConfigManager.Type.WORLDS, true);
    }

    public void registerWorld(String worldName, FileConfiguration config) {
        config.set(worldName + ".spawn", "");
        config.set(worldName + ".teleport-to-last-location", false);
        config.set(worldName + ".teleport-to-spawn-on-join", false);
        config.set(worldName + ".security.deny-message", "&c&lSorry! &7You can't do that here.");
        config.set(worldName + ".security.disable-block-place", false);
        config.set(worldName + ".security.disable-block-break", false);
        config.set(worldName + ".security.disable-mob-spawning", false);
        config.set(worldName + ".security.disable-explosions", false);
        config.set(worldName + ".security.disable-player-actions", false);
        config.set(worldName + ".security.disable-players-drops", false);
        config.set(worldName + ".security.disable-players-pickup", false);
        config.set(worldName + ".security.disable-fall-damage", false);
        config.set(worldName + ".security.disable-pvp", false);
        config.set(worldName + ".denied-commands.deny-message", "Unknown command. Type \"/help\" for help.");
        config.set(worldName + ".denied-commands.starts-with", "[]");
        config.set(worldName + ".denied-commands.single-command", "[]");
        config.set(worldName + ".actions-on-join", "[]");
        config.set(worldName + ".actions-on-quit", "[]");
        config.set(worldName + ".actions-on-death", "[]");
        config.set(worldName + ".actions-on-respawn", "[]");
        config.set(worldName + ".death-message", Values.CONFIG.getDeathMessage1());
        config.set(worldName + ".killer-death-message", Values.CONFIG.getDeathMessage2());
        config.set(worldName + ".killer-weapon-death-message", Values.CONFIG.getDeathMessage3());
        config.set(worldName + ".join-message", Values.CONFIG.getJoinMessage());
        config.set(worldName + ".quit-message", Values.CONFIG.getQuitMessage());
        config.set(worldName + ".linked-worlds", "[]");
    }

    public void setupWorld(BWWorld world) {
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(ConfigManager.Type.WORLDS);
        String worldName = world.name;

        world.spawn = config.getString(worldName + ".spawn");
        world.teleportToLastLocation = config.getBoolean(worldName + ".teleport-to-last-location");
        world.teleportToSpawnOnJoin = config.getBoolean(worldName + ".teleport-to-spawn-on-join");

        BWSecurity security = new BWSecurity();
        security.denyMessage = config.getString(worldName + ".security.deny-message");
        security.disabledBlockPlace = config.getBoolean(worldName + ".security.disable-block-place");
        security.disabledBlockBreak = config.getBoolean(worldName + ".security.disable-block-break");
        security.disabledMobSpawning = config.getBoolean(worldName + ".security.disable-mob-spawning");
        security.disabledExplosions = config.getBoolean(worldName + ".security.disable-explosions");
        security.disabledPlayerActions = config.getBoolean(worldName + ".security.disable-player-actions");
        security.disabledPlayerDrops = config.getBoolean(worldName + ".security.disable-players-drops");
        security.disabledPlayerPickup = config.getBoolean(worldName + ".security.disable-players-pickup");
        security.disabledFallDamage = config.getBoolean(worldName + ".security.disable-fall-damage");
        security.disabledPvP = config.getBoolean(worldName + ".security.disable-pvp");

        world.security = security;

        world.denyCommandsMessage = config.getString(worldName + ".denied-commands.deny-message");
        world.denyCommandsStartsWith = config.getStringList(worldName + ".denied-commands.starts-with");
        world.denyCommandsSingle = config.getStringList(worldName + ".denied-commands.single-command");
        world.actionsOnJoin = config.getStringList(worldName + ".actions-on-join");
        world.actionsOnQuit = config.getStringList(worldName + ".actions-on-quit");
        world.actionsOnDeath = config.getStringList(worldName + ".actions-on-death");
        world.actionsOnRespawn = config.getStringList(worldName + ".actions-on-respawn");
        world.deathMessage = config.getString(worldName + ".death-message");
        world.killerDeathMessage = config.getString(worldName + ".killer-death-message");
        world.killerWeaponDeathMessage = config.getString(worldName + ".killer-weapon-death-message");
        world.joinMessage = config.getString(worldName + ".join-message");
        world.quitMessage = config.getString(worldName + ".quit-message");
        world.linkedWorlds = config.getStringList(worldName + ".linked-worlds");
    }

    public void setValue(World world, String path, Object value) {
        ConfigManager configManager = BungeeWorld.INSTANCE.getConfigs();
        FileConfiguration worldsConfig = configManager.getConfig(ConfigManager.Type.WORLDS);

        worldsConfig.set(world.getName() + "." + path, value);
        configManager.saveConfig(ConfigManager.Type.WORLDS, true);
        loadWorlds();
    }
}