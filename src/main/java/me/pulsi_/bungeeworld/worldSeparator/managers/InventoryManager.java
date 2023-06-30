package me.pulsi_.bungeeworld.worldSeparator.managers;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.players.BWPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryManager {

    private final BWPlayer player;
    private final Player p;
    
    public InventoryManager(Player p) {
        this.player = BungeeWorld.INSTANCE.getPlayerRegistry().getPlayers().get(p.getUniqueId());
        this.p = p;
    }
    
    public boolean setInventory(boolean clear) {
        return setInventory(p.getWorld().getName(), clear);
    }

    public boolean setInventory(String worldName, boolean clear) {
        boolean needToSave = false;

        if (!player.inventories.containsKey(worldName)) {
            ItemStack[] inventory;
            FileConfiguration playerData = player.config;
            List<?> items = playerData.getList(worldName + ".inventory");

            if (items == null) {
                if (clear) p.getInventory().clear();

                inventory = p.getInventory().getContents();
                needToSave = true;
            } else inventory = items.toArray(new ItemStack[0]);

            player.inventories.put(worldName, inventory);
        }
        Bukkit.getScheduler().runTaskAsynchronously(BungeeWorld.INSTANCE, () -> p.getInventory().setContents(player.inventories.get(worldName)));
        return needToSave;
    }

    public void loadInventoriesToHashMap() {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            List<?> items = player.config.getList(worldName + ".inventory");
            if (items != null) player.inventories.put(worldName, items.toArray(new ItemStack[0]));
        }
    }

    /**
     * Load the current player inventory to the current world.
     */
    public void loadInventoryToHashMap() {
        loadInventoryToHashMap(p.getWorld().getName());
    }

    /**
     * Load the current player inventory to the selected world.
     * @param world The world
     */
    public void loadInventoryToHashMap(World world) {
        loadInventoryToHashMap(world.getName());
    }

    /**
     * Load the current player inventory to the selected world.
     * @param worldName The world name
     */
    public void loadInventoryToHashMap(String worldName) {
        player.inventories.put(worldName, p.getInventory().getContents());
    }

    public void saveInventoriesToFile() {
        saveInventoriesToFile(true);
    }

    public void saveInventoriesToFile(boolean save) {
        for (World world : Bukkit.getWorlds()) {
            String worldName = world.getName();
            if (player.inventories.containsKey(worldName))
                player.config.set(worldName + ".inventory", player.inventories.get(worldName));
        }

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }

    /**
     * Save the current player inventory to the current world
     * @param save Save or not the file
     */
    public void saveInventoryToFile(boolean save) {
        saveInventoryToFile(p.getWorld(), save);
    }

    /**
     * Save the current player inventory to the selected world
     * @param world The world
     * @param save Save or not the file
     */
    public void saveInventoryToFile(World world, boolean save) {
        saveInventoryToFile(world.getName(), save);
    }

    public void saveInventoryToFile(String worldName, boolean save) {
        FileConfiguration playerData = player.config;

        if (player.inventories.containsKey(worldName)) playerData.set(worldName + ".inventory", player.inventories.get(worldName));
        else playerData.set(worldName + ".inventory", p.getInventory().getContents());

        if (save) BungeeWorld.INSTANCE.getPlayerRegistry().savePlayerFile(p, true);
    }
}