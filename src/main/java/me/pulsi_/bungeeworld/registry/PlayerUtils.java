package me.pulsi_.bungeeworld.registry;

import me.pulsi_.bungeeworld.BungeeWorld;
import me.pulsi_.bungeeworld.utils.BWChat;
import me.pulsi_.bungeeworld.values.Values;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * This class is used to simplify actions for that player in the specified world.
 */
public class PlayerUtils {

    private final Player p;

    public PlayerUtils(Player p) {
        this.p = p;
    }

    public void joinMessage(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) joinMessage(world);
    }

    public void joinMessage(World world) {
        WorldReader reader = new WorldReader(world.getName());
        String joinMessage = reader.getWorld().getJoinMessage();
        if (joinMessage == null || joinMessage.isEmpty()) return;

        String message = BWChat.color(joinMessage.replace("%player%", p.getName()));
        for (Player player : world.getPlayers())
            if (!player.equals(p)) player.sendMessage(message);

        List<String> linkedWorldsNames = reader.getWorld().getLinkedWorlds();
        if (linkedWorldsNames.isEmpty()) return;

        for (String linkedWorldName : linkedWorldsNames) {
            if (linkedWorldName.equals(world.getName())) continue;

            World linkedWorld = Bukkit.getWorld(linkedWorldName);
            if (linkedWorld == null) continue;

            for (Player players : linkedWorld.getPlayers())
                players.sendMessage(joinMessage);
        }
    }

    public void quitMessage(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) quitMessage(world);
    }

    public void quitMessage(World world) {
        WorldReader reader = new WorldReader(world.getName());
        String quitMessage = reader.getWorld().getQuitMessage();
        if (quitMessage == null || quitMessage.isEmpty()) return;

        String message = BWChat.color(quitMessage.replace("%player%", p.getName()));
        for (Player player : world.getPlayers())
            if (!player.equals(p)) player.sendMessage(message);

        List<String> linkedWorldsNames = reader.getWorld().getLinkedWorlds();
        if (linkedWorldsNames.isEmpty()) return;

        for (String linkedWorldName : linkedWorldsNames) {
            if (linkedWorldName.equals(world.getName())) continue;

            World linkedWorld = Bukkit.getWorld(linkedWorldName);
            if (linkedWorld == null) continue;

            for (Player players : linkedWorld.getPlayers())
                players.sendMessage(quitMessage);
        }
    }

    public void loadStatistics(String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player == null) return;

        if (Values.CONFIG.isIsolateInventories()) p.getInventory().setContents(player.getInventory());
        if (Values.CONFIG.isIsolateEnderchests()) p.getEnderChest().setContents(player.getEnderChest());
        if (Values.CONFIG.isIsolateEffects()) p.addPotionEffects(player.getEffects());
        if (Values.CONFIG.isIsolateGamemode()) p.setGameMode(player.getGameMode());
        if (Values.CONFIG.isIsolateHealth()) {
            p.setHealth(player.getHealth());
            p.setMaxHealth(player.getMaxHealth());
        }
        if (Values.CONFIG.isIsolateHunger()) p.setFoodLevel(player.getHunger());
    }

    public void storeStatistics(String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player == null) return;

        player.setInventory(p.getInventory().getContents());
        player.setEnderChest(p.getEnderChest().getContents());
        player.setEffects(p.getActivePotionEffects());
        player.setGameMode(p.getGameMode());
        player.setHealth((int) p.getHealth());
        player.setMaxHealth((int) p.getMaxHealth());
        player.setHunger(p.getFoodLevel());
    }

    /**
     * Method to change last player location separated from all other statistics because it is the only value that change when switching world.
     * @param location The location.
     * @param worldName The world where to save this location.
     */
    public void storeLastLocation(Location location, String worldName) {
        BWPlayer player = getBWPlayer(worldName);
        if (player != null) player.setLastLocation(location);
    }

    public BWPlayer getBWPlayer(String worldName) {
        return BungeeWorld.INSTANCE.getWorldsRegistry().getWorlds().get(worldName).getPlayers().get(p.getUniqueId());
    }
}