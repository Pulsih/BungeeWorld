package me.pulsi_.bungeeworld.worldSeparator;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.values.Values;
import me.pulsi_.bungeeworld.worldSeparator.managers.*;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Storage {

    public static void switchStatistics(Player p, String fromWorld) {
        boolean save = false;

        EffectManager effectManager = new EffectManager(p);
        EnderChestManager enderChestManager = new EnderChestManager(p);
        GameModeManager gameModeManager = new GameModeManager(p);
        HealthManager healthManager = new HealthManager(p);
        HungerManager hungerManager = new HungerManager(p);
        InventoryManager inventoryManager = new InventoryManager(p);

        if (Values.CONFIG.isIsolateEffects()) {
            effectManager.loadEffectsToHashMap(fromWorld);
            if (effectManager.setEffects(true)) save = true;
        }
        if (Values.CONFIG.isIsolateEnderchests()) {
            enderChestManager.loadEnderChestToHashMap(fromWorld);
            if (enderChestManager.setEnderChest(true)) save = true;
        }
        if (Values.CONFIG.isIsolateGamemode()) {
            gameModeManager.loadGameModeToHashMap(fromWorld);
            if (gameModeManager.setGameMode(true)) save = true;
        }
        if (Values.CONFIG.isIsolateHealth()) {
            healthManager.loadHealthToHashMap(fromWorld);
            if (healthManager.setHealth(true)) save = true;
        }
        if (Values.CONFIG.isIsolateHunger()) {
            hungerManager.loadHungerToHashMap(fromWorld);
            if (hungerManager.setHunger(true)) save = true;
        }
        if (Values.CONFIG.isIsolateInventories()) {
            inventoryManager.loadInventoryToHashMap(fromWorld);
            if (inventoryManager.setInventory(true)) save = true;
        }

        if (save || Values.CONFIG.isSaveStatisticsOnWorldChange()) {
            effectManager.saveEffectsToFile(fromWorld, false);
            inventoryManager.saveInventoryToFile(fromWorld, false);
            gameModeManager.saveGameModeToFile(fromWorld, false);
            healthManager.saveHealthToFile(fromWorld, false);
            hungerManager.saveHungerToFile(fromWorld, false);
            inventoryManager.saveInventoryToFile(fromWorld, false);
            BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
        }
    }

    public static void updateCurrentStatistic(Player p) {
        new EffectManager(p).loadEffectsToHashMap();
        new EnderChestManager(p).loadEnderChestToHashMap();
        new GameModeManager(p).loadGameModeToHashMap();
        new HealthManager(p).loadHealthToHashMap();
        new HungerManager(p).loadHungerToHashMap();
        new InventoryManager(p).loadInventoryToHashMap();
    }

    public static void updateAllStatistic(Player p) {
        new EffectManager(p).loadAllEffectsToHashMap();
        new EnderChestManager(p).loadEnderChestsToHashMap();
        new GameModeManager(p).loadGameModesToHashMap();
        new HealthManager(p).loadHealthsToHashMap();
        new HungerManager(p).loadHungersToHashMap();
        new InventoryManager(p).loadInventoriesToHashMap();
    }

    public static void saveAllPlayerStatistics(Player p) {
        new EffectManager(p).saveAllEffectsToFile(false);
        new EnderChestManager(p).saveEnderChestsToFile(false);
        new GameModeManager(p).saveGameModesToFile(false);
        new HealthManager(p).saveHealthsToFile(false);
        new HungerManager(p).saveHungersToFile(false);
        new InventoryManager(p).saveInventoriesToFile(false);

        BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    public static void givePlayerStatistics(Player p, boolean clear) {
        givePlayerStatistics(p, p.getWorld().getName(), clear);
    }

    public static void givePlayerStatistics(Player p, World world, boolean clear) {
        givePlayerStatistics(p, world.getName(), clear);
    }

    public static void givePlayerStatistics(Player p, String worldName, boolean clear) {
        new EffectManager(p).setEffects(worldName, clear);
        new EnderChestManager(p).setEnderChest(worldName, clear);
        new GameModeManager(p).setGameMode(worldName, clear);
        new HealthManager(p).setHealth(worldName, clear);
        new HungerManager(p).setHunger(worldName, clear);
        new InventoryManager(p).setInventory(worldName, clear);
    }
}