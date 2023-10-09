package me.pulsi_.bungeeworld.registry;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.managers.BWConfigs;
import me.pulsi_.bungeeworld.utils.BWLogger;
import me.pulsi_.bungeeworld.utils.BWUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
        for (World world : Bukkit.getWorlds()) worldNames.add(world.getName());

        for (String worldName : worldNames) {
            BWWorld world = new BWWorld(worldName);
            setupWorld(world);
            worlds.put(worldName, world);
        }
    }

    public void registerWorld(BWWorld world) {
        FileConfiguration worldConfig = new YamlConfiguration(), config = plugin.getConfigs().getConfig(BWConfigs.Type.CONFIG);

        setFromConfig(worldConfig, config, "spawn");
        setFromConfig(worldConfig, config, "teleport-to-last-location");
        setFromConfig(worldConfig, config, "teleport-to-spawn-on-join");
        setFromConfig(worldConfig, config, "security.deny-message");
        setFromConfig(worldConfig, config, "security.disable-block-place");
        setFromConfig(worldConfig, config, "security.disable-block-break");
        setFromConfig(worldConfig, config, "security.disable-block-break");
        setFromConfig(worldConfig, config, "security.disable-mob-spawning");
        setFromConfig(worldConfig, config, "security.disable-explosions");
        setFromConfig(worldConfig, config, "security.disable-player-actions");
        setFromConfig(worldConfig, config, "security.disable-players-drops");
        setFromConfig(worldConfig, config, "security.disable-players-pickup");
        setFromConfig(worldConfig, config, "security.disable-fall-damage");
        setFromConfig(worldConfig, config, "security.disable-pvp");
        setFromConfig(worldConfig, config, "denied-commands.deny-message");
        setFromConfig(worldConfig, config, "denied-commands.starts-with");
        setFromConfig(worldConfig, config, "denied-commands.single-command");
        setFromConfig(worldConfig, config, "actions-on-join");
        setFromConfig(worldConfig, config, "actions-on-quit");
        setFromConfig(worldConfig, config, "actions-on-death");
        setFromConfig(worldConfig, config, "actions-on-respawn");
        setFromConfig(worldConfig, config, "death-message");
        setFromConfig(worldConfig, config, "killer-death-message");
        setFromConfig(worldConfig, config, "killer-weapon-death-message");
        setFromConfig(worldConfig, config, "join-message");
        setFromConfig(worldConfig, config, "quit-message");
        setFromConfig(worldConfig, config, "linked-worlds");

        try {
            worldConfig.save(plugin.getConfigs().getFile("worlds" + File.separator + world.getName()));
        } catch (IOException e) {
            BWLogger.warn(e, "Could not save file changes to the file \"" + world.getName() + "\".");
        }
        setupWorld(world, worldConfig);
    }

    public void setupWorld(BWWorld world) {
        setupWorld(world, plugin.getConfigs().getConfig("worlds" + File.separator + world.getName() + ".yml"));
    }

    public void setupWorld(BWWorld world, FileConfiguration config) {
        if (config == null) {
            registerWorld(world);
            return;
        }

        world.setSpawn(BWUtils.getLocation(config.getString("spawn")));
        world.setTeleportToLastLocation(config.getBoolean("teleport-to-last-location"));
        world.setTeleportToSpawnOnJoin(config.getBoolean("teleport-to-spawn-on-join"));

        BWSecurity security = new BWSecurity();
        security.denyMessage = config.getString("security.deny-message");
        security.disabledBlockPlace = config.getBoolean("security.disable-block-place");
        security.disabledBlockBreak = config.getBoolean("security.disable-block-break");
        security.disabledMobSpawning = config.getBoolean("security.disable-mob-spawning");
        security.disabledExplosions = config.getBoolean("security.disable-explosions");
        security.disabledPlayerActions = config.getBoolean("security.disable-player-actions");
        security.disabledPlayerDrops = config.getBoolean("security.disable-players-drops");
        security.disabledPlayerPickup = config.getBoolean("security.disable-players-pickup");
        security.disabledFallDamage = config.getBoolean("security.disable-fall-damage");
        security.disabledPvP = config.getBoolean("security.disable-pvp");

        world.setSecurity(security);

        world.setDenyCommandsMessage(config.getString("denied-commands.deny-message"));
        world.setDenyCommandsStartsWith(config.getStringList("denied-commands.starts-with"));
        world.setDenyCommandsSingle(config.getStringList("denied-commands.single-command"));
        world.setActionsOnJoin(config.getStringList("actions-on-join"));
        world.setActionsOnQuit(config.getStringList("actions-on-quit"));
        world.setActionsOnDeath(config.getStringList("actions-on-death"));
        world.setActionsOnRespawn(config.getStringList("actions-on-respawn"));
        world.setDeathMessage(config.getString("death-message"));
        world.setKillerDeathMessage(config.getString("killer-death-message"));
        world.setKillerWeaponDeathMessage(config.getString("killer-weapon-death-message"));
        world.setJoinMessage(config.getString("join-message"));
        world.setQuitMessage(config.getString("quit-message"));
        world.setLinkedWorlds(config.getStringList("linked-worlds"));
    }

    public void loadAllPlayerStatistics(Player p) {
        FileConfiguration config = plugin.getConfigs().getConfig("playerData" + File.separator + p.getUniqueId() + ".yml");

        for (BWWorld world : worlds.values()) {
            String worldName = world.getName();
            BWPlayer player = new BWPlayer();

            if (Values.CONFIG.isIsolateInventories()) player.setInventory((ItemStack[]) config.get(worldName + ".inventory"));
            if (Values.CONFIG.isIsolateEnderchests()) player.setEnderChest((ItemStack[]) config.get(worldName + ".enderchest"));
            if (Values.CONFIG.isIsolateEffects()) player.setEffects((Collection<PotionEffect>) config.get(worldName + ".effects"));
            if (Values.CONFIG.isIsolateGamemode()) player.setGameMode(GameMode.valueOf(config.getString(worldName + ".gamemode")));
            if (Values.CONFIG.isIsolateHealth()) {
                player.setHealth(config.getInt(worldName + ".health"));
                player.setMaxHealth(config.getInt(worldName + ".max-health"));
            }
            if (Values.CONFIG.isIsolateHunger()) player.setHunger(config.getInt(worldName + ".hunger"));

            world.getPlayers().put(p.getUniqueId(), player);
        }
    }

    public void saveAllPlayerStatistics(Player p) {
        File file = new File(plugin.getDataFolder(), "playerData" + File.separator + p.getUniqueId() + ".yml");
        FileConfiguration config = plugin.getConfigs().getConfig(file);

        for (BWWorld world : worlds.values()) {
            String worldName = world.getName();
            BWPlayer player = world.getPlayers().get(p.getUniqueId());
            if (player == null) continue;

            config.set(worldName + ".gamemode", player.getGameMode().name());
            config.set(worldName + ".health", player.getHealth());
            config.set(worldName + ".max-health", player.getMaxHealth());
            config.set(worldName + ".hunger", player.getHunger());
            config.set(worldName + ".last-location", BWUtils.getStringLocation(player.getLastLocation()));
            config.set(worldName + ".inventory", player.getInventory());
            config.set(worldName + ".enderchest", player.getEnderChest());
            config.set(worldName + ".effects", player.getEffects());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            BWLogger.warn(e, "Something went wrong while trying to save player statistics to " + p.getName() + "'s file.");
        }
    }

    public void saveWorldSettings(BWWorld world) {
        File file = new File(plugin.getDataFolder(), "worlds" + File.separator + world.getName() + ".yml");
        FileConfiguration config = plugin.getConfigs().getConfig(file);

        config.set("spawn", BWUtils.getStringLocation(world.getSpawn()));
        config.set("teleport-to-last-location", world.isTeleportToLastLocation());
        config.set("teleport-to-spawn-on-join", world.isTeleportToSpawnOnJoin());
        config.set("security.deny-message", world.getSecurity().denyMessage);
        config.set("security.disable-block-place", world.getSecurity().disabledBlockBreak);
        config.set("security.disable-block-break", world.getSecurity().disabledBlockBreak);
        config.set("security.disable-mob-spawning", world.getSecurity().disabledMobSpawning);
        config.set("security.disable-explosions", world.getSecurity().disabledExplosions);
        config.set("security.disable-player-actions", world.getSecurity().disabledPlayerActions);
        config.set("security.disable-players-drops", world.getSecurity().disabledPlayerDrops);
        config.set("security.disable-players-pickup", world.getSecurity().disabledPlayerPickup);
        config.set("security.disable-fall-damage", world.getSecurity().disabledFallDamage);
        config.set("security.disable-pvp", world.getSecurity().disabledPvP);
        config.set("denied-commands.deny-message", world.getDenyCommandsMessage());
        config.set("denied-commands.starts-with", world.getDenyCommandsStartsWith());
        config.set("denied-commands.single-command", world.getDenyCommandsSingle());
        config.set("actions-on-join", world.getActionsOnJoin());
        config.set("actions-on-quit", world.getActionsOnQuit());
        config.set("actions-on-death", world.getActionsOnDeath());
        config.set("actions-on-respawn", world.getActionsOnRespawn());
        config.set("death-message", world.getDeathMessage());
        config.set("killer-death-message", world.getKillerDeathMessage());
        config.set("killer-weapon-death-message", world.getKillerWeaponDeathMessage());
        config.set("join-message", world.getJoinMessage());
        config.set("quit-message", world.getQuitMessage());
        config.set("linked-worlds", world.getLinkedWorlds());

        plugin.getConfigs().save(config, file);
    }

    public void unloadPlayerFromWorlds(Player p) {
        for (BWWorld world : worlds.values()) world.getPlayers().remove(p.getUniqueId());
    }

    private void setFromConfig(FileConfiguration worldConfig, FileConfiguration config, String path) {
        worldConfig.set(path, config.get("default-world-config." + path));
    }
}