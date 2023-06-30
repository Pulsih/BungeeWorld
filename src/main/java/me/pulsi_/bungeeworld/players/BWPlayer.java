package me.pulsi_.bungeeworld.players;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

public class BWPlayer {

    public static class BWHealth {
        public double value, max;

        public BWHealth(double value, double max) {
            this.value = value;
            this.max = max;
        }
    }

    public HashMap<String, Collection<PotionEffect>> effects = new HashMap<>();
    public HashMap<String, ItemStack[]> enderchests = new HashMap<>();
    public HashMap<String, GameMode> gamemodes = new HashMap<>();
    public HashMap<String, BWHealth> healths = new HashMap<>();
    public HashMap<String, Integer> hungers = new HashMap<>();
    public HashMap<String, ItemStack[]> inventories = new HashMap<>();
    public HashMap<String, Location> locations = new HashMap<>();
    public FileConfiguration config;
    public File configFile;
}