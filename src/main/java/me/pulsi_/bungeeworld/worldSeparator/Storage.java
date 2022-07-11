package me.pulsi_.bungeeworld.worldSeparator;

import me.pulsi_.bungeeworld.BungeeWorld;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class Storage {

    public static void savePlayerStatistics(Player p) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String world = p.getWorld().getName();
        UUID uuid = p.getUniqueId();

        List<ItemStack> inventory = new ArrayList<>(Arrays.asList(p.getInventory().getContents()));
        Collection<PotionEffect> effects = new ArrayList<>(p.getActivePotionEffects());

        playerData.set(uuid + "." + world + ".inventory", inventory);
        playerData.set(uuid + "." + world + ".effects", effects);
        playerData.set(uuid + "." + world + ".gamemode", p.getGameMode().toString());

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

    public static void givePlayerStatistics(Player p) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        String world = p.getWorld().getName();
        UUID uuid = p.getUniqueId();

        if (playerData.getString(uuid + "." + world) == null) {
            for (ItemStack i : p.getInventory().getContents()) if (i != null) i.setType(Material.AIR);
            return;
        }

        ItemStack[] content = playerData.getList(uuid + "." + world + ".inventory").toArray(new ItemStack[0]);
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> p.getInventory().setContents(content));

        String gamemode = playerData.getString(uuid + "." + world + ".gamemode");
        p.setGameMode(GameMode.valueOf(gamemode));

        Collection<PotionEffect> effects = (Collection<PotionEffect>) playerData.getList(uuid + "." + world + ".effects");
        if (effects != null && !effects.isEmpty()) p.addPotionEffects(effects);
        else for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
    }

    public static void givePlayerStatistics(Player p, String world) {
        FileConfiguration playerData = PlayerManager.getPlayerConfig(p);
        UUID uuid = p.getUniqueId();

        if (playerData.getString(uuid + "." + world) == null) {
            for (ItemStack i : p.getInventory().getContents()) if (i != null) i.setType(Material.AIR);
            return;
        }

        ItemStack[] content = playerData.getList(uuid + "." + world + ".inventory").toArray(new ItemStack[0]);
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.getInstance(), () -> p.getInventory().setContents(content));

        String gamemode = playerData.getString(uuid + "." + world + ".gamemode");
        p.setGameMode(GameMode.valueOf(gamemode));

        Collection<PotionEffect> effects = (Collection<PotionEffect>) playerData.getList(uuid + "." + world + ".effects");
        if (effects != null && !effects.isEmpty()) p.addPotionEffects(effects);
        else for (PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
    }
}