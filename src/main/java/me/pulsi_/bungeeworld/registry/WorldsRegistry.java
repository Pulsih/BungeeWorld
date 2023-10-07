package me.pulsi_.bungeeworld.registry;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldsRegistry {

    private final HashMap<String, BWWorld> worlds = new HashMap<>();
    private final BungeeWorld plugin;

    public WorldsRegistry(BungeeWorld plugin) {
        this.plugin = plugin;
    }

    public HashMap<String, BWWorld> getWorlds() {
        return worlds;
    }

    public void loadWorlds() {
        worlds.clear();

        File worldFilesFolder = new File(plugin.getDataFolder(), "worlds");
        if (!worldFilesFolder.exists()) worldFilesFolder.getParentFile().mkdirs();

        File[] worldFiles = worldFilesFolder.listFiles();
        if (worldFiles == null || worldFiles.length == 0) return;

        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds())
            worldNames.add(world.getName());

        for (String worldName : worldNames) {
            File worldFile = new File(plugin.getDataFolder(), "worlds" + File.separator + worldName);

            BWWorld world = new BWWorld(worldName);

            if (!worldFile.exists()) {

            }

            setupWorld(world);
        }

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
        if (save) BungeeWorld.INSTANCE.getConfigs().saveConfig(BWConfigs.Type.WORLDS, true);
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
        FileConfiguration config = BungeeWorld.INSTANCE.getConfigs().getConfig(BWConfigs.Type.WORLDS);
        String worldName = world.getName();

        world.setSpawn(config.getString(worldName + ".spawn"));
        world.setTeleportToLastLocation(config.getBoolean(worldName + ".teleport-to-last-location"));
        world.setTeleportToSpawnOnJoin(config.getBoolean(worldName + ".teleport-to-spawn-on-join"));

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

        world.setSecurity(security);

        world.setDenyCommandsMessage(config.getString(worldName + ".denied-commands.deny-message"));
        world.setDenyCommandsStartsWith(config.getStringList(worldName + ".denied-commands.starts-with"));
        world.setDenyCommandsSingle(config.getStringList(worldName + ".denied-commands.single-command"));
        world.setActionsOnJoin(config.getStringList(worldName + ".actions-on-join"));
        world.setActionsOnQuit(config.getStringList(worldName + ".actions-on-quit"));
        world.setActionsOnDeath(config.getStringList(worldName + ".actions-on-death"));
        world.setActionsOnRespawn(config.getStringList(worldName + ".actions-on-respawn"));
        world.setDeathMessage(config.getString(worldName + ".death-message"));
        world.setKillerDeathMessage(config.getString(worldName + ".killer-death-message"));
        world.setKillerWeaponDeathMessage(config.getString(worldName + ".killer-weapon-death-message"));
        world.setJoinMessage(config.getString(worldName + ".join-message"));
        world.setQuitMessage(config.getString(worldName + ".quit-message"));
        world.setLinkedWorlds(config.getStringList(worldName + ".linked-worlds"));
    }

    public void saveAllPlayerStatistics() {

    }

    public void setValue(World world, String path, Object value) {
        BWConfigs BWConfigs = BungeeWorld.INSTANCE.getConfigs();
        FileConfiguration worldsConfig = BWConfigs.getConfig(BWConfigs.Type.WORLDS);

        worldsConfig.set(world.getName() + "." + path, value);
        BWConfigs.saveConfig(BWConfigs.Type.WORLDS, true);
        loadWorlds();
    }
}