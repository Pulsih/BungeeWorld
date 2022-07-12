package me.pulsi_.bungeeworld.worldSeparator;

import me.pulsi_.bungeeworld.BungeeWorld;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class Storage {

    public static boolean isRegistered(Player p) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = p.getWorld().getName();

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        return playerWorlds != null && playerWorlds.getKeys(false).contains(worldName);
    }

    public static boolean isRegistered(Player p, String worldName) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        return playerWorlds != null && playerWorlds.getKeys(false).contains(worldName);
    }

    public static boolean isRegistered(Player p, World world) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = world.getName();

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        return playerWorlds != null && playerWorlds.getKeys(false).contains(worldName);
    }

    public static void clearPlayer(Player p) {
        p.getInventory().clear();
        for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
        p.setGameMode(GameMode.SURVIVAL);
    }

    public static void savePlayerStatistics(Player p) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = p.getWorld().getName();
        UUID uuid = p.getUniqueId();

        List<ItemStack> inventory = new ArrayList<>(Arrays.asList(p.getInventory().getContents()));
        Collection<PotionEffect> effects = new ArrayList<>(p.getActivePotionEffects());

        playerData.set(uuid + "." + worldName + ".inventory", inventory);
        playerData.set(uuid + "." + worldName + ".effects", effects);
        playerData.set(uuid + "." + worldName + ".gamemode", p.getGameMode().toString());

        PlayerManager.savePlayerFile(p, true);
    }

    public static void savePlayerStatistics(Player p, World world) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = world.getName();
        UUID uuid = p.getUniqueId();

        List<ItemStack> inventory = new ArrayList<>(Arrays.asList(p.getInventory().getContents()));
        Collection<PotionEffect> effects = new ArrayList<>(p.getActivePotionEffects());

        playerData.set(uuid + "." + worldName + ".inventory", inventory);
        playerData.set(uuid + "." + worldName + ".effects", effects);
        playerData.set(uuid + "." + worldName + ".gamemode", p.getGameMode().toString());

        PlayerManager.savePlayerFile(p, true);
    }

    public static void savePlayerStatistics(Player p, String worldName) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        UUID uuid = p.getUniqueId();

        List<ItemStack> inventory = new ArrayList<>(Arrays.asList(p.getInventory().getContents()));
        Collection<PotionEffect> effects = new ArrayList<>(p.getActivePotionEffects());

        playerData.set(uuid + "." + worldName + ".inventory", inventory);
        playerData.set(uuid + "." + worldName + ".effects", effects);
        playerData.set(uuid + "." + worldName + ".gamemode", p.getGameMode().toString());

        PlayerManager.savePlayerFile(p, true);
    }

    public static void givePlayerStatistics(Player p) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = p.getWorld().getName();
        UUID uuid = p.getUniqueId();

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        if (playerWorlds == null || !playerWorlds.getKeys(false).contains(worldName)) {
            savePlayerStatistics(p, worldName);
            return;
        }

        ItemStack[] content = playerData.getList(uuid + "." + worldName + ".inventory").toArray(new ItemStack[0]);
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> p.getInventory().setContents(content));

        String gamemode = playerData.getString(uuid + "." + worldName + ".gamemode");
        p.setGameMode(GameMode.valueOf(gamemode));

        Collection<PotionEffect> effects = (Collection<PotionEffect>) playerData.getList(uuid + "." + worldName + ".effects");
        if (effects != null && !effects.isEmpty()) p.addPotionEffects(effects);
        else for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
    }

    public static void givePlayerStatistics(Player p, World world) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String worldName = world.getName();
        UUID uuid = p.getUniqueId();

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        if (playerWorlds == null || !playerWorlds.getKeys(false).contains(worldName)) {
            savePlayerStatistics(p, worldName);
            return;
        }

        ItemStack[] content = playerData.getList(uuid + "." + worldName + ".inventory").toArray(new ItemStack[0]);
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> p.getInventory().setContents(content));

        String gamemode = playerData.getString(uuid + "." + worldName + ".gamemode");
        p.setGameMode(GameMode.valueOf(gamemode));

        Collection<PotionEffect> effects = (Collection<PotionEffect>) playerData.getList(uuid + "." + worldName + ".effects");
        if (effects != null && !effects.isEmpty()) p.addPotionEffects(effects);
        else for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
    }

    public static void givePlayerStatistics(Player p, String worldName) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        UUID uuid = p.getUniqueId();

        ConfigurationSection playerWorlds = playerData.getConfigurationSection(p.getUniqueId() + "");
        if (playerWorlds == null || !playerWorlds.getKeys(false).contains(worldName)) {
            savePlayerStatistics(p, worldName);
            return;
        }

        ItemStack[] content = playerData.getList(uuid + "." + worldName + ".inventory").toArray(new ItemStack[0]);
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> p.getInventory().setContents(content));

        String gamemode = playerData.getString(uuid + "." + worldName + ".gamemode");
        p.setGameMode(GameMode.valueOf(gamemode));

        Collection<PotionEffect> effects = (Collection<PotionEffect>) playerData.getList(uuid + "." + worldName + ".effects");
        if (effects != null && !effects.isEmpty()) p.addPotionEffects(effects);
        else for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
    }
}