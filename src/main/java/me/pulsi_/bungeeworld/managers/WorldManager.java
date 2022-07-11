package me.pulsi_.bungeeworld.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.utils.MapUtils;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class WorldManager {

    private static final HashMap<String, Object> worldObjects = new HashMap<>();

    public static void loadWorldsValues() {
        FileConfiguration worlds = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.WORLDS);
        ConfigurationSection section = worlds.getConfigurationSection("");
        if (section == null) return;

        for (String world : section.getKeys(false)) {
            worldObjects.put(world + ".spawn", worlds.getString(world + ".spawn"));
            worldObjects.put(world + ".chat", worlds.getString(world + ".chat"));
            worldObjects.put(world + ".security.deny-message", worlds.getString(world + ".security.deny-message"));
            worldObjects.put(world + ".security.disable-block-place", worlds.getBoolean(world + ".security.disable-block-place"));
            worldObjects.put(world + ".security.disable-block-break", worlds.getBoolean(world + ".security.disable-block-break"));
            worldObjects.put(world + ".security.disable-mob-spawning", worlds.getBoolean(world + ".security.disable-mob-spawning"));
            worldObjects.put(world + ".security.disable-explosions", worlds.getBoolean(world + ".security.disable-explosions"));
            worldObjects.put(world + ".security.disable-player-actions", worlds.getBoolean(world + ".security.disable-player-actions"));
            worldObjects.put(world + ".security.disable-players-drops", worlds.getBoolean(world + ".security.disable-players-drops"));
            worldObjects.put(world + ".security.disable-players-pickup", worlds.getBoolean(world + ".security.disable-players-pickup"));
            worldObjects.put(world + ".security.disable-players-drops", worlds.getBoolean(world + ".security.disable-players-drops"));
            worldObjects.put(world + ".security.disable-fall-damage", worlds.getBoolean(world + ".security.disable-fall-damage"));
            worldObjects.put(world + ".security.disable-pvp", worlds.getBoolean(world + ".security.disable-pvp"));
            worldObjects.put(world + ".denied-commands.deny-message", worlds.getString(world + ".denied-commands.deny-message"));
            worldObjects.put(world + ".denied-commands.starts-with", worlds.getStringList(world + ".denied-commands.starts-with"));
            worldObjects.put(world + ".denied-commands.single-command", worlds.getStringList(world + ".denied-commands.single-command"));
            worldObjects.put(world + ".actions-on-join", worlds.getStringList(world + ".actions-on-join"));
            worldObjects.put(world + ".actions-on-death", worlds.getStringList(world + ".actions-on-death"));
            worldObjects.put(world + ".actions-on-respawn", worlds.getStringList(world + ".actions-on-respawn"));
            worldObjects.put(world + ".death-message", worlds.getString(world + ".death-message"));
            worldObjects.put(world + ".join-message", worlds.getString(world + ".join-message"));
            worldObjects.put(world + ".quit-message", worlds.getString(world + ".quit-message"));
        }
    }

    public static void registerWorld(World world) {
        ConfigManager configManager = BungeeWorld.getInstance().getConfigs();
        FileConfiguration worldsConfig = configManager.getConfig(ConfigManager.Type.WORLDS);

        String worldName = world.getName();
        ConfigurationSection section = worldsConfig.getConfigurationSection("");
        if (section != null && section.getKeys(false).contains(worldName)) return;

        worldsConfig.set(worldName + ".spawn", "null");
        worldsConfig.set(worldName + ".chat", Values.CONFIG.getDefaultChatFormat());
        worldsConfig.set(worldName + ".security.deny-message", Values.CONFIG.getSecurityDenyMessage());
        worldsConfig.set(worldName + ".security.disable-block-place", Values.CONFIG.isDisableBlockPlace());
        worldsConfig.set(worldName + ".security.disable-block-break", Values.CONFIG.isDisableBlockBreak());
        worldsConfig.set(worldName + ".security.disable-mob-spawning", Values.CONFIG.isDisableMobSpawn());
        worldsConfig.set(worldName + ".security.disable-explosions", Values.CONFIG.isDisableExplosion());
        worldsConfig.set(worldName + ".security.disable-player-actions", Values.CONFIG.isDisableActions());
        worldsConfig.set(worldName + ".security.disable-players-drops", Values.CONFIG.isDisableDrops());
        worldsConfig.set(worldName + ".security.disable-players-pickup", Values.CONFIG.isDisablePickup());
        worldsConfig.set(worldName + ".security.disable-fall-damage", Values.CONFIG.isDisableFall());
        worldsConfig.set(worldName + ".security.disable-pvp", Values.CONFIG.isDisablePvP());
        worldsConfig.set(worldName + ".denied-commands.deny-message", Values.CONFIG.getCommandsDenyMessage());
        worldsConfig.set(worldName + ".denied-commands.starts-with", "[]");
        worldsConfig.set(worldName + ".denied-commands.single-command", "[]");
        worldsConfig.set(worldName + ".actions-on-join", "[]");
        worldsConfig.set(worldName + ".actions-on-death", "[]");
        worldsConfig.set(worldName + ".actions-on-respawn", "[]");
        worldsConfig.set(worldName + ".death-message", Values.CONFIG.getDeathMessage());
        worldsConfig.set(worldName + ".join-message", Values.CONFIG.getJoinMessage());
        worldsConfig.set(worldName + ".quit-message", Values.CONFIG.getQuitMessage());

        configManager.saveConfig(ConfigManager.Type.WORLDS, true);
    }

    public static boolean canBroke(Player p) {
        String path = p.getWorld().getName() + ".security.disable-block-break";
        if (!worldObjects.containsKey(path)) return true;
        if ((boolean) worldObjects.get(path) && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public static boolean canPlace(Player p) {
        String path = p.getWorld().getName() + ".security.disable-block-place";
        if (!worldObjects.containsKey(path)) return true;
        if ((boolean) worldObjects.get(path) && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public static boolean canUse(Player p) {
        String path = p.getWorld().getName() + ".security.disable-player-actions";
        if (!worldObjects.containsKey(path)) return true;
        if ((boolean) worldObjects.get(path) && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public static boolean canDrop(Player p) {
        String path = p.getWorld().getName() + ".security.disable-players-drops";
        if (!worldObjects.containsKey(path)) return true;
        if ((boolean) worldObjects.get(path) && !p.hasPermission("bungeeworld.admin")) {
            denyMessage(p);
            return false;
        }
        return true;
    }

    public static boolean canPickup(Player p) {
        String path = p.getWorld().getName() + ".security.disable-players-pickup";
        if (!worldObjects.containsKey(path)) return true;
        return !(boolean) worldObjects.get(path) || p.hasPermission("bungeeworld.admin");
    }

    public static boolean canPvP(Entity e) {
        String path = e.getWorld().getName() + ".security.disable-pvp";
        if (!worldObjects.containsKey(path)) return true;
        if ((boolean) worldObjects.get(path)) {
            denyMessage(e);
            return false;
        }
        return true;
    }

    public static boolean canSpawnMob(String worldName) {
        String path = worldName + ".security.disable-block-place";
        if (!worldObjects.containsKey(path)) return true;
        return !(boolean) worldObjects.get(path);
    }

    public static boolean canExplode(String worldName) {
        FileConfiguration worlds = BungeeWorld.getInstance().getConfigs().getConfig(ConfigManager.Type.WORLDS);
        return !worlds.getBoolean(worldName + ".security.disable-explosions");
    }

    public static boolean canFallDamage(String worldName) {
        String path = worldName + ".security.disable-fall-damage";
        if (!worldObjects.containsKey(path)) return true;
        return !(boolean) worldObjects.get(path);
    }

    public static void denyMessage(Entity p) {
        String path = p.getWorld().getName() + ".security.deny-message";
        if (!worldObjects.containsKey(path)) return;

        HashMap<UUID, Long> cooldown = MapUtils.denyMessageCooldown;
        if (cooldown.containsKey(p.getUniqueId()) && System.currentTimeMillis() < cooldown.get(p.getUniqueId())) return;

        String denyMessage = (String) worldObjects.get(path);
        p.sendMessage(BWChat.color(denyMessage));
        cooldown.put(p.getUniqueId(), System.currentTimeMillis() + 2000L);
    }

    public static List<String> getActionsOnJoin(Player p) {
        String path = p.getWorld().getName() + ".actions-on-join";
        if (!worldObjects.containsKey(path)) return new ArrayList<>();
        return (List<String>) worldObjects.get(path);
    }

    public static List<String> getActionsOnDeath(Player p) {
        String path = p.getWorld().getName() + ".actions-on-death";
        if (!worldObjects.containsKey(path)) return new ArrayList<>();
        return (List<String>) worldObjects.get(path);
    }

    public static List<String> getActionsOnRespawn(Player p) {
        String path = p.getWorld().getName() + ".actions-on-respawn";
        if (!worldObjects.containsKey(path)) return new ArrayList<>();
        return (List<String>) worldObjects.get(path);
    }

    public static String getDeathMessage(Player p) {
        String path = p.getWorld().getName() + ".death-message";
        if (!worldObjects.containsKey(path)) return null;
        return (String) worldObjects.get(path);
    }

    public static String getJoinMessage(Player p) {
        String path = p.getWorld().getName() + ".join-message";
        if (!worldObjects.containsKey(path)) return null;
        return (String) worldObjects.get(path);
    }

    public static String getQuitMessage(Player p) {
        String path = p.getWorld().getName() + ".quit-message";
        if (!worldObjects.containsKey(path)) return null;
        return (String) worldObjects.get(path);
    }

    public static String getSpawn(Player p) {
        String path = p.getWorld().getName() + ".spawn";
        if (!worldObjects.containsKey(path)) return null;
        return (String) worldObjects.get(path);
    }
}